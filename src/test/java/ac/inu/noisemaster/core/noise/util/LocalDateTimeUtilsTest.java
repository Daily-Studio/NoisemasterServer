package ac.inu.noisemaster.core.noise.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateTimeUtilsTest {

    @DisplayName("날짜는 그대로 시간은 절삭 후 0으로 만들기")
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