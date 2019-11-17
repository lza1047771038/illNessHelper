package wust.student.illnesshepler.Utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
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
    final static String comment_post = "http://47.100.93.91:8996/comment_post";
    final static String reply_post = "http://47.100.93.91:8996/reply_post";
    final static String comment_request = "http://47.100.93.91:8996/comment_request";
    final static String reply_request = "http://47.100.93.91:8996/reply_request";
    final static String register = "http://47.100.93.91:8996/register";
    final static String login = "http://47.100.93.91:8996/login";
    final static String updateUserInfo ="Http://47.100.93.91:8996/update";


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
        if(themeid.equals(""))
        {

        }else {
            multipartBodyBuilder.addFormDataPart("themeid", themeid);
        }
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
    public static void NotificationPost(String themeid,String authorid,String title,String contains,String posttime,String headerimage ,String uploadtype,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        RequestBody build = new FormBody.Builder()
                .add("themeid", themeid)
                .add("authorid", authorid)
                .add("title", title)
                .add("contains", contains)
                .add("posttime", posttime)
                .add("headerimage",headerimage)
                .add("type",uploadtype)
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
    public static void comment_post(String Themeid,String Userid ,String Contains,String phoneid,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .add("themeid", Themeid)
                .add("userid", Userid)
                .add("contains", Contains)
                .add("phoneid", phoneid)
                .build();
        Request request = new Request.Builder().url(comment_post).post(build).build();
        client.newCall(request).enqueue(callback);
    }
    public static void comment_request(String themeid,String page ,String pagesize,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .add("themeid", themeid)
                .add("page", page)
                .add("pagesize", pagesize)
                .build();
        Request request = new Request.Builder().url(comment_request).post(build).build();
        client.newCall(request).enqueue(callback);
    }

    public static void reply_request(String root,String id,String page ,String pagesize,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .add("root", root)
                .add("id", id)
                .build();
        Request request = new Request.Builder().url(reply_request).post(build).build();
        client.newCall(request).enqueue(callback);
    }
    public static void   reply_post(String contains,String userid,String id ,String root,String parentid,String phoneid,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .add("contains", contains)
                .add("userid", userid)
                .add("id", id)
                .add("root", root)
                .add("parentid", parentid)
                .add("phoneid", phoneid)
                .build();
        Request request = new Request.Builder().url(reply_post).post(build).build();
        client.newCall(request).enqueue(callback);
    }
    public static void register(String userid, String password,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .add("userid", userid)
                .add("password", password)
                .build();
        Request request = new Request.Builder().url(register).post(build).build();
        client.newCall(request).enqueue(callback);
    }
    public static void login(String userid, String password,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .add("userid", userid)
                .add("password", password)
                .build();
        Request request = new Request.Builder().url(login).post(build).build();
        client.newCall(request).enqueue(callback);
    }
//    userid,username,age,coin,userimagepath
    public static void updateUserInfo(String userid, String username, int age, int coin, String userimagepath ,okhttp3.Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        RequestBody build = new FormBody.Builder()
                .add("userid", userid)
                .add("username", username)
                .add("age", age+"")
                .add("coin", coin+"")
                .add("userimagepath", userimagepath)
                .build();
        Request request = new Request.Builder().url(updateUserInfo).post(build).build();
        client.newCall(request).enqueue(callback);
    }
}
