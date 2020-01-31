package ac.inu.noisemaster.core.noise.dto;

import ac.inu.noisemaster.core.noise.domain.Noise;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class NoisePagingResDTO {
    private NoiseBundleResDTO noises;
    private int totalPageNumber;
    private int currentPageNumber;
    private boolean isFirst;
    private boolean isLast;

    @Builder
    public NoisePagingResDTO(NoiseBundleResDTO noises, int totalPageNumber, int currentPageNumber, boolean isFirst, boolean isLast) {
        this.noises = noises;
        this.totalPageNumber = totalPageNumber;
        this.currentPageNumber = currentPageNumber;
        this.isFirst = isFirst;
        this.isLast = isLast;
    }

    public static NoisePagingResDTO of(Page<Noise> pagedNoise) {
        return NoisePagingResDTO.builder()
                .noises(NoiseBundleResDTO.of(pagedNoise.getContent()))
                .totalPageNumber(pagedNoise.getTotalPages())
                .currentPageNumber(pagedNoise.getNumber())
                .isFirst(pagedNoise.isFirst())
                .isLast(pagedNoise.isLast())
                .build();
    }
}