package wust.student.illnesshepler.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTheme {

    @SerializedName("total")
    public String status;
    @SerializedName("theme")
    public List<Posting> data;

}
