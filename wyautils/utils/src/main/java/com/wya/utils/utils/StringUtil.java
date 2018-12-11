package com.wya.utils.utils;

import java.util.Locale;
 /**
  * 创建日期：2018/12/6 11:25
  * 作者： Mao Chunjiang
  * 文件名称：StringUtil
  * 类说明：字符串工具类
  */

public class StringUtil {

    /**
     * 将毫秒值转化为时分秒显示
     * @param timeMs 毫秒值
     * @return
     */
    public static String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
    }
}
