package wust.student.illnesshepler;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.Utils.FileUtil;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.StatusBarUtil;

import static org.litepal.LitePalApplication.getContext;

public class Edit_Userdata extends AppCompatActivity implements View.OnClickListener {

    private View v,statusBarBackground;
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

        v=getLayoutInflater().inflate(R.layout.dialoglayout,null);

        builder=new AlertDialog.Builder(this);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        StatusBarUtil.setStatusBarDarkTheme(this, true);

        Drawable drawable = getDrawable(R.color.white);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("编辑个人信息");
            actionBar.setBackgroundDrawable(drawable);
        }

        /*statusBarBackground = findViewById(R.id.statusBarBackground);
        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusBarBackground.setLayoutParams(params);*/
        handleMessge();

        LinearLayout User_img=(LinearLayout)findViewById(R.id.edit_user_img);
        final LinearLayout User_age=(LinearLayout)findViewById(R.id.edit_user_age);
        LinearLayout User_integra=(LinearLayout)findViewById(R.id.edit_user_integral);
        LinearLayout User_name=(LinearLayout)findViewById(R.id.edit_user_name);

        user_name=(TextView)findViewById(R.id.user_name);
        user_age=(TextView)findViewById(R.id.user_age);
        user_image=(ImageView)findViewById(R.id.user_img);
        chandeBtn=(Button)findViewById(R.id.change_user_info);

        chandeBtn.setOnClickListener(this);

        input=v.findViewById(R.id.input);

        User_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Edit_Userdata.this, Set_Userimg.class);
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
public void handleMessge()
{
    handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case 1:
                promptDialog.showSuccess("修改成功");
                    break;
                case 2:
                    MainActivity.userName=temp_name;
                    MainActivity.userAge=temp_age;
                    UpData("User_Age",Integer.toString(temp_age));
                    UpData("User_Name",temp_name);
                    user_name.setText(temp_name);
                    user_age.setText(temp_age);
//                    promptDialog.showError("修改失败请重试");
                    break;
            }
            return false;
        }
    });
}
    public void username_edit(){
        final String[] name = {""};
        builder.setTitle("修改用户名");
        if(v.getParent()!=null){
            ViewGroup vg=(ViewGroup)v.getParent();
            vg.removeView(v);
        }

        builder.setView(v);

        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name[0] =input.getText().toString();
                user_name.setText(name[0]);
                MainActivity.userName=name[0];
                UpData("User_Name",name[0]);
                Toast.makeText(Edit_Userdata.this,"已经修改用户名",Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Edit_Userdata.this,"已取消，当前用户名："+user_name.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void userage_edit(String age){
        final int[] newAge = new int[1];
        int oldAge=Integer.parseInt(age);
        View v_age_dialog=getLayoutInflater().inflate(R.layout.item_dialog_age,null);
        NumberPicker numberPicker_age=v_age_dialog.findViewById(R.id.input);
        numberPicker_age.setMinValue(0);
        numberPicker_age.setMaxValue(100);
        numberPicker_age.setValue(oldAge);
        numberPicker_age.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newAge[0] =newVal;
            }
        });


        builder.setView(v_age_dialog);

        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    user_age.setText(Integer.toString(newAge[0]));
                    MainActivity.userAge=newAge[0];
                    UpData("User_Age",Integer.toString(newAge[0]));
                    Toast.makeText(Edit_Userdata.this, "修改后的年龄："+newAge[0], Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Edit_Userdata.this,"已取消，当前年龄："+user_age.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void UpData(String name,String data){

        ContentValues values=new ContentValues();
        values.put(name,data);
        LitePal.updateAll(User_information.class,values);
    }

    public void setUesrdata(){
//        List<User_information> all = LitePal.findAll(User_information.class);//查询功能
        if(MainActivity.isLogin){
            if(MainActivity.userName!=null)
            {
                temp_name=MainActivity.userName;
                user_name.setText(MainActivity.userName);
            }
            else
            {
                temp_name="";
                user_name.setText("请修改用户名");
            }
            temp_age=MainActivity.userAge;
            user_age.setText(MainActivity.userAge+"");
            if(MainActivity.user_image!=null) {
//                user_image.setImageBitmap(fileUtil.getBitmap(all.get(0).getUser_Image_Uri()));
                Glide.with(this).load(MainActivity.user_image).apply(new RequestOptions().transforms(new CenterCrop())).into(user_image);
            }
        }
    }

    public void onStart(){
        super.onStart();
//        List<User_information> all = LitePal.findAll(User_information.class);//查询功能
        setUesrdata();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.change_user_info:
                changeUserinfo();
                break;
        }
    }
    public void changeUserinfo()
    {
        Log.d("test","updateUserInfo result"+MainActivity.userId+"\n"
                +MainActivity.userId+"\n"
                +MainActivity.userName+"\n"
                +MainActivity.userAge+"\n"
                +MainActivity.user_coin+"\n"
                +MainActivity.user_image+"\n");
        Httputil.updateUserInfo(MainActivity.userId, MainActivity.userName, MainActivity.userAge, MainActivity.user_coin, MainActivity.user_image, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message=new Message();
                message.what=2;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String result=response.body().string();
                Log.d("test","updateUserInfo result"+result);
                    Message message=new Message();
                    if(result.equals("0"))
                    {
                        message.what=2;
                    }
                    else {
                        message.what=1;
                    }
                    handler.sendMessage(message);
            }
        });
    }
}
