package net.cuongpro.readmiu.model.model_api;

import com.google.gson.annotations.SerializedName;

import net.cuongpro.readmiu.model.Comic;
import net.cuongpro.readmiu.model.Favorite;

public class GetFavorite {
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private Favorite[] favorites;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Favorite[] getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorite[] favorites) {
        this.favorites = favorites;
    }
}
