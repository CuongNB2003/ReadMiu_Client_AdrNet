package net.cuongpro.readmiu.model.model_api;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("msg")
    private String msg;
    @SerializedName("checkLogin")
    private Boolean check;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
