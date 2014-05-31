package at.peterzainzinger.wudu.android.social.core.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public class DateUtils {

    public static Calendar DateToCalendar(Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;

    }
}
