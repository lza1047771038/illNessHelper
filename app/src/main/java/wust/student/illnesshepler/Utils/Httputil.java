package wust.student.illnesshepler.Utils;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Httputil {

    final static String getTheme = "http://192.168.1.106:8080/theme_request";

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


}
