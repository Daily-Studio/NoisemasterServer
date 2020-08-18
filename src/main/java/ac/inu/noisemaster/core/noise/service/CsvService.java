package ac.inu.noisemaster.core.noise.service;

import ac.inu.noisemaster.core.noise.domain.csv.CsvLog;
import ac.inu.noisemaster.core.noise.domain.model.Noises;
import ac.inu.noisemaster.core.noise.domain.repository.csv.CsvLogRepository;
import ac.inu.noisemaster.core.noise.domain.repository.noise.NoiseRepository;
import ac.inu.noisemaster.core.noise.dto.csv.CsvLogDto;
import ac.inu.noisemaster.core.noise.dto.csv.NoiseCsvDto;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseBundleResDTO;
import ac.inu.noisemaster.core.noise.util.DirUtils;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvService {
    private final NoiseRepository noiseRepository;
    private final CsvLogRepository csvLogRepository;

    public List<CsvLogDto> getList() {
        List<CsvLog> csvLogs = csvLogRepository.findAll();
        return csvLogs.stream()
                .map(CsvLogDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void exportNoise() {
        String dir = DirUtils.getDir("../csv/");
        String csvName = getCsvName(dir);

        Noises noises = new Noises(noiseRepository.findAllByOrderByCreatedTimeDesc());
        if (noises.isEmpty()) {
            return;
        }

        CsvLog csvLog = CsvLog.builder()
                .csvName(csvName)
                .startNoiseId(noises.getLastNoise().getId())
                .endNoiseId(noises.getFirstNoise().getId())
                .build();
        csvLogRepository.save(csvLog);

        File csvFile = new File(csvName);

        try (PrintWriter printWriter = new PrintWriter(csvFile, "EUC-KR")) {
            StatefulBeanToCsv<NoiseCsvDto> writer = new StatefulBeanToCsvBuilder<NoiseCsvDto>(printWriter)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withOrderedResults(false)
                    .build();
            List<NoiseCsvDto> noiseCsvDtos = NoiseCsvDto.of(NoiseBundleResDTO.of(noises.get()));
            writer.write(noiseCsvDtos);
        } catch (FileNotFoundException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }

        noiseRepository.deleteAll(noises.get());
    }

    private String getCsvName(String dir) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return dir + "noise" +
                "-" +
                localDateTime.getYear() +
                "-" +
                localDateTime.getMonthValue() +
                "-" +
                localDateTime.getDayOfMonth() +
                ".csv";
    }
}
