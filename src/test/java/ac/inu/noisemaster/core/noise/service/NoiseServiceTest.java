package ac.inu.noisemaster.core.noise.service;

import ac.inu.noisemaster.core.noise.domain.model.Device;
import ac.inu.noisemaster.core.noise.domain.model.Noise;
import ac.inu.noisemaster.core.noise.domain.model.Place;
import ac.inu.noisemaster.core.noise.domain.repository.PlaceRepository;
import ac.inu.noisemaster.core.noise.domain.repository.device.DeviceRepository;
import ac.inu.noisemaster.core.noise.domain.repository.noise.NoiseRepository;
import ac.inu.noisemaster.core.noise.dto.device.DeviceRecentBundleResDTO;
import ac.inu.noisemaster.core.noise.dto.device.DeviceRecentResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoisePagingResDTO;
import ac.inu.noisemaster.core.noise.dto.noise.NoiseSaveDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class NoiseServiceTest {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

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

    private static Place aPlace(String tag) {
        return Place.builder()
                .tag(tag)
                .gridX("123")
                .gridY("321")
                .build();
    }

    private static Place aPlace() {
        return aPlace("태그");
    }

    @DisplayName("페이징 최근 저장 순대로")
    @Test
    void name() throws InterruptedException {
        //Given
        Place place = placeRepository.saveAndFlush(aPlace());
        Device device1 = deviceRepository.saveAndFlush(aDevice("device 1"));

        LocalDateTime time = LocalDateTime.now();
        Noise noiseA1 = Noise.builder().device(device1).place(place).decibel(1D)
                .createdTime(time).build();

        time = time.plus(10, ChronoUnit.SECONDS);
        Noise noiseA2 = Noise.builder().device(device1).place(place).decibel(2D)
                .createdTime(time).build();
        time = time.plus(10, ChronoUnit.SECONDS);
        Noise noiseA3 = Noise.builder().device(device1).place(place).decibel(3D)
                .createdTime(time).build();

        time = time.plus(10, ChronoUnit.SECONDS);
        Noise noiseB1 = Noise.builder().device(device1).place(place).decibel(4D)
                .createdTime(time).build();

        time = time.plus(10, ChronoUnit.SECONDS);
        Noise noiseB2 = Noise.builder().device(device1).place(place).decibel(5D)
                .createdTime(time).build();

        time = time.plus(10, ChronoUnit.SECONDS);
        Noise noiseB3 = Noise.builder().device(device1).place(place).decibel(6D)
                .createdTime(time).build();

        noiseRepository.save(noiseA1);
        noiseRepository.save(noiseA2);
        noiseRepository.save(noiseA3);
        noiseRepository.save(noiseB1);
        noiseRepository.save(noiseB2);
        noiseRepository.save(noiseB3);
        noiseRepository.flush();

        //when
        NoisePagingResDTO noiseAdvance = noiseService.findNoiseAdvance(null, null, null, null, null, PageRequest.of(0, 6));

        //then
        assertThat(noiseAdvance.getData().getNoises().get(0).getDecibel()).isEqualTo(6);
    }

    @DisplayName("최근 저장된 디바이스가 가장 위로 올라오도록")
    @Test
    void name2() {
        //given
        Place place = placeRepository.saveAndFlush(aPlace());
        Device device1 = deviceRepository.saveAndFlush(aDevice("device 1"));
        Device device2 = deviceRepository.saveAndFlush(aDevice("device 2"));

        LocalDateTime time = LocalDateTime.now();
        Noise noiseA1 = Noise.builder().device(device1).place(place).decibel(1D)
                .createdTime(time).build();

        LocalDateTime secondTime = time.plus(100, ChronoUnit.SECONDS);
        Noise noiseA2 = Noise.builder().device(device1).place(place).decibel(3D)
                .createdTime(secondTime).build();

        LocalDateTime thirdTime = secondTime.plus(100, ChronoUnit.SECONDS);
        Noise noiseB1 = Noise.builder().device(device2).place(place).decibel(1D)
                .createdTime(thirdTime).build();

        noiseRepository.saveAll(Arrays.asList(noiseA1, noiseA2, noiseB1));

        //when
        DeviceRecentBundleResDTO recentNoises = noiseService.findRecentNoises();

        //then
        DeviceRecentResDTO deviceDto = recentNoises.getDevices().stream()
                .filter(deviceRecentResDTO -> deviceRecentResDTO.getDevice().equals("device 1"))
                .findFirst().orElseThrow(RuntimeException::new);
        assertThat(deviceDto.getDecibel()).isEqualTo(3D);
    }

    @DisplayName("저장 내역이 아예 없는 경우")
    @Test
    void name3() {
        //given
        Place place = placeRepository.saveAndFlush(aPlace());
        Device device1 = deviceRepository.saveAndFlush(aDevice("device 1"));
        Device device2 = deviceRepository.saveAndFlush(aDevice("device 2"));

        //when
        DeviceRecentBundleResDTO recentNoises = noiseService.findRecentNoises();

        //then

        assertThat(recentNoises.getDevices()).hasSize(0);
    }

    private Device aDevice(String name) {
        return new Device(name);
    }
}