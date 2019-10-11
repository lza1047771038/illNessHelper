package wust.student.illnesshepler.Bean;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class Posting {
    @SerializedName("theme_id")
    public String theme_id; //主题id	示例如：19100822231 年（后两位） 月 日 时 分 秒
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
}
