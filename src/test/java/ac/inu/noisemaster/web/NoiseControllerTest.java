package ac.inu.noisemaster.web;

import ac.inu.noisemaster.core.noise.domain.Device;
import ac.inu.noisemaster.core.noise.domain.Noise;
import ac.inu.noisemaster.core.noise.domain.Place;
import ac.inu.noisemaster.core.noise.repository.PlaceRepository;
import ac.inu.noisemaster.core.noise.repository.device.DeviceRepository;
import ac.inu.noisemaster.core.noise.repository.noise.NoiseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class NoiseControllerTest {

    private static final String NOISE_URL = "/api/v1/noise";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private NoiseRepository noiseRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @AfterEach
    void tearDown() {
        noiseRepository.deleteAll();
        placeRepository.deleteAll();
        deviceRepository.deleteAll();
    }

    @DisplayName("검색조건 없이 검색하기")
    @Test
    void name() throws Exception {
        //given
        Place place = Place.builder().gridX("123").gridY("123").tag("device1").build();
        place = placeRepository.saveAndFlush(place);

        Device device = deviceRepository.saveAndFlush(aDevice("device1"));

        Noise noise = Noise.builder().device(device).place(place).build();
        noiseRepository.saveAndFlush(noise);

        //when
        mockMvc.perform(get(NOISE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        ).andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.noises", hasSize(1)));

    }


    @DisplayName("device 검색 조건으로 검색하기")
    @Test
    void findNoiseAdvance() throws Exception {
        //given
        Place place1 = Place.builder().gridX("123").gridY("123").tag("1번 위치").build();
        Place place2 = Place.builder().gridX("1234").gridY("1234").tag("2번 위치").build();
        place1 = placeRepository.saveAndFlush(place1);
        place2 = placeRepository.saveAndFlush(place2);

        Device device1 = deviceRepository.saveAndFlush(aDevice("device1"));
        Device device2 = deviceRepository.saveAndFlush(aDevice("device2"));

        Noise noise1 = Noise.builder().device(device1).place(place1).build();
        Noise noise2 = Noise.builder().device(device2).place(place2).build();
        noiseRepository.saveAndFlush(noise1);
        noiseRepository.saveAndFlush(noise2);

        //when
        mockMvc.perform(get(NOISE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("device", "device1")
        ).andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.noises", hasSize(1)));
    }

    private Device aDevice(String name) {
        return new Device(name);
    }

    @Test
    void saveNoise() {
        //given
        //when
        //then
    }

    @DisplayName("decibel 10단위 검색")
    @Test
    void test2() throws Exception {
        //given
        Place place = placeRepository.saveAndFlush(aPlace());
        Device device = deviceRepository.saveAndFlush(aDevice("device"));
        Noise noise1 = Noise.builder()
                .decibel(10D)
                .device(device)
                .place(place)
                .build();
        Noise noise2 = Noise.builder()
                .decibel(19D)
                .device(device)
                .place(place)
                .build();
        Noise noise3 = Noise.builder()
                .decibel(20D)
                .device(device)
                .place(place)
                .build();

        noiseRepository.saveAll(Arrays.asList(noise1, noise2, noise3));

        //when
        mockMvc.perform(get(NOISE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("decibel", "11D")
        ).andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.noises", hasSize(2)));
    }

    @DisplayName("디바이스별 가장 최근 소음정보들 하나씩 노출")
    @Test
    void test3() throws Exception {
        //given
        Place place = placeRepository.saveAndFlush(aPlace());
        Device device1 = deviceRepository.saveAndFlush(aDevice("device 1"));
        Device device2 = deviceRepository.saveAndFlush(aDevice("device 2"));

        Noise noiseA1 = Noise.builder().device(device1).place(place).decibel(1D).build();
        Noise noiseA2 = Noise.builder().device(device1).place(place).decibel(2D).build();
        Noise noiseA3 = Noise.builder().device(device1).place(place).decibel(3D).build();

        Noise noiseB1 = Noise.builder().device(device2).place(place).decibel(1D).build();
        Noise noiseB2 = Noise.builder().device(device2).place(place).decibel(2D).build();
        Noise noiseB3 = Noise.builder().device(device2).place(place).decibel(3D).build();

        noiseRepository.saveAll(Arrays.asList(noiseA1, noiseA2, noiseA3, noiseB1, noiseB2, noiseB3));

        //when
        mockMvc.perform(get(NOISE_URL + "/recent")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        ).andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.devices", hasSize(2)));
    }

    private Place aPlace() {
        return Place.builder()
                .tag("태그")
                .gridX("123")
                .gridY("321")
                .build();
    }
}