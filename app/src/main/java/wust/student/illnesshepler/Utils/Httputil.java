package wust.student.illnesshepler.Utils;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import wust.student.illnesshepler.Investigation;

public class Httputil {

    final static String getTheme = "http://47.100.93.91:8996/theme_request";
    final static String getSurvey = "http://47.100.93.91:8996/Survey_Response";
    final static String getSurvey_List = "http://47.100.93.91:8996/Survey_List";
    final static String Survey_Result = "http://47.100.93.91:8996/Survey_Result";

    /**
     * 发送theme请求
     * 重写callback接口形式，方便后期修改以及代码维护
     *
     * @param callback
     */
    public static void sendOKHttpRequest(okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .build();
        Request request = new Request.Builder().url(getTheme).post(build).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestSurvey_List(okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .build();
        Request request = new Request.Builder().url(getSurvey_List).post(build).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOKHttpRequestGetSurvey(String type,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .add("type", type)
                .build();
        Request request = new Request.Builder().url(getSurvey).post(build).build();
        client.newCall(request).enqueue(callback);
    }
    public static void sendokhttpSurveyResult(String type, JSONObject json ,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        RequestBody build = new FormBody.Builder()
                .add("type",type)
                .add("result", String.valueOf(json))
                .build();
        Request request = new Request.Builder().url(Survey_Result).post(build).build();
        client.newCall(request).enqueue(callback);
    }

}
