package net.cuongpro.readmiu.model.model_api;

import com.google.gson.annotations.SerializedName;

import net.cuongpro.readmiu.model.Comic;

public class GetListPhoto {
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private String[] photoLists;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String[] getPhotoLists() {
        return photoLists;
    }

    public void setPhotoLists(String[] photoLists) {
        this.photoLists = photoLists;
    }
}
