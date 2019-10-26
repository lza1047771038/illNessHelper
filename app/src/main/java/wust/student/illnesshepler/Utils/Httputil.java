package wust.student.illnesshepler.Utils;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import wust.student.illnesshepler.Investigation;

public class Httputil {

    final static String getTheme = "http://192.168.1.102:8080/theme_request";
    final static String getSurvey = "http://192.168.1.102:8080/Survey";
    final static String getSurvey_List = "http://192.168.1.102:8080/Survey_List";

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

    public static void sendOKHttpRequestGetSurvey(okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .add("type", Investigation.type)
                .build();
        Request request = new Request.Builder().url(getSurvey).post(build).build();
        client.newCall(request).enqueue(callback);
    }
    public static void sendOKHttpRequest3(okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .build();
        Request request = new Request.Builder().url(getTheme).post(build).build();
        client.newCall(request).enqueue(callback);
    }

}
