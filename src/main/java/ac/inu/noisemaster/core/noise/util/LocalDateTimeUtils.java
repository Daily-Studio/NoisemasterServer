package ac.inu.noisemaster.core.noise.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeUtils {

    private static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String SPACE = " ";
    private static final int DATE = 0;
    private static final String ZERO_TIME = " 00:00:00";

    public static LocalDateTime convertZeroTime(String date) {
        return LocalDateTime.parse(changeTimeToZero(date), YYYY_MM_DD);
    }

    private static String changeTimeToZero(String date) {
        String[] dates = date.split(SPACE);
        return dates[DATE] + ZERO_TIME;
    }

}
