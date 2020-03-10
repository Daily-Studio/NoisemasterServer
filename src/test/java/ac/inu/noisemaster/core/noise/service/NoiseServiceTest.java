package ac.inu.noisemaster.core.noise.service;

import ac.inu.noisemaster.core.noise.domain.Noise;
import ac.inu.noisemaster.core.noise.domain.Place;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseSaveDTO;
import ac.inu.noisemaster.core.noise.repository.PlaceRepository;
import ac.inu.noisemaster.core.noise.repository.device.DeviceRepository;
import ac.inu.noisemaster.core.noise.repository.noise.NoiseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class NoiseServiceTest {

    @Autowired
    private NoiseService noiseService;

    @Autowired
    private NoiseRepository noiseRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @AfterEach
    void tearDown() {
        noiseRepository.deleteAll();
        placeRepository.deleteAll();
        deviceRepository.deleteAll();
    }

    @DisplayName("1개 정상저장")
    @Test
    void save1() {
        NoiseSaveDTO noiseSaveDTO = NoiseSaveDTO.testBuilder()
                .decibel(10D)
                .device("raspberry1")
                .temperature("36.5")
                .gridX("123.123")
                .gridY("321.321")
                .build();

        noiseService.save(noiseSaveDTO);

        List<Noise> all = noiseRepository.findAll();

        assertThat(all).hasSize(1);
    }

    @DisplayName("좌표 중복저장시 쿼리 한방 나가기")
    @Test
    void save2() {
        NoiseSaveDTO noiseSaveDTO = NoiseSaveDTO.testBuilder()
                .decibel(10D)
                .device("raspberry1")
                .temperature("36.5")
                .tag("태그1")
                .gridX("123.123")
                .gridY("321.321")
                .build();

        noiseService.save(noiseSaveDTO);

        List<Place> saveOne = placeRepository.findAll();
        assertThat(saveOne).hasSize(1);

        noiseService.save(noiseSaveDTO);
        List<Place> all = placeRepository.findAll();
        assertThat(all).hasSize(1);
    }

}