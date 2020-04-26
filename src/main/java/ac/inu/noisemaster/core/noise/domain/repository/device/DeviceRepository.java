package ac.inu.noisemaster.core.noise.domain.repository.device;

import ac.inu.noisemaster.core.noise.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long>, DeviceRepositoryCustom {
}
