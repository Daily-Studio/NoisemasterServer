package ac.inu.noisemaster.core.noise.dto.noise;

import ac.inu.noisemaster.core.noise.domain.Device;
import ac.inu.noisemaster.core.noise.domain.Noise;
import ac.inu.noisemaster.core.noise.domain.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NoiseSaveDTO {
    private Double decibel;
    private String device;
    private String temperature;
    private String tag;
    private String gridX;
    private String gridY;

    @Builder(builderMethodName = "testBuilder")
    public NoiseSaveDTO(Double decibel, String device, String temperature, String tag, String gridX, String gridY) {
        this.decibel = decibel;
        this.device = device;
        this.temperature = temperature;
        this.tag = tag;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public Noise toNoiseEntity(Device device, Place place) {
        return Noise.builder()
                .decibel(this.decibel)
                .device(device)
                .temperature(this.temperature)
                .place(place)
                .build();
    }

    public Place toPlaceEntity() {
        return Place.builder()
                .tag(tag)
                .gridX(gridX)
                .gridY(gridY)
                .build();
    }

    public Device toDeviceEntity() {
        return new Device(this.device);
    }
}
