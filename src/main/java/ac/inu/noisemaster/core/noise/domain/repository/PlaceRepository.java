package ac.inu.noisemaster.core.noise.domain.repository;

import ac.inu.noisemaster.core.noise.domain.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
