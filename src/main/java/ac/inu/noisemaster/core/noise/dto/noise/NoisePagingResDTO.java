package ac.inu.noisemaster.core.noise.dto.noise;

import ac.inu.noisemaster.core.noise.domain.model.Noise;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@ToString
@Getter
public class NoisePagingResDTO {
    private NoiseBundleResDTO data;
    private int totalPageNumber;
    private int currentPageNumber;
    private boolean isFirst;
    private boolean isLast;

    @Builder
    public NoisePagingResDTO(NoiseBundleResDTO data, int totalPageNumber, int currentPageNumber, boolean isFirst, boolean isLast) {
        this.data = data;
        this.totalPageNumber = totalPageNumber;
        this.currentPageNumber = currentPageNumber;
        this.isFirst = isFirst;
        this.isLast = isLast;
    }

    public static NoisePagingResDTO of(Page<Noise> pagedNoise) {
        return NoisePagingResDTO.builder()
                .data(NoiseBundleResDTO.of(pagedNoise.getContent()))
                .totalPageNumber(pagedNoise.getTotalPages())
                .currentPageNumber(pagedNoise.getNumber())
                .isFirst(pagedNoise.isFirst())
                .isLast(pagedNoise.isLast())
                .build();
    }
}