package com.example.shoppingapp.model;

public class ReviewProduct {

    private String id , id_product , header_text , link_img , final_text;

    public ReviewProduct() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getHeader_text() {
        return header_text;
    }

    public void setHeader_text(String header_text) {
        this.header_text = header_text;
    }

    public String getLink_img() {
        return link_img;
    }

    public void setLink_img(String link_img) {
        this.link_img = link_img;
    }

    public String getFinal_text() {
        return final_text;
    }

    public void setFinal_text(String final_text) {
        this.final_text = final_text;
    }
}
