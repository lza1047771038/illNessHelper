package wust.student.illnesshepler.Utills;

import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import wust.student.illnesshepler.Bean.GetTheme;

public class Httputil {

    final static String getTheme="http://192.168.1.100:8080/theme";
//theme
    public static void sendOKHttpRequest(okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build=new FormBody.Builder()
                .build();
        Request request = new Request.Builder().url(getTheme).post(build).build();
        client.newCall(request).enqueue(callback);
    }
//postings
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
