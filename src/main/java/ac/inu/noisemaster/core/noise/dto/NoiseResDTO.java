package ac.inu.noisemaster.core.noise.dto;

import ac.inu.noisemaster.core.noise.domain.Noise;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoiseResDTO {
    private String decibel;
    private String device;
    private String temperature;
    private PlaceDTO placeDTO;
    private LocalDateTime createdTime;

    @Builder
    public NoiseResDTO(String decibel, String device, String temperature, PlaceDTO placeDTO, LocalDateTime createdTime) {
        this.decibel = decibel;
        this.device = device;
        this.temperature = temperature;
        this.placeDTO = placeDTO;
        this.createdTime = createdTime;
    }

    public static NoiseResDTO from(Noise noise) {
        return NoiseResDTO.builder()
                .decibel(noise.getDecibel())
                .device(noise.getDevice())
                .createdTime(noise.getCreatedTime())
                .temperature(noise.getTemperature())
                .placeDTO(PlaceDTO.from(noise.getPlace()))
                .build();
    }
}
