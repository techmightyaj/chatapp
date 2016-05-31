package in.techmighty.chatapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by loginextuser14 on 30/05/16.
 */
public class ChatModel {

    @SerializedName("count")
    private int chatCount;

    @SerializedName("messages")
    private List<MessageModel> messageModelList;

    @Override
    public String toString() {
        return "ChatModel{" +
                "chatCount=" + chatCount +
                ", messageModelList=" + messageModelList +
                '}';
    }

    public int getChatCount() {
        return chatCount;
    }

    public void setChatCount(int chatCount) {
        this.chatCount = chatCount;
    }

    public List<MessageModel> getMessageModelList() {
        return messageModelList;
    }

    public void setMessageModelList(List<MessageModel> messageModelList) {
        this.messageModelList = messageModelList;
    }
}
