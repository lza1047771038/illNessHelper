package wust.student.illnesshepler.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SingleQuestion extends BaseQuestion implements Serializable {

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

    @SerializedName("A_next")
    public int A_next;
    @SerializedName("B_next")
    public int B_next;
    @SerializedName("C_next")
    public int C_next;
    @SerializedName("D_next")
    public int D_next;
    @SerializedName("E_next")
    public int E_next;
    @SerializedName("F_next")
    public int F_next;
    @SerializedName("G_next")
    public int G_next;
    @SerializedName("H_next")
    public int H_next;
    @SerializedName("I_next")
    public int I_next;
    @SerializedName("J_next")
    public int J_next;

}
