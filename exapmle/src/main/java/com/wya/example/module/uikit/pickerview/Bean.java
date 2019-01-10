package com.wya.example.module.uikit.pickerview;

import java.util.List;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 城市bean类
 */
public class Bean {
    
    private List<DataBean> data;
    
    public List<DataBean> getData() {
        return data;
    }
    
    public void setData(List<DataBean> data) {
        this.data = data;
    }
    
    public static class DataBean {
        /**
         * name : 北京市
         * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区",
         * "房山区","通州区","顺义区","昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]}]
         */
        
        private String name;
        private List<CityBean> city;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public List<CityBean> getCity() {
            return city;
        }
        
        public void setCity(List<CityBean> city) {
            this.city = city;
        }
        
        public static class CityBean {
            /**
             * name : 北京市
             * area : ["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区",
             * "昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]
             */
            
            private String name;
            private List<String> area;
            
            public String getName() {
                return name;
            }
            
            public void setName(String name) {
                this.name = name;
            }
            
            public List<String> getArea() {
                return area;
            }
            
            public void setArea(List<String> area) {
                this.area = area;
            }
        }
    }
}
