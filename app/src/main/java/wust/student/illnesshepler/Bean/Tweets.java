package wust.student.illnesshepler.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tweets {
    @SerializedName("data")
    public List<Tweets.Item> data;

    public static class Item {

        @SerializedName("id")
        public String id;
        @SerializedName("themeid")
        public String themeid;
        @SerializedName("title")
        public String title;
        @SerializedName("contains")
        public String contains;
        @SerializedName("author_id_id")
        public String auther;
        @SerializedName("post_time")
        public String post_time;
        @SerializedName("image")
        public String imageUrl;
        @SerializedName("number")
        public int visitNum;
        @SerializedName("username")
        public String username;
    }
}
