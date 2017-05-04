package com.example.administrator.news.bean;

/**
 * Created by Administrator on 2017/4/20.
 */

public class Data {
    private String thumbnail;
    private String title;
    private String url;

    public Data() {}

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {

        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
