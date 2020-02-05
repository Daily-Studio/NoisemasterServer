package ac.inu.noisemaster.core.noise.dto.device;

import ac.inu.noisemaster.core.noise.domain.Device;
import ac.inu.noisemaster.core.noise.dto.place.PlaceDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
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

    public static DeviceRecentResDTO from(Device device) {
        return DeviceRecentResDTO.builder()
                .device(device.getName())
                .decibel(device.getNoiseOne().getDecibel())
                .temperature(device.getNoiseOne().getTemperature())
                .placeDTO(PlaceDTO.from(device.getNoiseOne().getPlace()))
                .createdTime(device.getNoiseOne().getCreatedTime())
                .build();
    }
}
