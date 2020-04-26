package ac.inu.noisemaster.core.noise.domain.repository.noise;

import ac.inu.noisemaster.core.noise.domain.model.Noise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoiseRepository extends JpaRepository<Noise, Long>, NoiseRepositoryCustom {
}
