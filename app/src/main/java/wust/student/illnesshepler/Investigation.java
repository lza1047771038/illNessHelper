package wust.student.illnesshepler;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Adapters.InvestigationAdapter;
import wust.student.illnesshepler.Bean.BaseQuestion;
import wust.student.illnesshepler.Bean.Problem;
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
    public  String type;
    public long starttime=-1;
    public long endtime=-1;
    ActionBar actionBar;
    Drawable drawable;
    Problem problem;
    PromptDialog promptDialog;
    public static int problemnum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle=this.getIntent().getExtras();
        type= bundle.getString("type","00");

        try {
            Investigation.jsonObject.put("problem1","");
            Investigation.jsonObject.put("problem2","");
            Investigation.jsonObject.put("problem3","");
            Investigation.jsonObject.put("submitTime","");
            Investigation.jsonObject.put("useTime","");
        } catch (JSONException e) {
            e.printStackTrace();
        }


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
//        SharedPreferences preferences = getSharedPreferences("SurveyInfo", Context.MODE_PRIVATE);
//        String cache = preferences.getString("type", null);
//        if (cache != null) {
//            testinfo = GsonUtils.handleMessages1(cache);
//            if (testinfo != null) {
//                mlist.addAll(testinfo.data1);
//                mlist.addAll(testinfo.data2);
//                mlist.addAll(testinfo.data3);
//            }
//        }
//        else {
            requestThemes();
//        }
        starttime=System.currentTimeMillis();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new InvestigationAdapter(getSupportFragmentManager(), mlist);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mlist.size());
        problem=new Problem();
        problem.num=bundle.getInt("num",0);
        problemnum=bundle.getInt("num",0);
        problem.problem1=bundle.getString("problem1","");
        problem.problem2=bundle.getString("problem2","");
        problem.problem3=bundle.getString("problem3","");
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
                endtime=System.currentTimeMillis();
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
                try {
                if( endtime-starttime>0)
                {
                        jsonObject.put("submitTime",""+format.format(endtime));
                        jsonObject.put("useTime",""+(endtime-starttime)/1000);
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("test",jsonObject.toString());
                Toast.makeText(this, "json:"+jsonObject, Toast.LENGTH_LONG).show();
                promptDialog = new PromptDialog(this);
                promptDialog.showLoading("正在提交",true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        promptDialog.showSuccess("成功",true);
                    }
                },2000);
                break;
            case android.R.id.home:
                MaterialDialog dialog = new MaterialDialog.Builder(this)
                        .title("您确认要退出吗")
                        .content("点击确认推出")
                        .positiveText("确认")
                        .negativeText("取消")
                        .negativeColor(getColor(R.color.optioncolorcolor))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestThemes() {
        Httputil.sendOKHttpRequestGetSurvey(type,new Callback() {
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
//                SharedPreferences preferences = getSharedPreferences("SurveyInfo", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString(type, result);

                Log.d("test",result);
                testinfo= GsonUtils.handleMessages1(result);
                if(problemnum!=0) {
                    mlist.add(problem);
                }
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
