package ac.inu.noisemaster.core.noise.repository;

import ac.inu.noisemaster.core.noise.domain.Noise;
import ac.inu.noisemaster.core.noise.domain.Place;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class NoiseRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(NoiseRepositoryTest.class);
    private final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();

    @Autowired
    private NoiseRepository noiseRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @AfterEach
    void tearDown() {
        noiseRepository.deleteAll();
        placeRepository.deleteAll();
    }

    @DisplayName("쿼리 동작 확인")
    @Test
    void test1() {
        saveMockNoise(20);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Noise> pagedNoise = noiseRepository.findDynamicPagingQueryAdvance(null, null, null, null, null, pageable);

        log.info(gson.toJson(pagedNoise.getContent()));
        log.info(gson.toJson(pagedNoise.getTotalPages()));
        log.info(gson.toJson(pagedNoise.getNumber()));
        log.info(gson.toJson(pagedNoise.isFirst()));
        log.info(gson.toJson(pagedNoise.isLast()));
        log.info(gson.toJson(pagedNoise.getPageable()));
    }

    private void saveMockNoise(int count) {
        Place place = Place.builder()
                .tag("태그")
                .gridX("123")
                .gridY("321")
                .build();
        place = placeRepository.saveAndFlush(place);

        for (int i = 0; i < count; i++) {
            Noise noise = new Noise(i + " db", "d1", "36.0", null, place);
            noiseRepository.save(noise);
        }
    }
}