package ac.inu.noisemaster.core.noise.util;

public final class DecibelUtils {
    public static double upperMinusOne(final double decibel) {
        return down(decibel) + 9;
    }

    public static double down(final double decibel) {
        double ceil = Math.ceil(decibel);
        double mod = Math.ceil(decibel % 10);
        return ceil - mod;
    }

}
