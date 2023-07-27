package net.cuongpro.readmiu.model.model_api;

import com.google.gson.annotations.SerializedName;

import net.cuongpro.readmiu.model.Comic;
import net.cuongpro.readmiu.model.Comment;

public class GetCommet {
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private Comment[] comments;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }
}
