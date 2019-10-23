package wust.student.illnesshepler.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Test {
    @SerializedName("title")
    public String title;
    @SerializedName("type")
    public String type;//类型比如20191019
    //    public List<BaseQuestion> data;
    @SerializedName("SingleQuestion")
    public List<SingleQuestion> data1;
    @SerializedName("MutipleQuestion")
    public List<MutipleQuestion> data2;
    @SerializedName("ManualQuestion")
    public List<ManualQuestion>data3;
}
