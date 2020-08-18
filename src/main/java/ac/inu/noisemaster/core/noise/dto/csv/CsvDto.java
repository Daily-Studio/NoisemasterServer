package ac.inu.noisemaster.core.noise.dto.csv;

import ac.inu.noisemaster.core.noise.domain.model.Noise;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseBundleResDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class CsvDto {
    private final String csvName;
    private final NoiseBundleResDTO noiseBundleResDTO;

    public CsvDto(final String csvName, final NoiseBundleResDTO noiseBundleResDTO) {
        this.csvName = csvName;
        this.noiseBundleResDTO = noiseBundleResDTO;
    }

    public static CsvDto of(String csvName, List<Noise> noises) {
        return new CsvDto(csvName, NoiseBundleResDTO.of(noises));
    }

    public List<NoiseCsvDto> toCsvList() {
        return NoiseCsvDto.of(this.noiseBundleResDTO);
    }
}
