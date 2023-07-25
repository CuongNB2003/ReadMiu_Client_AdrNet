package net.cuongpro.readmiu.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Comment {
    @SerializedName("_id")
    private String id;
    @SerializedName("id_comic")
    private String idTruyen;
    @SerializedName("id_user")
    private String idUser;
    @SerializedName("cmt_content")
    private String noiDungCmt;
    @SerializedName("cmt_date")
    private String ngayCmt;
}
