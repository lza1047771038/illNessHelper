package wust.student.illnesshepler.Bean;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class Posting {
    @SerializedName("theme_id")
    public BigInteger theme_id; //主题id	示例如：19100822231 年（后两位） 月 日 时 分 秒
    @SerializedName("author_id_id")
    public BigInteger author_id;//发帖人id	外键约束参照UserInfo表 userid
    @SerializedName("contains")
    public String contains;//主题内容
    @SerializedName("time")
    public String time;//时间  示例同themeid
    @SerializedName("likes")
    public int likes;//点赞数
    @SerializedName("comments_num")
    public int comments_num;//评论数

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigInteger getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(BigInteger theme_id) {
        this.theme_id = theme_id;
    }

    public BigInteger getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(BigInteger author_id) {
        this.author_id = author_id;
    }

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }



    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments_num() {
        return comments_num;
    }

    public void setComments_num(int comments_num) {
        this.comments_num = comments_num;
    }
}
