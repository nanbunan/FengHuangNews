package com.example.administrator.news.bean;

/**
 * Created by Administrator on 2017/4/20.
 */

public class BannerBean {
    private String[] image_url;
    private String[] image_title;
    private String[] toUrl;

    public BannerBean() {
    }

    public void setImage_url(String[] image_url) {
        this.image_url = image_url;
    }

    public void setImage_title(String[] image_title) {
        this.image_title = image_title;
    }

    public void setToUrl(String[] toUrl) {
        this.toUrl = toUrl;
    }

    public String[] getImage_url() {
        return image_url;
    }

    public String[] getToUrl() {
        return toUrl;
    }

    public String[] getImage_title() {
        return image_title;
    }
}
