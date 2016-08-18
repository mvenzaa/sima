package com.bangunmediasejahtera.wartaplus.model;

/**
 * Created by ASUS on 7/29/2016.
 */
public class comment {

    private int ID;
    String name;
    String url;
    String date;
    String avatar;
    String content;


    public comment(){
    }

    public comment(int ID, String name, String url, String date, String avatar,String content) {
        this.ID = ID;
        this.name = name;
        this.url = url;
        this.date = date;
        this.avatar = avatar;
        this.content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
