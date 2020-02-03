package ac.inu.noisemaster.core.noise;

import ac.inu.noisemaster.core.noise.util.LocalDateTimeUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateTimeUtilsTest {

    @Test
    void convertZeroTime() {
        //given
        String date = "2020-01-01 12:12:12";
        //when
        LocalDateTime localDateTime = LocalDateTimeUtils.convertZeroTime(date);
        //then
        System.out.println(localDateTime);
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
    }
}