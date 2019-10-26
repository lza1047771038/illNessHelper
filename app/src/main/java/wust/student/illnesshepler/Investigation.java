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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "无网络连接，已退出", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
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
