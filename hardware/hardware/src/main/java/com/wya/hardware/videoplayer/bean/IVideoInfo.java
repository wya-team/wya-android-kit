package com.wya.hardware.videoplayer.bean;

import java.io.Serializable;
 /**
  * 创建日期：2018/12/6 14:17
  * 作者： Mao Chunjiang
  * 文件名称：IVideoInfo
  * 类说明：视频数据类请实现本接口
  */

public interface IVideoInfo extends Serializable {

    /**
     * 视频标题
     */
    String getVideoTitle();

    /**
     * 视频播放路径 url / file path
     */
    String getVideoPath();

}
