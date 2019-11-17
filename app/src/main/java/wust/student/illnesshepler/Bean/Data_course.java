package wust.student.illnesshepler.Bean;

import android.net.Uri;

public class Data_course {

    public String Title,Author;
    public Uri uri;

    public Data_course(String Title,String Author,Uri uri){
        this.Title=Title;
        this.Author=Author;
        this.uri=uri;
    }

    public String getTitle(){return Title;}
    public String getAuthor(){return Author;}
    public Uri getUri(){return uri;}
}
