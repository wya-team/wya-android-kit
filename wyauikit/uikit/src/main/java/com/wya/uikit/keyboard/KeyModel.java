package com.wya.uikit.keyboard;

 /**
  * 创建日期：2018/12/26 16:31
  * 作者： Mao Chunjiang
  * 文件名称：KeyModel
  * 类说明：
  */

public class KeyModel {
    private Integer code;
    private String label;
    public KeyModel(Integer code, String lable){
        this.code = code;
        this.label = lable;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLable() {
        return label;
    }

    public void setLable(String lable) {
        this.label = lable;
    }

}
