package ac.inu.noisemaster.web;

import ac.inu.noisemaster.core.noise.domain.Noise;
import ac.inu.noisemaster.core.noise.domain.Place;
import ac.inu.noisemaster.core.noise.repository.NoiseRepository;
import ac.inu.noisemaster.core.noise.repository.PlaceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        noiseRepository.deleteAll();
        placeRepository.deleteAll();
    }

    @Test
    void findNoiseAdvance() throws Exception {
        //given
        Place place = Place.builder().gridX("123").gridY("123").tag("device1").build();
        place = placeRepository.saveAndFlush(place);

        Noise noise = Noise.builder().device("device1").place(place).build();
        noiseRepository.saveAndFlush(noise);

        //when
        MvcResult mvcResult = mockMvc.perform(get(NOISE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .requestAttr("device", "device1")
//        .requestAttr("decibel")
//        .requestAttr("temperature")
//        .requestAttr("tag")
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
    }

    @Test
    void saveNoise() {
        //given
        //when
        //then
    }
}