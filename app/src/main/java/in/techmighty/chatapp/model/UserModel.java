package in.techmighty.chatapp.model;

/**
 * Created by loginextuser14 on 31/05/16.
 */
public class UserModel {

    private String userName;
    private String name;
    private String favMessages;
    private String totalMessages;

    @Override
    public String toString() {
        return "UserModel{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", favMessages='" + favMessages + '\'' +
                ", totalMessages='" + totalMessages + '\'' +
                '}';
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

    public String getFavMessages() {
        return favMessages;
    }

    public void setFavMessages(String favMessages) {
        this.favMessages = favMessages;
    }

    public String getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(String totalMessages) {
        this.totalMessages = totalMessages;
    }
}
