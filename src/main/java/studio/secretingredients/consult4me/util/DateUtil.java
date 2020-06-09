package studio.secretingredients.consult4me.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Date helper methods
 *
 * @author corbandalas - created 27.02.2017
 * @since 0.1.0
 */
public class DateUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Constant for date format {@value}
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private static final ThreadLocal<DateFormat> threadLocalDateFormat
            = new ThreadLocal<DateFormat>() {
        /**
         * @see ThreadLocal#initialValue()
         */
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.ENGLISH);
        }
    };


    /**
     * Return a date formated in yyyy-MM-dd format.
     *
     * @param date    to format
     * @param pattern format pattern
     * @return the formatted date.
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) threadLocalDateFormat.get();

        if (StringUtils.isNotBlank(pattern)) {
            simpleDateFormat.applyPattern(pattern);
        }

        return threadLocalDateFormat.get().format(date);
    }

    /**
     * Parse date.
     *
     * @param datePresentation the date presentation
     * @param pattern          the pattern
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date parse(String datePresentation, String pattern) throws ParseException {
        if (!StringUtils.isNotBlank(datePresentation)) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) threadLocalDateFormat.get();

        if (StringUtils.isNotBlank(pattern)) {
            simpleDateFormat.applyPattern(pattern);
        }

        return threadLocalDateFormat.get().parse(datePresentation);
    }

    public static Date checkDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.MILLISECOND) == 0)
            calendar.set(Calendar.MILLISECOND, 1);
        return calendar.getTime();
    }

    public static long getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime().getTime();
    }

    public static long getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }

    public static XMLGregorianCalendar toXmlGregorianCalendar(Date date) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);

        XMLGregorianCalendar date2 = null;

        try {
            date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

            return date2;
        } catch (DatatypeConfigurationException e) {
        }

        return date2;
    }


}
