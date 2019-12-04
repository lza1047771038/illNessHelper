package wust.student.illnesshepler.Activities.SetUserInfo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;

import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.FileUtil;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.StatusBarUtil;

public class Edit_Userdata extends AppCompatActivity implements View.OnClickListener {

    private View v;
    public Button chandeBtn;
    public Handler handler;
    private String temp_name;
    private int temp_age;
    public PromptDialog promptDialog;
    AlertDialog.Builder builder;
    TextView user_name;
    TextView user_age;
    ImageView user_image;
    EditText input;
    FileUtil fileUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__userdata);
        promptDialog = new PromptDialog(this);

        v = getLayoutInflater().inflate(R.layout.dialoglayout, null);

        builder = new AlertDialog.Builder(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.edit);
        }

        handleMessge();

        LinearLayout User_img = (LinearLayout) findViewById(R.id.edit_user_img);
        final LinearLayout User_age = (LinearLayout) findViewById(R.id.edit_user_age);
        LinearLayout User_integra = (LinearLayout) findViewById(R.id.edit_user_integral);
        LinearLayout User_name = (LinearLayout) findViewById(R.id.edit_user_name);

        user_name = (TextView) findViewById(R.id.user_name);
        user_age = (TextView) findViewById(R.id.user_age);
        user_image = (ImageView) findViewById(R.id.user_img);
        chandeBtn = (Button) findViewById(R.id.change_user_info);

        chandeBtn.setOnClickListener(this);

        input = v.findViewById(R.id.input);

        User_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_Userdata.this, Set_Userimg.class);
                startActivity(intent);
            }
        });

        User_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setHint(user_name.getText().toString());
                username_edit();
            }
        });

        User_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userage_edit(user_age.getText().toString());
            }
        });
    }

    public void handleMessge() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1:
                        promptDialog.showSuccess("修改成功");
                        finish();
                        break;
                    case 2:
                        MainActivity.userInfo.setUser_Name(temp_name);
                        MainActivity.userInfo.setUser_Age(temp_age);
                        UpDataInt("User_Age", temp_age);
                        UpData("User_Name", temp_name);
                        user_name.setText(temp_name);
                        user_age.setText(temp_age + "");
                        promptDialog.showError("修改失败请重试");
                        break;
                    case 3:
                        promptDialog.showError("异地登录");
                        break;
                }
                return false;
            }
        });
    }

    public void username_edit() {
        final String[] name = {""};
        builder.setTitle("修改用户名");
        if (v.getParent() != null) {
            ViewGroup vg = (ViewGroup) v.getParent();
            vg.removeView(v);
        }

        builder.setView(v);

        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name[0] = input.getText().toString();
                user_name.setText(name[0]);
                MainActivity.userInfo.setUser_Name(name[0]);
                UpData("User_Name", name[0]);

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Edit_Userdata.this, "已取消，当前用户名：" + user_name.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void userage_edit(String age) {
        final int[] newAge = new int[1];
        int oldAge = Integer.parseInt(age);
        View v_age_dialog = getLayoutInflater().inflate(R.layout.item_dialog_age, null);
        NumberPicker numberPicker_age = v_age_dialog.findViewById(R.id.input);
        numberPicker_age.setMinValue(0);
        numberPicker_age.setMaxValue(100);
        numberPicker_age.setValue(oldAge);
        numberPicker_age.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newAge[0] = newVal;
            }
        });


        builder.setView(v_age_dialog);

        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user_age.setText(Integer.toString(newAge[0]));
                MainActivity.userInfo.setUser_Age(newAge[0]);
                UpData("User_Age", Integer.toString(newAge[0]));
                Toast.makeText(Edit_Userdata.this, "修改后的年龄：" + newAge[0], Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Edit_Userdata.this, "已取消，当前年龄：" + user_age.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void UpData(String name, String data) {

        ContentValues values = new ContentValues();
        values.put(name, data);
        LitePal.updateAll(User_information.class, values);
    }

    public void UpDataInt(String name, int data) {
        ContentValues values = new ContentValues();
        values.put(name, data);
        LitePal.updateAll(User_information.class, values);
    }

    public void setUesrdata() {
        if (MainActivity.userInfo.isLogin()) {
            if (MainActivity.userInfo.getUser_Name() != null) {
                temp_name = MainActivity.userInfo.getUser_Name();
                user_name.setText(MainActivity.userInfo.getUser_Name());
            } else {
                temp_name = "";
                user_name.setText("请修改用户名");
            }
            temp_age = MainActivity.userInfo.getUser_Age();
            user_age.setText(MainActivity.userInfo.getUser_Age() + "");
            if (MainActivity.userInfo.getUser_Image_Uri() != null) {
                Glide.with(this).load(MainActivity.userInfo.getUser_Image_Uri()).apply(new RequestOptions().transforms(new CenterCrop())).into(user_image);
            }
        }
    }

    public void onStart() {
        super.onStart();
        setUesrdata();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_user_info:
                changeUserinfo();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            changeUserinfo();
            finish();
            return true;
        }
        return false;
    }

    public void changeUserinfo() {
        Httputil.updateUserInfo(MainActivity.userInfo.getUserId(),
                MainActivity.userInfo.getUser_Name(), MainActivity.userInfo.getUser_Age(),
                MainActivity.userInfo.getUser_coin(), MainActivity.userInfo.getUser_Image_Uri(),
                MainActivity.userInfo.getPhoneid(), new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String result = response.body().string();
                        Log.d("test", "updateUserInfo result" + result);
                        Message message = new Message();
                        if (result.equals("0")) {
                            message.what = 2;
                        } else if (result.equals("2")) {
                            message.what = 3;
                        } else {
                            message.what = 1;
                        }
                        handler.sendMessage(message);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
