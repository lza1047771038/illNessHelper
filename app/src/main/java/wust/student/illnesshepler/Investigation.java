package wust.student.illnesshepler;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
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
import wust.student.illnesshepler.Bean.ManualQuestion;
import wust.student.illnesshepler.Bean.MutipleQuestion;
import wust.student.illnesshepler.Bean.Problem;
import wust.student.illnesshepler.Bean.SingleQuestion;
import wust.student.illnesshepler.Bean.Test;
import wust.student.illnesshepler.Utils.Dialog_prompt;
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
    public String type;
    public long starttime = -1;
    public long endtime = -1;
    ActionBar actionBar;
    Drawable drawable;
    Problem problem;
    PromptDialog promptDialog;
    public static int problemnum = 0;
    Bundle bundle;
    Handler handler;
    public boolean temp1 = false;
    public boolean temp2 = false;
    private Dialog_prompt dialog_prompt;
    private int[] v = new int[]{R.id.prompt_begin, R.id.prompt_text, R.id.calcel_btn};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全屏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_investigation);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        //标题栏
        drawable = getDrawable(R.color.white);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("问卷调查");
            drawable.setAlpha(0);
            actionBar.setBackgroundDrawable(drawable);
        }
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new InvestigationAdapter(getSupportFragmentManager(), mlist);
        bundle = this.getIntent().getExtras();


        //从InvastigationList中取出type以及3个问题
        //初始化json 数据
        type = bundle.getString("type", "error");
        problem = new Problem();
        problem.num = bundle.getInt("num", 0);
        problemnum = bundle.getInt("num", 0);
        problem.problem1 = bundle.getString("problem1", "");
        problem.problem2 = bundle.getString("problem2", "");
        problem.problem3 = bundle.getString("problem3", "");

        try {
            Investigation.jsonObject.put("id", "");
            Investigation.jsonObject.put("submitTime", "");
            Investigation.jsonObject.put("useTime", "");

            if (!problem.problem1.equals("")) {
                Investigation.jsonObject.put("problem1", "");
            }
            if (!problem.problem2.equals("")) {
                Investigation.jsonObject.put("problem2", "");
            }
            if (!problem.problem3.equals("")) {
                Investigation.jsonObject.put("problem3", "");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //请求网络数据
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("正在加载请稍后");
        //请求数据
        requestThemes();
        int waittime = 500 + (int) (Math.random() * 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initlayout();
            }
        }, waittime);


    }

    public void initlayout() {
        promptDialog.showSuccess("加载完成");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog_prompt = new Dialog_prompt(Investigation.this, R.layout.prompt_dialog, v, bundle.getString("warning", ""));
//        dialog_prompt.settext("测试标题\n换行测试");
                dialog_prompt.setCancelable(false);
                dialog_prompt.setOnButtonClickedListener(new Dialog_prompt.onBtnClickListener() {
                    @Override
                    public void onpositiveButtonClicked() {
                        //开始回答问卷调查时间
                        starttime = System.currentTimeMillis();
                        mViewPager.setAdapter(mAdapter);
                        mViewPager.setOffscreenPageLimit(mlist.size());
                        dialog_prompt.dismiss();
                    }

                    @Override
                    public void onCancelButtonClicked() {
                        finish();
                    }
                });
                dialog_prompt.show();

            }
        }, 1000);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invastigation_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_submit:
                Log.d("test", jsonObject + "");
                submit();
                break;
            case android.R.id.home:
                showexitdilog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showexitdilog();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void requestThemes() {
        Httputil.sendOKHttpRequestGetSurvey(type, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        promptDialog.showSuccess("无网络连接，已退出");
                        finish();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.d("test", result);
                testinfo = GsonUtils.handleMessages1(result);
                if (problemnum != 0) {
                    mlist.add(problem);
                    Log.d("test", "haveporoblem");
                }
                mlist.addAll(testinfo.data1);
                mlist.addAll(testinfo.data2);
                mlist.addAll(testinfo.data3);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public void submit() {
        endtime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        try {
            if (endtime - starttime > 0) {
                jsonObject.put("submitTime", "" + format.format(endtime));
                jsonObject.put("useTime", "" + (endtime - starttime) / 1000);
            }
        } catch (JSONException e) {
            Toast.makeText(getContext(), "错误编号：002，请提您提交反馈给我们", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("正在提交", true);
        int submittime = 500 + (int) (Math.random() * 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Httputil.sendokhttpSurveyResult(type, jsonObject, new Callback() {

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                promptDialog.showError("网络连接错误！！");
                            }
                        });

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String result = response.body().string();
                        Log.d("test", "result    :" + result);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result.equals("1")) {
                                    promptDialog.showSuccess("提交成功！谢谢参与！");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 1000);
                                } else if (result.equals("0")) {
                                    promptDialog.showError("提交失败！同时提交人数多，请片刻后重试");
                                } else {
                                    promptDialog.showError("提交失败！！请您提交反馈给我们\n" +
                                            "错误编号：001");
                                }
                            }
                        });
                    }
                });
            }
        }, submittime);

    }

    public void showexitdilog() {
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
    }
}
