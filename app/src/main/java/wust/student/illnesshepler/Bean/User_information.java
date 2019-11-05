package wust.student.illnesshepler.Bean;

import android.net.Uri;
import org.litepal.crud.LitePalSupport;

import java.net.URI;

public class User_information extends LitePalSupport {
    private String User_Name, User_Age,User_Image_Uri=null;
    private int id;


    public int get_Id(){
        return id;
    }


    public void setUser_Image_Uri(String user_Image_Uri) {
        this.User_Image_Uri = user_Image_Uri;
    }

    public void setUser_Name(String name) {
        this.User_Name = name;
    }

    public void setUser_Age(String age){
        this.User_Age=age;
    }

    public String getUser_Image_Uri(){
        return User_Image_Uri;
    }

    public String getUser_Name(){
        return User_Name;
    }

    public String getUser_Age(){
        return User_Age;
    }

    public String toString(){
        return "User_Information{"+
                "Image_Uri="+User_Image_Uri+'/'+
                ",name="+User_Name+'/'+
                "age="+User_Age+'/'+
                '}';
    }

}

