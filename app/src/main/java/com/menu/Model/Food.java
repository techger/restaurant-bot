package com.menu.Model;

/**
 * Created by tsenguun on 4/30/2016.
 */

public class Food {
    private String name ;
    private String une;
    private String turul;
    private String hemjee;
    private String imageName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUne() {
        return une;
    }

    public void setUne(String une) {
        this.une = une;
    }

    public String getTurul() {
        return turul;
    }

    public void setTurul(String turul) {
        this.turul = turul;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getHemjee() {
        return hemjee;
    }

    public void setHemjee(String hemjee) {
        this.hemjee = hemjee;
    }

    public Food(String name, String une, String turul, String hemjee, String imageName) {
        this.name = name;
        this.une = une;
        this.turul = turul;
        this.hemjee = hemjee;
        this.imageName = imageName;

    }
}
