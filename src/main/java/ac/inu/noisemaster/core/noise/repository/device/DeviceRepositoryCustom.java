package ac.inu.noisemaster.core.noise.repository.device;

import ac.inu.noisemaster.core.noise.domain.Device;

import java.util.List;

public interface DeviceRepositoryCustom {
    List<Device> findRecentDevice();
}
