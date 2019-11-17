package wust.student.illnesshepler.Bean.SurveyQuestions;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import wust.student.illnesshepler.Bean.SurveyQuestions.BaseQuestion;

public class MutipleQuestion extends BaseQuestion implements Serializable {
    @SerializedName("selectionA")
    public String optiona;
    @SerializedName("selectionB")
    public String optionb;
    @SerializedName("selectionC")
    public String optionc;
    @SerializedName("selectionD")
    public String optiond;
    @SerializedName("selectionE")
    public String optione;
    @SerializedName("selectionF")
    public String optionf;
    @SerializedName("selectionG")
    public String optiong;
    @SerializedName("selectionH")
    public String optionh;
    @SerializedName("selectionI")
    public String optioni;
    @SerializedName("selectionJ")
    public String optionj;

}
