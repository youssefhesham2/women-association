package com.womens.womensassociation.models;

import java.util.ArrayList;
import java.util.List;

public  class  HomePageTowModel {
    String youtube2,detials;
    List<String> imagesurls=new ArrayList<>();
    public HomePageTowModel(String youtube2, String detials) {
        this.youtube2 = youtube2;
        this.detials = detials;
        this.imagesurls.clear();

        //  this.imagesurls = strings;
    }


    public String getYoutube2() {
        return youtube2;
    }

    public void setYoutube2(String youtube2) {
        this.youtube2 = youtube2;
    }

    public String getDetials() {
        return detials;
    }

    public void setDetials(String detials) {
        this.detials = detials;
    }

    public List<String> getImagesurls() {
        return imagesurls;
    }

     public  void setImagesurls(String imagesurls) {
        this.imagesurls.add(imagesurls);
    }
}
