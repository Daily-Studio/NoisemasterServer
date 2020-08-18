package ac.inu.noisemaster.core.noise.dto.device;

import ac.inu.noisemaster.core.noise.domain.model.Noise;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DeviceRecentBundleResDTO {
    private final List<DeviceRecentResDTO> devices;

    public DeviceRecentBundleResDTO() {
        this.devices = new ArrayList<>();
    }

    public DeviceRecentBundleResDTO(List<DeviceRecentResDTO> devices) {
        this.devices = devices;
    }

    public static DeviceRecentBundleResDTO of(List<Noise> noises) {
        return noises.stream()
                .map(DeviceRecentResDTO::from)
                .collect(Collectors.collectingAndThen(Collectors.toList(), DeviceRecentBundleResDTO::new));
    }

}
