package ac.inu.noisemaster.core.noise.domain.repository.device;

import ac.inu.noisemaster.core.noise.domain.model.Device;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ac.inu.noisemaster.core.noise.domain.model.QDevice.device;
import static ac.inu.noisemaster.core.noise.domain.model.QNoise.noise;
import static ac.inu.noisemaster.core.noise.domain.model.QPlace.place;


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
