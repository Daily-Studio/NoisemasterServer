package ac.inu.noisemaster.core.noise.domain.csv;


import ac.inu.noisemaster.core.noise.domain.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class CsvLog extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long startNoiseId;
    private Long endNoiseId;
    private String csvName;

    @Builder
    public CsvLog(final LocalDateTime createdTime, final Long startNoiseId, final Long endNoiseId, final String csvName) {
        super(createdTime);
        this.startNoiseId = startNoiseId;
        this.endNoiseId = endNoiseId;
        this.csvName = csvName;
    }
}
