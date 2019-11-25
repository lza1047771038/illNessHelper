package wust.student.illnesshepler.Activities.LoginAndRegister;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.Httputil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView enrol;
    ImageButton btn_login;
    EditText userid;
    EditText psd;
    public Handler handler;

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
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login(userid.getText().toString(), psd.getText().toString());
                break;
            case R.id.enrol:
                Intent intent = new Intent(LoginActivity.this, Enrol.class);
                startActivity(intent);
                break;
        }
    }

    public void login(String userid, String pasword) {
        String phoneid = Settings.System.getString(this.getContentResolver(),
                Settings.System.ANDROID_ID);
        if (phoneid == null) {
            phoneid = "null";
        }
        Httputil.login(userid, pasword, phoneid, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                if (result.equals("0")) {
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                } else {
                    getUserInfo(result);
                    Log.d("test", "login result" + result);
                }
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
