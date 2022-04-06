package com.example.shoppingapp.model;

public class Order {

    private String cart_id , id_product , name , link_img , price , token_pay_off;

    public Order() {
    }

    public String getToken_pay_off() {
        return token_pay_off;
    }

    public void setToken_pay_off(String token_pay_off) {
        this.token_pay_off = token_pay_off;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink_img() {
        return link_img;
    }

    public void setLink_img(String link_img) {
        this.link_img = link_img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

