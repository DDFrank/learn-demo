package io.frank.spring.jooq.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * .Description: 封装 Joda-time 类库的时间日期工具类 Author: 金君良 Date: 2018/11/7 0007 下午 3:26
 */
@Slf4j
public class DateUtils {
    private static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter HM = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter HMS = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter YM = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final DateTimeFormatter YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter YMDHMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String[] NUM = {"一", "二", "三", "四", "五", "六", "日"};

    private DateUtils() {
    }

    public static LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    public static String getNowString() {
        return formatDateTime(getNow());
    }

    /**
     * 按指定次数分割 两个 LocalTime 之间的间隔
     *
     * @return 分割后 开始和结束时间的list组合而成的list
     */
//    public static List<List<LocalTime>> splitLocalTime(LocalTime startTime, LocalTime endTime,
//                                                       int times) {
//        List<List<LocalTime>> resultList = new ArrayList<>();
//        // 得到按次数分割后的分钟数，存在余数的可能性, 该余数需要加到最后一次的时间段上
//        int[] plusMinutes = getSplitEndTimeList(startTime, endTime, times);
//
//        for (int i = 0; i < plusMinutes.length; i++) {
//            LocalTime mutableStartTime;
//            LocalTime mutableEndTime;
//            if (i == 0) {
//                mutableStartTime = startTime;
//                mutableEndTime = startTime.plusMinutes(plusMinutes[0]);
//            } else if (i == plusMinutes.length - 1) {
//                mutableStartTime = startTime.plusMinutes(plusMinutes[i - 1]);
//                mutableEndTime = endTime;
//            } else {
//                mutableStartTime = startTime.plusMinutes(plusMinutes[i - 1]);
//                mutableEndTime = startTime.plusMinutes(plusMinutes[i]);
//            }
//            resultList.add(Lists.newArrayList(mutableStartTime, mutableEndTime));
//        }
//
//        return resultList;
//    }

//    /**
//     * 计算应该有多少个结束时间
//     *
//     * @param startTime 开始时间
//     * @param endTime   结束时间
//     * @param times     分割的时间段
//     * @return 得到分割后的时间段距离开始时间的分钟数的列表
//     */
//    public static int[] getSplitEndTimeList(LocalTime startTime, LocalTime endTime, int times) {
//        int totalMinutes = endTime.get(DateTimeFieldType.minuteOfDay()) -
//                startTime.get(DateTimeFieldType.minuteOfDay());
//
//        float duration = (float) totalMinutes / times;
//        int[] plusTimes = new int[times];
//        for (int i = 0; i < times; i++) {
//            plusTimes[i] = Math.round(duration * (i + 1));
//        }
//
//        return plusTimes;
//    }

//    public static int getSplitTimeMinutes(LocalTime startTime, LocalTime endTime, int times) {
//        int totalMinutes = endTime.get(DateTimeFieldType.minuteOfDay()) -
//                startTime.get(DateTimeFieldType.minuteOfDay());
//
//        return totalMinutes / times;
//    }
    public static String formatDate(LocalDate localDate) {
        return localDate.format(YMD);
    }

    public static String formatDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(YMDHMS);
    }

    public static String formatTime(LocalTime localTime) {
        return localTime.format(HM);
    }

    public static String formatTime(Object obj) {
        if (Objects.isNull(obj)) {
            return "";
        }
        if (obj instanceof Date) {
            return formatTime((Date) obj);
        } else if (obj instanceof BigInteger) {
            return formatTime((BigInteger) obj);
        }
        return "";
    }

    public static String formatTime(Long timeStamp) {
        if (Objects.isNull(timeStamp)) {
            return "";
        }

        return formatTime(new Date(timeStamp));
    }

    public static LocalTime getLocalTime(String target) {
        if (StringUtils.isEmpty(target)) {
            return null;
        } else {
            return LocalTime.parse(target, HM);
        }
    }

    /*
     * 前者的时间不小于后者
     * */
    public static boolean ifNotBefore(LocalTime localTime, LocalTime localTimeToBeCompared) {
        return localTime.compareTo(localTimeToBeCompared) >= 0;
    }

    /*
     * 前者的时间不大于后者
     * */
    public static boolean ifNotAfter(LocalTime localTime, LocalTime localTimeToBeCompared) {
        return localTime.compareTo(localTimeToBeCompared) <= 0;
    }

    /*
     * 获取今天是周几， 1-7 对应周一至周日
     * */
    public static int getWeek() {
        return LocalDateTime.now().getDayOfWeek().getValue();
    }

    public static boolean isBefore(LocalDate localDate, LocalDate localDateToBeCompared) {
        if (localDate == null || localDateToBeCompared == null) {
            throw new IllegalArgumentException("Partial cannot be null");
        } else {
            return localDate.compareTo(localDateToBeCompared) <= 0;
        }
    }

    public static boolean isAfter(LocalDate localDate, LocalDate localDateToBeCompared) {
        if (localDate == null || localDateToBeCompared == null) {
            throw new IllegalArgumentException("Partial cannot be null");
        } else {
            return localDate.compareTo(localDateToBeCompared) >= 0;
        }
    }


    public static List<String> getShiftDaysList(String shift) {
        List<String> list = new ArrayList<>();
        if (shift.length() == 7) {
            list.add("每天");
        } else {
            char[] chars = shift.toCharArray();
            for (char one : chars) {
                list.add("周" + NUM[Integer.parseInt(String.valueOf(one)) - 1]);
            }
        }
        return list;
    }

    /**
     * 判断某个日期是不是今天
     *
     * @param date 被判断日期
     * @return 是否是今天
     */
    public static boolean isToday(Date date) {
        return true;
    }

    /**
     * 获取今天的时间
     *
     * @return 今天
     */
    public static LocalDate getTodayDate() {
        return LocalDate.now();
    }

    /**
     * 获取明天的时间
     *
     * @return 明天
     */
    public static LocalDate getTomorrowDate() {
        return LocalDate.now().plusDays(1);
    }
}
