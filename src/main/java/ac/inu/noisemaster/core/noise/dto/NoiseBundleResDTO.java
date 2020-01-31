package ac.inu.noisemaster.core.noise.dto;

import ac.inu.noisemaster.core.noise.domain.Noise;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NoiseBundleResDTO {
    private final List<NoiseResDTO> noises;

    public NoiseBundleResDTO(List<NoiseResDTO> noises) {
        this.noises = noises;
    }

    public static NoiseBundleResDTO of(List<Noise> noises) {
        List<NoiseResDTO> noiseResDTOs = noises.stream()
                .map(NoiseResDTO::from)
                .collect(Collectors.toList());
        return new NoiseBundleResDTO(noiseResDTOs);
    }
}
