package wust.student.illnesshepler.Utils;

import com.google.gson.Gson;

import org.json.JSONObject;

import wust.student.illnesshepler.Bean.GetInvaestigationList;
import wust.student.illnesshepler.Bean.GetTheme;
import wust.student.illnesshepler.SurveyQuestions.Test;
import wust.student.illnesshepler.Bean.Tweets;

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

    public static Test handleMessages1(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return new Gson().fromJson(jsonObject.toString(), Test.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GetInvaestigationList handleMessages2(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return new Gson().fromJson(jsonObject.toString(), GetInvaestigationList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Tweets handleMessages3(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return new Gson().fromJson(jsonObject.toString(), Tweets.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
