package com.womens.womensassociation.models;

public class model {
    String title;
    String image_url;
    String image_name;

    public model(String title, String image_url, String image_name) {
        this.title = title;
        this.image_url = image_url;
        this.image_name = image_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    model(){};
}
