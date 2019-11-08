package wust.student.illnesshepler.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTweetComments {
    @SerializedName("comments")
    public List<Comments> data;

    public static class Comments {
        /**
         * id : 27
         * theme_id : NFT20191107195403
         * time : 20191108180706
         * person_id : 13972008325
         * contains : 哈哈哈哈
         * likes : 0
         * replies : 0
         * comments_num : 0
         * username : 老刘
         * userimage : null
         */
        @SerializedName("id")
       public int id;
        @SerializedName("theme_id")
       public String theme_id;
        @SerializedName("time")
       public String time;
        @SerializedName("person_id")
       public long person_id;
        @SerializedName("contains")
       public String contains;
        @SerializedName("likes")
       public int likes;
        @SerializedName("replies")
       public int replies;
        @SerializedName("comments_num")
       public int comments_num;
        @SerializedName("username")
       public String username;
        @SerializedName("userimage")
       public String userimage;
    }
}
