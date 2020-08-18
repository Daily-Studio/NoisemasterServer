package ac.inu.noisemaster.core.noise.dto.csv;

import ac.inu.noisemaster.core.noise.domain.csv.CsvLog;
import lombok.Getter;

@Getter
public class CsvLogDto {
    private Long id;
    private String csvPath;

    public CsvLogDto(final Long id, final String csvPath) {
        this.id = id;
        this.csvPath = csvPath;
    }

    public static CsvLogDto of(CsvLog csvLog) {
        return new CsvLogDto(csvLog.getId(), csvLog.getCsvName());
    }
}
