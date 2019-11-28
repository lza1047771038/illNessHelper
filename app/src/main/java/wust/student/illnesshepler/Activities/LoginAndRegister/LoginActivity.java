package wust.student.illnesshepler.Activities.LoginAndRegister;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.CustomViews.processingDialog;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.BuildConfig;
import wust.student.illnesshepler.Utils.HttpApi;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView enrol;
    ImageButton btn_login;
    EditText userid;
    EditText psd;
    public Handler handler;
    public static String TAG = "test";
    processingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        dialog = new processingDialog(LoginActivity.this, R.layout.processingdialog);
        dialog.setCancelable(false);

        LitePal.initialize(this);
        LitePal.deleteAll(User_information.class);
        initLayout();
    }

    public void initLayout() {
        enrol = findViewById(R.id.enrol);
        btn_login = findViewById(R.id.btn_login);

        userid = (EditText) findViewById(R.id.login_user_id);
        psd = (EditText) findViewById(R.id.login_user_psd);

        enrol.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1:
                        if(dialog.isShowing())
                            dialog.cancel();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        Toast.makeText(LoginActivity.this,
                                getResources().getString(R.string.connect_fail),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(LoginActivity.this,
                                getResources().getString(R.string.wrong_password),
                                Toast.LENGTH_SHORT).show();
                        break;
                }
                dialog.cancel();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if(!dialog.isShowing()){
                    dialog.show();
                }
                login(userid.getText().toString(), psd.getText().toString());
                break;
            case R.id.enrol:
                Intent intent = new Intent(LoginActivity.this, Enrol.class);
                startActivity(intent);
                break;
        }
    }

    int maxConnectCount = 5, currentRetryCount = 0, waitRetryTime = 0;

    public void login(String userid, String pasword) {

        String phoneid = Settings.System.getString(this.getContentResolver(),
                Settings.System.ANDROID_ID);
        if (phoneid == null) {
            phoneid = "null";
        }

        currentRetryCount = 0;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.RequestBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        HttpApi request = retrofit.create(HttpApi.class);

        Observable<ResponseBody> observable = request.login(userid, pasword, phoneid);

        observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) {
                        if (throwable instanceof IOException) {
                            Log.d(TAG, "属于IO异常，需重试");
                            if (currentRetryCount < maxConnectCount) {
                                currentRetryCount++;
                                Log.d(TAG, "重试次数 = " + currentRetryCount);
                                waitRetryTime = 1000 + currentRetryCount * 500;
                                Log.d(TAG, "等待时间 =" + waitRetryTime);
                                return Observable.just(1).delay(waitRetryTime,
                                        TimeUnit.MILLISECONDS);
                            } else {
                                return Observable.error(new Throwable("网络连接失败，请重试"));
                            }
                        } else {
                            return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        d.isDisposed();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String result = responseBody.string();
                            if (result.equals("0")) {
                                Message message = new Message();
                                message.what = 3;
                                handler.sendMessage(message);
                            } else {
                                getUserInfo(result);
                                Log.d("test", "login result" + result);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.cancel();
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getUserInfo(String result) {
        Log.d("test", result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject userInfo = new JSONObject(jsonObject.getString("data"));
            String userId = userInfo.get("userId").toString();
            String userImagePath = userInfo.getString("userImagePath");
            ;
            String username = userInfo.getString("username");
            String password = userInfo.getString("password");
            String phoneid = Settings.System.getString(this.getContentResolver(),
                    Settings.System.ANDROID_ID);
            if (phoneid == null) {
                phoneid = "null";
            }
            int userType = userInfo.getInt("userType");
            int type = userInfo.getInt("type");
            int age = userInfo.getInt("age");
            int coin = userInfo.getInt("coin");
            Log.d("test", "coin:" + coin);


            User_information info = new User_information();
            info.setLogin(true);
            info.setId(1);
            info.setUserId(userId + "");
            info.setUser_Name(username);
            info.setPassword(password);
            info.setUser_Image_Uri(userImagePath);
            info.setPhoneid(phoneid);
            info.setUserType(userType);
            info.setType(type);
            info.setUser_Age(age);
            info.setUser_coin(coin);
            info.save();


            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
