package com.htzh.htdxjk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Rechel
 */
public class TimeUtil {
    public static void main(String[] args) throws Exception {
        String date = "2019-03-05 01:00:00";

        calculateTimeDifferenceByCalendar(date);
    }

    /**
     * 计算当前时间与targetDate的时间差
     *
     * @param targetDate
     * @throws ParseException
     */
    public static Long calculateTimeDifferenceByCalendar(String targetDate) throws ParseException {
        Long minutes = 0L;
        try {
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
            /*分钟差*/
            Date target = simpleFormat.parse(targetDate);
            long nowd = (new Date()).getTime();
            long targetd = target.getTime();
            minutes = (nowd - targetd) / (1000 * 60 * 60 );
            System.out.println("两个时间之间的小时差为：" + minutes);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return minutes;
    }

}