package ac.inu.noisemaster.core.noise.repository;

import ac.inu.noisemaster.core.noise.domain.Noise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoiseRepository extends JpaRepository<Noise, Long>, NoiseRepositoryCustom {
}
