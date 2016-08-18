package com.bangunmediasejahtera.wartaplus.model;

public class article {
    private int ID;
    String title, featured_image_Url;
    String content;
    String date;



    public article(){
    }

    public article(int ID, String name, String content, String date, String featured_image_Url) {
        this.ID = ID;
        this.title = name;
        this.content = content;
        this.date = date;
        this.featured_image_Url = featured_image_Url;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeatured_image_Url() {
        return featured_image_Url;
    }

    public void setFeatured_image_Url(String featured_image_Url) {
        this.featured_image_Url = featured_image_Url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
