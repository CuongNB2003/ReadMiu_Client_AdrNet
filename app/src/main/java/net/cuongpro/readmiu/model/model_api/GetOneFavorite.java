package net.cuongpro.readmiu.model.model_api;

import com.google.gson.annotations.SerializedName;

public class GetOneFavorite {
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private Boolean status;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
