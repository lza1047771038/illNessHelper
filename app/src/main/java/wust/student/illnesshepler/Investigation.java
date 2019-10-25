package wust.student.illnesshepler;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Adapters.InvestigationAdapter;
import wust.student.illnesshepler.Bean.BaseQuestion;
import wust.student.illnesshepler.Bean.ManualQuestion;
import wust.student.illnesshepler.Bean.MutipleQuestion;
import wust.student.illnesshepler.Bean.SingleQuestion;
import wust.student.illnesshepler.Bean.Test;
import wust.student.illnesshepler.Utils.GsonUtils;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.StatusBarUtil;

import static org.litepal.LitePalApplication.getContext;

public class Investigation extends AppCompatActivity {
    public static ViewPager mViewPager;
    public InvestigationAdapter mAdapter;
    public Test testinfo;
    public List<BaseQuestion> mlist = new ArrayList<>();
    public static JSONObject jsonObject = new JSONObject();
    public static String type;
    ActionBar actionBar;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=this.getIntent().getExtras();
        type= bundle.getString("type","00");
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_investigation);
        StatusBarUtil.setStatusBarDarkTheme(this,true);



        drawable = getDrawable(R.color.white);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("问卷调查");
            drawable.setAlpha(0);
            actionBar.setBackgroundDrawable(drawable);
        }
//        for (int i = 0; i < 2; i++) {
//            SingleQuestion singleQuestion = new SingleQuestion();
//            singleQuestion.title = "单选标题" + i;
//            singleQuestion.optiona = "第一个选项";
//            singleQuestion.optionb = "第二个选项";
//            singleQuestion.optionc = "第三个选项";
//            singleQuestion.optiond = "第四个选项";
//            singleQuestion.optione = "第五个选项";
//            singleQuestion.optionf = "";
//            singleQuestion.optiong = "";
//            singleQuestion.optionh = "";
//            singleQuestion.optioni = "";
//            singleQuestion.optionj = "";
//            mlist.add(singleQuestion);
//        }
//        for (int i = 0; i < 2; i++) {
//            MutipleQuestion mutipleQuestion = new MutipleQuestion();
//            mutipleQuestion.title = "多选标题" + i;
//            mutipleQuestion.optiona = "多选第一个选项";
//            mutipleQuestion.optionb = "多选第二个选项";
//            mutipleQuestion.optionc = "多选第三个选项";
//            mutipleQuestion.optiond = "多选第四个选项";
//            mutipleQuestion.optione = "多选第五个选项";
//            mutipleQuestion.optionf = "多选第六个选项";
//            mutipleQuestion.optiong = "多选第七个选项";
//            mutipleQuestion.optionh = "多选第八个选项";
//            mutipleQuestion.optioni = "多选第九个选项";
//            mutipleQuestion.optionj = "多选第十个选项";
//            mlist.add(mutipleQuestion);
//        }
//            for (int i = 0; i < 2; i++) {
//            ManualQuestion manualQuestion=new ManualQuestion();
//            manualQuestion.title="填空标题"+i;
//            mlist.add(manualQuestion);
//        }
        SharedPreferences preferences = getSharedPreferences("SurveyInfo", Context.MODE_PRIVATE);
        String cache = preferences.getString("type", null);
        if (cache != null) {
            testinfo = GsonUtils.handleMessages1(cache);
            if (testinfo != null) {
                mlist.addAll(testinfo.data1);
                mlist.addAll(testinfo.data2);
                mlist.addAll(testinfo.data3);
            }
        }
        else {
            requestThemes();
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new InvestigationAdapter(getSupportFragmentManager(), mlist);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mlist.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invastigation_action_bar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_submit :
                Toast.makeText(this, "json:"+jsonObject, Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestThemes() {
        Httputil.sendOKHttpRequestGetSurvey(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getContext(), "似乎出现了一些问题", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result=response.body().string();
                SharedPreferences preferences = getSharedPreferences("SurveyInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(type, result);

                testinfo= GsonUtils.handleMessages1(result);
                mlist.addAll(testinfo.data1);
                mlist.addAll(testinfo.data2);
                mlist.addAll(testinfo.data3);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test","ssssssssssssssssshhhhhhhhhhhhhhhhhhh");
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });


    }

}
