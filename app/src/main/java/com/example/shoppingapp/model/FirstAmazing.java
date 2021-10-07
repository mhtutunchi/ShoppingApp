package com.example.shoppingapp.model;

public class FirstAmazing {

    private String title , link_img;

    public FirstAmazing(String title, String link_img) {
        this.title = title;
        this.link_img = link_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink_img() {
        return link_img;
    }

    public void setLink_img(String link_img) {
        this.link_img = link_img;
    }
}
