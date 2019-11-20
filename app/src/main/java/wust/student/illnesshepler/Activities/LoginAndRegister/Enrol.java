package wust.student.illnesshepler.Activities.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.Httputil;

public class Enrol extends AppCompatActivity implements View.OnClickListener {
    public EditText id;
    public EditText psd1;
    public EditText psd2;
    public Button btn;
    public Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol);
        initlayout();


        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what)
                {
                    case 1:
                        Toast.makeText(Enrol.this,getResources().getString(R.string.registe_succeed),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Enrol.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        Toast.makeText(Enrol.this,getResources().getString(R.string.registe_denyed),Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    public void initlayout()
    {
        id=(EditText)findViewById(R.id.regist_id);
        psd1=(EditText)findViewById(R.id.regist_psd);
        psd2=(EditText)findViewById(R.id.regist_confrim_psd);
        btn=(Button)findViewById(R.id.btn_enrol);
        btn.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_enrol:
                check();
                break;
        }
    }
    public void check()
    {
        String userId=id.getText().toString();
        String userPsd1=psd1.getText().toString();
        String userPsd2=psd2.getText().toString();
        if(userId.length()<6||userId.length()>20)
        {
            Toast.makeText(this,getResources().getString(R.string.registe_id_wrong),Toast.LENGTH_SHORT).show();
        }else if(userPsd1.length()<6&&userPsd1.length()>20)
        {
            Toast.makeText(this,getResources().getString(R.string.registe_psd_wrong),Toast.LENGTH_SHORT).show();
        }
        else if(!userPsd1.equals(userPsd2))
        {
            Toast.makeText(this,getResources().getString(R.string.registe_dif_psd),Toast.LENGTH_SHORT).show();
        }
        else{
            registe(userId,userPsd1);
        }
    }
    public void registe(final String userid, final String pasword)
    {
        Httputil.register(userid, pasword, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Enrol.this,getResources().getString(R.string.connect_fail),Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String result=response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result.equals("0"))
                        {
                            Toast.makeText(Enrol.this,getResources().getString(R.string.registe_denyed),Toast.LENGTH_SHORT).show();
                        }
                        else if(result.equals("1"))
                        {
                            login(userid,pasword);

                        }
                    }
                });

            }
        });
    }
    public  void login(String userid,String pasword)
    {
        String phoneid =Settings.System.getString(this.getContentResolver(), Settings.System.ANDROID_ID);
        if(phoneid==null)
        {
            phoneid="null";
        }
        Httputil.login(userid, pasword,phoneid, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message=new Message();
                message.what=2;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result=response.body().string();
                getUserInfo(result);
            }
        });
    }
    public void getUserInfo(String result)
    {
        String username;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject  userInfo = new JSONObject(jsonObject.getString("data"));
            String userId=userInfo.get("userId").toString();
            String userImagePath="http://47.100.93.91:8996/MediaFiles/mediaImages/1897a172a0c58afa62ae3aa663e8a71e.jpg";
            if(userInfo.getString("username")==null)
                username="请在个人详情页修改个人信息";
            else
                username=userInfo.getString("username");

            String password=userInfo.getString("password");
            String phoneid=userInfo.getString("phoneid");
            int userType=userInfo.getInt("userType");
            int type=userInfo.getInt("type");
            int age=userInfo.getInt("age");
            int coin=userInfo.getInt("coin");
            Log.d("test","coin:"+coin);

            LitePal.initialize(this);
            LitePal.deleteAll(User_information.class);

            User_information info =new User_information();
            info.setLogin(true);
            info.setId(1);
            info.setUserId(userId+"");
            info.setPassword(password);
            info.setUser_Name(username);
            info.setUser_Image_Uri(userImagePath);
            info.setPhoneid(phoneid);
            info.setUserType(userType);
            info.setType(type);
            info.setUser_Age(age);
            info.setUser_coin(coin);
            info.save();
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
