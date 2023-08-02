package net.cuongpro.readmiu.model;

import com.google.gson.annotations.SerializedName;

public class Favorite {
    @SerializedName("_id")
    private String id;
    @SerializedName("id_user")
    private String idUser;
    @SerializedName("id_comic")
    private Comic comic;
    @SerializedName("favorite")
    private Boolean favorite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
