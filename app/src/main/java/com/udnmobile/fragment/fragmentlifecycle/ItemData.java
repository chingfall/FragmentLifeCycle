package com.udnmobile.fragment.fragmentlifecycle;

public class ItemData {

    //
    private  String title;
    private int imageUrl;

    //
    public ItemData(String title,int imageUrl){

        this.title = title;
        this.imageUrl = imageUrl;
    }

    //getter
    public String getTitle(){
        return title;
    }

    public Integer getImageUrl(){
        return imageUrl;
    }

    //setter
    public void setTitle(){
        this.title = title;
    }

    public void setImageUrl(){
        this.imageUrl = imageUrl;
    }

}
