package wust.student.illnesshepler.Utils;

import com.google.gson.Gson;

import org.json.JSONObject;

import wust.student.illnesshepler.Bean.GetTheme;

public class GsonUtils {


    /**
     * 根据json字符串返回对应的对象
     */
    public static GetTheme handleMessages(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return new Gson().fromJson(jsonObject.toString(), GetTheme.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
