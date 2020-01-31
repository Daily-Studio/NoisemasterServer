package ac.inu.noisemaster.core.noise.repository;

import ac.inu.noisemaster.core.noise.domain.Noise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoiseRepositoryCustom {
    Page<Noise> findDynamicPagingQueryAdvance(String device, String decibel, String temperature, String tag, Pageable pageable);
}
