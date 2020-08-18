package ac.inu.noisemaster.core.noise.dto.csv;

import ac.inu.noisemaster.core.noise.dto.noise.NoiseBundleResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseResDTO;
import ac.inu.noisemaster.core.noise.dto.place.PlaceDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NoiseCsvDto {
    private final String decibel;
    private final String device;
    private final String temperature;
    private final String placeId;
    private final String tag;
    private final String gridX;
    private final String gridY;
    private final String createdTime;

    @Builder
    public NoiseCsvDto(final Double decibel, final String device, final String temperature, final PlaceDTO placedto, final String createdTime) {
        this.decibel = String.valueOf(decibel);
        this.device = device;
        this.temperature = temperature;
        this.placeId = String.valueOf(placedto.getPlaceId());
        this.tag = placedto.getTag();
        this.gridX = placedto.getGridX();
        this.gridY = placedto.getGridY();
        this.createdTime = createdTime;
    }

    public static List<NoiseCsvDto> of(NoiseBundleResDTO noiseBundleResDTO) {
        return noiseBundleResDTO.getNoises().stream()
                .map(NoiseCsvDto::of)
                .collect(Collectors.toList());
    }

    public static NoiseCsvDto of(NoiseResDTO noiseResDTO) {
        return NoiseCsvDto.builder()
                .createdTime(noiseResDTO.getCreatedTime())
                .decibel(noiseResDTO.getDecibel())
                .device(noiseResDTO.getDevice())
                .temperature(noiseResDTO.getTemperature())
                .placedto(noiseResDTO.getPlaceDTO())
                .build();
    }

    public String[] toCsvDatas() {
        return new String[]{this.createdTime, this.device, this.decibel,
                this.placeId, this.tag, this.gridX, this.gridY,
                this.temperature};
    }
}
