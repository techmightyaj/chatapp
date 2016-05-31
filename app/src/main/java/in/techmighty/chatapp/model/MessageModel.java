package in.techmighty.chatapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by loginextuser14 on 30/05/16.
 */
public class MessageModel {

    @SerializedName("body")
    private String body;

    @SerializedName("username")
    private String userName;

    @SerializedName("Name")
    private String name;

    @SerializedName("image-url")
    private String imgUrl;

    @SerializedName("message-time")
    private String msgTime;

    private boolean favMsg;


    @Override
    public String toString() {
        return "MessageModel{" +
                "body='" + body + '\'' +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", msgTime='" + msgTime + '\'' +
                ", favMsg=" + favMsg +
                '}';
    }

    public boolean isFavMsg() {
        return favMsg;
    }

    public void setFavMsg(boolean favMsg) {
        this.favMsg = favMsg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }
}
