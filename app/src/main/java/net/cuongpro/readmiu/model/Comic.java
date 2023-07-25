package net.cuongpro.readmiu.model;

import com.google.gson.annotations.SerializedName;

public class Comic {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String tenChuyen;
    @SerializedName("story_desc")
    private String motaChuyen;
    @SerializedName("writer_name")
    private String tenTacGia;
    @SerializedName("publishing_year")
    private String namXuatBan;
    @SerializedName("cover_img")
    private String anhBia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenChuyen() {
        return tenChuyen;
    }

    public void setTenChuyen(String tenChuyen) {
        this.tenChuyen = tenChuyen;
    }

    public String getMotaChuyen() {
        return motaChuyen;
    }

    public void setMotaChuyen(String motaChuyen) {
        this.motaChuyen = motaChuyen;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public String getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(String namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public String getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(String anhBia) {
        this.anhBia = anhBia;
    }
}
