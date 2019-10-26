package wust.student.illnesshepler.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetInvaestigationList {

    @SerializedName("data")
    public List<Item> data;

    public class Item {

        @SerializedName("id")
        public String inid;
        @SerializedName("title")
        public String intitle;
        @SerializedName("type")
        public String intype;

    }
}
