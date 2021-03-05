package com.xinlan.android.basesupport.entity;

import java.util.List;

/**
 * @author: 63239
 * @date: 2021/1/10
 */
public class HomeMenuEntity {

    private String menu_type;//跳转类型
    private String name;//下方标题名
    private String imageUrl;//图片网络地址
    private String url;//跳转url
    private String androidClass;//跳转安卓活动名
    private String param;//其他参数

    private int localImage;//本地图片

    public String getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAndroidClass() {
        return androidClass;
    }

    public void setAndroidClass(String androidClass) {
        this.androidClass = androidClass;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public int getLocalImage() {
        return localImage;
    }

    public void setLocalImage(int localImage) {
        this.localImage = localImage;
    }
}
