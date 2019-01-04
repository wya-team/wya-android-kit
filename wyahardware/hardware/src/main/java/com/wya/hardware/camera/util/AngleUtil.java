package com.wya.hardware.camera.util;

 /**
  * @date: 2018/12/5 13:54
  * @author: Chunjiang Mao
  * @classname: AngleUtil
  * @describe: 角度工具类
  */

public class AngleUtil {
    private static int x_left = 4;
    private static int x_right = -4;
    private static int y_left = 7;
    private static int y_right = -7;
    public static int getSensorAngle(float x, float y) {
        if (Math.abs(x) > Math.abs(y)) {
            /**
             * 横屏倾斜角度比较大
             */
            if (x > x_left) {
                /**
                 * 左边倾斜
                 */
                return 270;
            } else if (x < x_right) {
                /**
                 * 右边倾斜
                 */
                return 90;
            } else {
                /**
                 * 倾斜角度不够大
                 */
                return 0;
            }
        } else {
            if (y > y_left) {
                /**
                 * 左边倾斜
                 */
                return 0;
            } else if (y < y_right) {
                /**
                 * 右边倾斜
                 */
                return 180;
            } else {
                /**
                 * 倾斜角度不够大
                 */
                return 0;
            }
        }
    }
}
