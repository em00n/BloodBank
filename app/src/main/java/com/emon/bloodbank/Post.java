package com.emon.bloodbank;

public class Post {
    private String name,mobile,disc,blood;

    public Post() {
    }

    public Post(String name, String mobile, String disc, String blood) {
        this.name = name;
        this.mobile = mobile;
        this.disc = disc;
        this.blood = blood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }



    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }
}
