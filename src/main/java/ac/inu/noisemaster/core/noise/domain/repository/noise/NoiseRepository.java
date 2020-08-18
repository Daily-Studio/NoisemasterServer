package ac.inu.noisemaster.core.noise.domain.repository.noise;

import ac.inu.noisemaster.core.noise.domain.model.Noise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoiseRepository extends JpaRepository<Noise, Long>, NoiseRepositoryCustom {
    List<Noise> findAllByOrderByCreatedTimeDesc();

    void deleteALlByIdBetween(Long start, Long end);
}
