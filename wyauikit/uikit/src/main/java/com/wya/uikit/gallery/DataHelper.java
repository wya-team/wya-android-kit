package com.wya.uikit.gallery;

import com.wya.uikit.imagepicker.LocalMedia;

import java.util.List;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 图片选择数据存放类
 */
public class DataHelper {
    private static final DataHelper DATA_HELPER = new DataHelper();
    private List<LocalMedia> mImages;
    private List<LocalMedia> mImageSelected;
    private List<String> mCropList;
    private String value;
    
    private DataHelper() {
    
    }
    
    public static DataHelper getInstance() {
        return DATA_HELPER;
    }
    
    public List<LocalMedia> getImages() {
        return mImages;
    }
    
    public void setImages(List<LocalMedia> images) {
        mImages = images;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public List<LocalMedia> getImageSelected() {
        return mImageSelected;
    }
    
    public void setImageSelected(List<LocalMedia> imageSelected) {
        mImageSelected = imageSelected;
    }
    
    public List<String> getCropList() {
        return mCropList;
    }
    
    public void setCropList(List<String> cropList) {
        mCropList = cropList;
    }
}
