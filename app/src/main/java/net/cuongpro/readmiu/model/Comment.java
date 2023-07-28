package net.cuongpro.readmiu.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Comment {
    @SerializedName("_id")
    private String id;
    @SerializedName("id_comic")
    private String idTruyen;
    @SerializedName("id_user")
    private User user;
    @SerializedName("cmt_content")
    private String noiDungCmt;
    @SerializedName("cmt_date")
    private Date ngayCmt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(String idTruyen) {
        this.idTruyen = idTruyen;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNoiDungCmt() {
        return noiDungCmt;
    }

    public void setNoiDungCmt(String noiDungCmt) {
        this.noiDungCmt = noiDungCmt;
    }

    public Date getNgayCmt() {
        return ngayCmt;
    }

    public void setNgayCmt(Date ngayCmt) {
        this.ngayCmt = ngayCmt;
    }
}
