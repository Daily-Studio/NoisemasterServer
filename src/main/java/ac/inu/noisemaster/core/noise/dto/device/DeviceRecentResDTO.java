package ac.inu.noisemaster.core.noise.dto.device;

import ac.inu.noisemaster.core.noise.domain.model.Noise;
import ac.inu.noisemaster.core.noise.dto.place.PlaceDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeviceRecentResDTO {
    private String device;
    private Double decibel;
    private String temperature;
    private PlaceDTO placeDTO;
    private String createdTime;

    @Builder
    public DeviceRecentResDTO(String device, Double decibel, String temperature, PlaceDTO placeDTO, LocalDateTime createdTime) {
        this.device = device;
        this.decibel = decibel;
        this.temperature = temperature;
        this.placeDTO = placeDTO;
        this.createdTime = createdTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static DeviceRecentResDTO from(Noise noise) {
        return DeviceRecentResDTO.builder()
                .device(noise.getDeviceName())
                .decibel(noise.getDecibel())
                .temperature(noise.getTemperature())
                .placeDTO(PlaceDTO.from(noise.getPlace()))
                .createdTime(noise.getCreatedTime())
                .build();
    }

}
