package ac.inu.noisemaster.core.noise.domain.repository.csv;

import ac.inu.noisemaster.core.noise.domain.csv.CsvLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvLogRepository extends JpaRepository<CsvLog, Long> {
}
