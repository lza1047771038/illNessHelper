package wust.student.illnesshepler.Utills;

import java.text.SimpleDateFormat;

public class Utils {

    /**
     * 毫秒字符串格式化为要求的时间（刚刚，3分钟前，3小时前。。。）
     *
     * @param temp
     * @return
     */
    public static String timeFormat(String temp) {
        int nd = 24 * 60 * 60 * 1000;
        int nh = 60 * 60 * 1000;
        int nm = 60 * 1000;
        int ns = 1000;
        long day, hour, minute;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        long oldtime = Long.parseLong(temp);

        long result = System.currentTimeMillis() - oldtime;

        day = result / nd;
        hour = result / nh % 24;
        minute = result / nm % 60;

        if (day == 0 && hour == 0 && minute < 5) {          //小于5分钟时
            return "刚刚";
        } else if (day == 0 && hour == 0 && minute > 5) {  //小于1小时
            return minute + "分钟前";
        } else if (day == 0 && hour != 0) {     //小于一天
            return hour + "小时前";
        } else if (day < 10) {              //小于10天
            return day + "天前";
        } else {                //10天以上
            return format.format(oldtime);
        }
    }
}
