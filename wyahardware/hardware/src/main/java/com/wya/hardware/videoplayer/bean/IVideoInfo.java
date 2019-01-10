package com.wya.hardware.videoplayer.bean;

import java.io.Serializable;
 /**
  * @date: 2018/12/6 14:17
  * @author: Chunjiang Mao
  * @classname: IVideoInfo
  * @describe: 视频数据类请实现本接口
  */

public interface IVideoInfo extends Serializable {

    /**
     * 视频标题
     * @return
     */
    String getVideoTitle();

    /**
     * 视频播放路径 url / file path
     * @return
     */
    String getVideoPath();

}
