package com.sibermediaabadi.wartaplus.model;

public class foto {
    private int ID;
    String title, image_small_Url, thumbnail1,thumbnail2, thumbnail3, thumbnail4 ;
    String content;
    String created_at;



    public foto(){
    }

    public foto(int ID, String name, String content, String created_at, String image_small_Url, String thumbnail1, String thumbnail2, String thumbnail3, String thumbnail4) {
        this.ID = ID;
        this.title = name;
        this.content = content;
        this.created_at = created_at;
        this.image_small_Url = image_small_Url;
        this.thumbnail1 = thumbnail1;
        this.thumbnail2 = thumbnail2;
        this.thumbnail3 = thumbnail3;
        this.thumbnail4 = thumbnail4;

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

    public String getImage_small_Url() {
        return image_small_Url;
    }

    public void setImage_small_Url(String image_small_Url) {
        this.image_small_Url = image_small_Url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getThumbnail1() {
        return thumbnail1;
    }

    public void setThumbnail1(String thumbnail1) {
        this.thumbnail1 = thumbnail1;
    }

    public String getThumbnail2() {
        return thumbnail2;
    }

    public void setThumbnail2(String thumbnail2) {
        this.thumbnail2 = thumbnail2;
    }

    public String getThumbnail3() {
        return thumbnail3;
    }

    public void setThumbnail3(String thumbnail3) {
        this.thumbnail3 = thumbnail3;
    }

    public String getThumbnail4() {
        return thumbnail4;
    }

    public void setThumbnail4(String thumbnail4) {
        this.thumbnail4 = thumbnail4;
    }
}
