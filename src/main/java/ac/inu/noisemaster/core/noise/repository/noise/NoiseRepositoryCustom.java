package ac.inu.noisemaster.core.noise.repository.noise;

import ac.inu.noisemaster.core.noise.domain.Noise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoiseRepositoryCustom {
    Page<Noise> findDynamicPagingQueryAdvance(String device, Double decibel, String temperature, String tag, String date, Pageable pageable);
}
