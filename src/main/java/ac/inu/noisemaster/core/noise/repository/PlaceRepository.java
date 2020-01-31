package ac.inu.noisemaster.core.noise.repository;

import ac.inu.noisemaster.core.noise.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
