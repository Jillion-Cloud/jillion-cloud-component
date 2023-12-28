package cloud.jillion.component.common.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public class DateUtils {

    public static String format(Date date, DateFormatEnum formatEnum) {
        return new SimpleDateFormat(formatEnum.getPattern()).format(date);
    }

    public static String formatTo0Minute(Date date) {
        Calendar beginDateCal = Calendar.getInstance();
        beginDateCal.setTime(date);
        beginDateCal.set(Calendar.HOUR_OF_DAY, 0);
        beginDateCal.set(Calendar.MINUTE, 0);
        beginDateCal.set(Calendar.SECOND, 0);
        return format(beginDateCal.getTime(), DateFormatEnum.PATTERN_0);
    }

    public static String formatTo59Minute(Date date) {
        Calendar beginDateCal = Calendar.getInstance();
        beginDateCal.setTime(date);
        beginDateCal.set(Calendar.HOUR_OF_DAY, 23);
        beginDateCal.set(Calendar.MINUTE, 59);
        beginDateCal.set(Calendar.SECOND, 59);
        return format(beginDateCal.getTime(), DateFormatEnum.PATTERN_0);
    }

    public static Date parse(String dateText, DateFormatEnum formatEnum) throws ParseException {
        return new SimpleDateFormat(formatEnum.getPattern()).parse(dateText);
    }
}
