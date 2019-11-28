package wust.student.illnesshepler.Utils;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpApi {

    @FormUrlEncoded
    @POST("login")
    Observable<ResponseBody> login(@Field("userid") String userid,@Field("password") String password,@Field("phoneid") String phoneid);

    @FormUrlEncoded
    @POST("NotificationList")
    Observable<ResponseBody> NotificationList(@Field("") String nothing);

    @FormUrlEncoded
    @POST("NotificationDetails")
    Observable<ResponseBody> NotificationDetails(@Field("themeid") String themeid);


}
