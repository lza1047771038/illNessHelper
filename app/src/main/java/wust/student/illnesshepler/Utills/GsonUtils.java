package wust.student.illnesshepler.Utills;

import com.google.gson.Gson;

import org.json.JSONObject;

import wust.student.illnesshepler.Bean.GetTheme;

public class GsonUtils {

    public static GetTheme handleMessages(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            return new Gson().fromJson(jsonObject.toString(),GetTheme.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
