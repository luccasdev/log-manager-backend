package luccas.dev.logmanager.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class Dates {

    public static Date from(LocalDate date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date from(LocalDateTime date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String to(Date date, String format) {
        if (Objects.isNull(date)) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

}
