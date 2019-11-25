package wust.student.illnesshepler.Bean;

import android.net.Uri;

import org.litepal.crud.LitePalSupport;

import java.net.URI;

public class User_information extends LitePalSupport {

    public boolean isLogin = false;
    private String userId;
    private String User_Name;
    public String password;
    public String phoneid;
    private int User_Age;
    public String User_Image_Uri=null;
    private int id;
    public int userType;
    public int type;
    public int user_coin;

    public String getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(String phoneid) {
        this.phoneid = phoneid;
    }

    public int get_Id() {
        return id;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_coin() {
        return user_coin;
    }

    public void setUser_coin(int user_coin) {
        this.user_coin = user_coin;
    }

    public void setUser_Image_Uri(String user_Image_Uri) {
        this.User_Image_Uri = user_Image_Uri;
    }

    public void setUser_Name(String name) {
        this.User_Name = name;
    }

    public void setUser_Age(int age) {
        this.User_Age = age;
    }

    public String getUser_Image_Uri() {
        return User_Image_Uri;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public int getUser_Age() {
        return User_Age;
    }

    @Override
    public String toString() {
        return "User_information{" +
                "isLogin=" + isLogin +
                ", userId='" + userId + '\'' +
                ", User_Name='" + User_Name + '\'' +
                ", password='" + password + '\'' +
                ", phoneid='" + phoneid + '\'' +
                ", User_Age='" + User_Age + '\'' +
                ", User_Image_Uri='" + User_Image_Uri + '\'' +
                ", id=" + id +
                ", userType=" + userType +
                ", type=" + type +
                ", user_coin=" + user_coin +
                '}';
    }
}

