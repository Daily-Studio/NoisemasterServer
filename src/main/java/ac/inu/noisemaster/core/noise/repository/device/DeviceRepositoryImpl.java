package ac.inu.noisemaster.core.noise.repository.device;

import ac.inu.noisemaster.core.noise.domain.Device;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ac.inu.noisemaster.core.noise.domain.QDevice.device;
import static ac.inu.noisemaster.core.noise.domain.QNoise.noise;
import static ac.inu.noisemaster.core.noise.domain.QPlace.place;

@Repository
public class DeviceRepositoryImpl extends QuerydslRepositorySupport implements DeviceRepositoryCustom {

    public DeviceRepositoryImpl() {
        super(Device.class);
    }

    @Override
    public List<Device> findRecentDevice() {
        return super.from(device)
                .distinct()
                .innerJoin(device.noises, noise).fetchJoin()
                .innerJoin(noise.place, place).fetchJoin()
                .orderBy(noise.createdTime.desc())
                .fetch();
    }
}
