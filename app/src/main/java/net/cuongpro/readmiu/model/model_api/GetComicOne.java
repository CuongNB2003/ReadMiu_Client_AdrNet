package net.cuongpro.readmiu.model.model_api;

import com.google.gson.annotations.SerializedName;

import net.cuongpro.readmiu.model.Comic;

public class GetComicOne {
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private Comic comic;
    @SerializedName("sum")
    private int sumFavorite;


    public int getSumFavorite() {
        return sumFavorite;
    }

    public void setSumFavorite(int sumFavorite) {
        this.sumFavorite = sumFavorite;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }
}
