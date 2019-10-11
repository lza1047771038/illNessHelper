package wust.student.illnesshepler.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTheme {

    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public List<Posting> data;

}
