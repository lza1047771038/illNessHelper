package wust.student.illnesshepler.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetInvaestigationList {
    @SerializedName("id")
    public String id;
    @SerializedName("data")
    public List<Item> data;

    public static class Item {

        @SerializedName("id")
        public String inid;
        @SerializedName("title")
        public String intitle;
        @SerializedName("type")
        public String intype;
        @SerializedName("problemOffset")
        public int num;
        @SerializedName("problem1")
        public String problem1;
        @SerializedName("problem2")
        public String problem2;
        @SerializedName("problem3")
        public String problem3;
        @SerializedName("warning")
        public String warning;
    }
}
