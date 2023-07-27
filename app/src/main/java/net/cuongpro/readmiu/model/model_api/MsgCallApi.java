package net.cuongpro.readmiu.model.model_api;

import com.google.gson.annotations.SerializedName;

public class MsgCallApi {
    @SerializedName("msg")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
