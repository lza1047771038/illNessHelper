package wust.student.illnesshepler.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import org.litepal.LitePal;

import java.util.List;

import wust.student.illnesshepler.Activities.LoginAndRegister.*;
import wust.student.illnesshepler.Bean.User_information;


public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LitePal.initialize(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<User_information> all = LitePal.findAll(User_information.class);//查询功能
                if (all.size() != 0) {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 1500);
    }

}
