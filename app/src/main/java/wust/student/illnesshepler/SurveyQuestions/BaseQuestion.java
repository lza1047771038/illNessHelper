package wust.student.illnesshepler.SurveyQuestions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseQuestion {
    @SerializedName("title")
    public String title;
    @SerializedName("type")
    public String type;//类型比如20191019
}
