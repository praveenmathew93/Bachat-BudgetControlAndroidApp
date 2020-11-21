package com.example.Bachat;

public class Category {
    private String name;
    private int imgURL;
    // private String birthday;
    //private String sex;


    /*public Category(String name, String birthday, String sex, String imgURL) {
       this.birthday = birthday;
        this.name = name;
        this.sex = sex;
        this.imgURL = imgURL;
    }*/

// test commit
    public Category(String name,int imgURL) {
        //   this.birthday = birthday;
        this.name = name;
        // this.sex = sex;
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgURL() {
        return imgURL;
    }

    public void setImgURL(int imgURL) {
        this.imgURL = imgURL;
    }

    /*public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }*/


}