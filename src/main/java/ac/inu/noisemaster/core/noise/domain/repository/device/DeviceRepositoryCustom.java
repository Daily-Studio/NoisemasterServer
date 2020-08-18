package ac.inu.noisemaster.core.noise.domain.repository.device;

import ac.inu.noisemaster.core.noise.domain.model.Device;

import java.util.List;

public interface DeviceRepositoryCustom {
    List<Device> findRecentDevice();


}
