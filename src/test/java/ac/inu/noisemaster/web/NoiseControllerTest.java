package ac.inu.noisemaster.web;

import ac.inu.noisemaster.core.noise.domain.model.Device;
import ac.inu.noisemaster.core.noise.domain.model.Noise;
import ac.inu.noisemaster.core.noise.domain.model.Place;
import ac.inu.noisemaster.core.noise.domain.repository.PlaceRepository;
import ac.inu.noisemaster.core.noise.domain.repository.device.DeviceRepository;
import ac.inu.noisemaster.core.noise.domain.repository.noise.NoiseRepository;
import ac.inu.noisemaster.core.noise.dto.place.PlaceTagUpdateReqDTO;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class NoiseControllerTest {

    private static final String NOISE_URL = "/api/v1/noise";
    private static final Gson gson = new Gson();

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
        noiseRepository.flush();

        //when
        mockMvc.perform(get(NOISE_URL + "/recent")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        ).andDo(print())
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.devices", hasSize(2)));
    }

    @DisplayName("tag 수정하기")
    @Test
    void test4() throws Exception {
        //given
        Place place = placeRepository.saveAndFlush(aPlace(null));
        Device device = deviceRepository.saveAndFlush(aDevice("디바이스 1"));
        Noise noise = Noise.builder().device(device).place(place).build();
        noiseRepository.saveAndFlush(noise);

        //when
        String changeTag = "태그";
        PlaceTagUpdateReqDTO placeTagUpdateReqDTO = new PlaceTagUpdateReqDTO(place.getId(), changeTag);

        //then
        assertThat(place.getTag()).isBlank();

        mockMvc.perform(put(NOISE_URL + "/place/tag")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(gson.toJson(placeTagUpdateReqDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tag", is(changeTag)));
    }

    @DisplayName("페이징 결과")
    @Test
    void name1() throws Exception {
        //given
        Place place = placeRepository.saveAndFlush(aPlace());
        Device device1 = deviceRepository.saveAndFlush(aDevice("device 1"));

        Noise noiseA1 = Noise.builder().device(device1).place(place).decibel(1D).build();
        Noise noiseA2 = Noise.builder().device(device1).place(place).decibel(2D).build();
        Noise noiseA3 = Noise.builder().device(device1).place(place).decibel(3D).build();
        Thread.sleep(10);
        Noise noiseB1 = Noise.builder().device(device1).place(place).decibel(4D).build();
        Noise noiseB2 = Noise.builder().device(device1).place(place).decibel(5D).build();
        Noise noiseB3 = Noise.builder().device(device1).place(place).decibel(6D).build();

        noiseRepository.save(noiseA1);
        noiseRepository.save(noiseA2);
        noiseRepository.save(noiseA3);
        noiseRepository.save(noiseB1);
        noiseRepository.save(noiseB2);
        noiseRepository.save(noiseB3);
        noiseRepository.flush();

        //when
        mockMvc.perform(get(NOISE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("size", "50")
        ).andDo(print())
                //then
                .andExpect(status().isOk());
    }
}