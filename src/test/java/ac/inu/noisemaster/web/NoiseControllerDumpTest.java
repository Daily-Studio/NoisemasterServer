package ac.inu.noisemaster.web;

import ac.inu.noisemaster.core.noise.dto.device.DeviceRecentBundleResDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ActiveProfiles("dump")
@SpringBootTest
@ExtendWith(SpringExtension.class)
class NoiseControllerDumpTest {

    private static final String NOISE_URL = "/api/v1/noise";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final Logger log = LoggerFactory.getLogger(NoiseControllerDumpTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("검색조건 없이 검색하기")
    @Test
    void name() throws Exception {
        //given

        //when
        mockMvc.perform(get(NOISE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        ).andDo(print())
                //then
                .andExpect(status().isOk());

    }


    @DisplayName("device 검색 조건으로 검색하기")
    @Test
    void findNoiseAdvance() throws Exception {
        //given

        //when
        mockMvc.perform(get(NOISE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("device", "device1")
        ).andDo(print())
                //then
                .andExpect(status().isOk());
    }

    @DisplayName("decibel 10단위 검색")
    @Test
    void test2() throws Exception {
        //given

        //when
        mockMvc.perform(get(NOISE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("decibel", "11D")
        ).andDo(print())
                //then
                .andExpect(status().isOk());

    }

    @DisplayName("디바이스별 가장 최근 소음정보들 하나씩 노출")
    @Test
    void test3() throws Exception {
        LocalDateTime start = LocalDateTime.now();

        //when
        MvcResult mvcResult = mockMvc.perform(get(NOISE_URL + "/recent")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        ).andDo(print())
                //then
                .andExpect(status().isOk())
                .andReturn();

        LocalDateTime end = LocalDateTime.now();
        log.info("\n시작 시간 : {}초\n 종료 시간 : {}초\n 소요 시간 : {}초", start, end,
                (Timestamp.valueOf(end).getTime() - Timestamp.valueOf(start).getTime()) / 1000D);

        DeviceRecentBundleResDTO deviceRecentBundleResDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), DeviceRecentBundleResDTO.class);
        log.info("{}", gson.toJson(deviceRecentBundleResDTO));
    }

}