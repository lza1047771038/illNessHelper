package wust.student.illnesshepler.Utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import wust.student.illnesshepler.Investigation;

public class Httputil {

    final static String getTheme = "http://47.100.93.91:8996/theme_request";
    final static String getSurvey = "http://47.100.93.91:8996/Survey_Response";
    final static String getSurvey_List = "http://47.100.93.91:8996/Survey_List";
    final static String Survey_Result = "http://47.100.93.91:8996/Survey_Result";
    final static String NotificationList = "http://47.100.93.91:8996/NotificationList";
    final static String ImagesUpload = "http://47.100.93.91:8996/ImagesUpload";
    final static String NotificationPost = "http://47.100.93.91:8996/NotificationPost";
    final static String NotificationDetails = "http://47.100.93.91:8996/NotificationDetails";


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

    public static void sendOkHttpRequestSurvey_List(okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .build();
        Request request = new Request.Builder().url(getSurvey_List).post(build).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOKHttpRequestGetSurvey(String type, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .add("type", type)
                .build();
        Request request = new Request.Builder().url(getSurvey).post(build).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendokhttpSurveyResult(String type, JSONObject json, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        RequestBody build = new FormBody.Builder()
                .add("type", type)
                .add("result", String.valueOf(json))
                .build();
        Request request = new Request.Builder().url(Survey_Result).post(build).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendokhttpNotificationList(okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        RequestBody build = new FormBody.Builder()
                .build();
        Request request = new Request.Builder().url(NotificationList).post(build).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendokhttplogin(okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        RequestBody build = new FormBody.Builder()
                .build();
        Request request = new Request.Builder().url(NotificationList).post(build).build();
        client.newCall(request).enqueue(callback);
    }

    public static void ImagesUpload(String themeid, List<String> pathList, okhttp3.Callback callback) {
        MediaType MutilPart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");

        OkHttpClient client = new OkHttpClient.Builder().build();
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        multipartBodyBuilder.addFormDataPart("themeid", themeid);
        if (pathList != null) {
            for (int i = 0; i < pathList.size(); i++) {
                File file = new File(pathList.get(i));
                multipartBodyBuilder.addFormDataPart("img", file.getName(), RequestBody.create(MutilPart_Form_Data, file));
                Log.d("test", "ImagesUpload   result :" + file);
            }
        }
        RequestBody requestBody = multipartBodyBuilder.build();
        Request request = new Request.Builder()
                .url(ImagesUpload)
                .post(requestBody)
                .build();
        Log.d("test", "ImagesUpload   result :" );
        client.newCall(request).enqueue(callback);
    }
    public static void NotificationPost(String themeid,String authorid,String title,String contains,String posttime,String headerimage ,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        RequestBody build = new FormBody.Builder()
                .add("themeid", themeid)
                .add("authorid", authorid)
                .add("title", title)
                .add("contains", contains)
                .add("posttime", posttime)
                .add("headerimage",headerimage)
                .build();
        Request request = new Request.Builder().url(NotificationPost).post(build).build();
        client.newCall(request).enqueue(callback);
    }
    public static void NotificationDetails(String themeid, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        RequestBody build = new FormBody.Builder()
                .add("themeid", themeid)
                .build();
        Request request = new Request.Builder().url(NotificationDetails).post(build).build();
        client.newCall(request).enqueue(callback);
    }
}
