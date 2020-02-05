package ac.inu.noisemaster.core.noise.dto.noise;

import ac.inu.noisemaster.core.noise.domain.Noise;
import ac.inu.noisemaster.core.noise.dto.place.PlaceDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoiseResDTO {
    private Double decibel;
    private String device;
    private String temperature;
    private PlaceDTO placeDTO;
    private String createdTime;

    @Builder
    public NoiseResDTO(Double decibel, String device, String temperature, PlaceDTO placeDTO, LocalDateTime createdTime) {
        this.decibel = decibel;
        this.device = device;
        this.temperature = temperature;
        this.placeDTO = placeDTO;
        this.createdTime = createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static NoiseResDTO from(Noise noise) {
        return NoiseResDTO.builder()
                .decibel(noise.getDecibel())
                .device(noise.getDeviceName())
                .createdTime(noise.getCreatedTime())
                .temperature(noise.getTemperature())
                .placeDTO(PlaceDTO.from(noise.getPlace()))
                .build();
    }
}
