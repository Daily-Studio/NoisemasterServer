package ac.inu.noisemaster.core.noise.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecibelUtilsTest {

    @Test
    void floorDecibel() {
        //given
        double decibel = 11.2;

        //when
        //then
        assertThat(DecibelUtils.upperMinusOne(decibel)).isEqualTo(19);
    }
}