package com.womens.womensassociation.models;

public class AboutUsModel {
    String image_url,describe,longitude,latitude;

    public AboutUsModel(String image_url, String describe, String longitude, String latitude) {
        this.image_url = image_url;
        this.describe = describe;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
