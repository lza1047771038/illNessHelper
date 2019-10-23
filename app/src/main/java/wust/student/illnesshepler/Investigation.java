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
    ActionBar actionBar;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        String cache = preferences.getString("S", null);
        if (cache != null) {
            testinfo = GsonUtils.handleMessages1(cache);
            if (testinfo != null) {
                mlist.addAll(testinfo.data1);
                mlist.addAll(testinfo.data2);
                mlist.addAll(testinfo.data3);
            }
        }
        else {

//            cache="{\"SingleQuestion\": [{\"id\": 20, \"title\": \"\\u4f60\\u559c\\u6b22\\u4ec0\\u4e48\\u8fd0\\u52a8\\uff1f\", \"type\": \"20191022\", \"themeid\": \"20191022\", \"selectionA\": \"\\u4e52\\u4e53\\u7403\", \"selectionB\": \"\\u7bee\\u7403\", \"selectionC\": \"\\u8db3\\u7403\", \"selectionD\": \"\\u6392\\u7403\", \"selectionE\": \"\\u7f51\\u7403\", \"selectionF\": \"\", \"selectionG\": \"\", \"selectionH\": \"\", \"selectionI\": \"\", \"selectionJ\": \"\", \"A_next\": 6, \"B_next\": 0, \"C_next\": 0, \"D_next\": 0, \"E_next\": 0, \"F_next\": 0, \"G_next\": 0, \"H_next\": 0, \"I_next\": 0, \"J_next\": 0}, {\"id\": 21, \"title\": \"\\u4f60\\u559c\\u6b22\\u4ec0\\u4e48\\u4e13\\u4e1a\\uff1f\", \"type\": \"20191022\", \"themeid\": \"20191022\", \"selectionA\": \"\\u8ba1\\u7b97\\u673a\\u79d1\\u5b66\\u4e0e\\u6280\\u672f\", \"selectionB\": \"\\u7f51\\u7edc\\u5de5\\u7a0b\", \"selectionC\": \"\\u8f6f\\u4ef6\\u5de5\\u7a0b\", \"selectionD\": \"\\u4fe1\\u606f\\u5b89\\u5168\", \"selectionE\": \"\\u5a92\\u4f53\", \"selectionF\": \"\\u6570\\u5b66\", \"selectionG\": \"\", \"selectionH\": \"\", \"selectionI\": \"\", \"selectionJ\": \"\", \"A_next\": 0, \"B_next\": 0, \"C_next\": 0, \"D_next\": 0, \"E_next\": 0, \"F_next\": 0, \"G_next\": 0, \"H_next\": 0, \"I_next\": 0, \"J_next\": 0}, {\"id\": 22, \"title\": \"\\u4f60\\u559c\\u6b22\\u4ec0\\u4e48\\u6b4c\\uff1f\", \"type\": \"20191022\", \"themeid\": \"20191022\", \"selectionA\": \"\\u6d41\\u884c\", \"selectionB\": \"\\u7235\\u58eb\", \"selectionC\": \"\\u53e4\\u5178\", \"selectionD\": \"\", \"selectionE\": \"\", \"selectionF\": \"\", \"selectionG\": \"\", \"selectionH\": \"\", \"selectionI\": \"\", \"selectionJ\": \"\", \"A_next\": 4, \"B_next\": 0, \"C_next\": 0, \"D_next\": 0, \"E_next\": 0, \"F_next\": 0, \"G_next\": 0, \"H_next\": 0, \"I_next\": 0, \"J_next\": 0}], \"MutipleQuestion\": [{\"id\": 2, \"type\": \"20191022\", \"title\": \"\\u4f60\\u559c\\u6b22\\u4ec0\\u4e48\\u6837\\u7684\\u5973\\u5b69\\u5b50\\uff1f\", \"selectionA\": \"\\u6e29\\u67d4\", \"selectionB\": \"\\u4f53\\u8d34\", \"selectionC\": \"\\u5584\\u826f\", \"selectionD\": \"\\u53ef\\u7231\", \"selectionE\": \"\\u6d3b\\u6cfc\", \"selectionF\": \"\", \"selectionG\": \"\", \"selectionH\": \"\", \"selectionI\": \"\", \"selectionJ\": \"\"}, {\"id\": 3, \"type\": \"20191022\", \"title\": \"\\u5b66\\u6821\\u751f\\u6d3b\\u600e\\u4e48\\u6837\\uff1f\", \"selectionA\": \"\\u4e00\\u822c\\u822c\", \"selectionB\": \"\\u8fd8\\u597d\", \"selectionC\": \"\\u633a\\u6ee1\\u610f\\u7684\", \"selectionD\": \"\\u5f88\\u6ee1\\u610f\", \"selectionE\": \"\\u975e\\u5e38\\u6ee1\\u610f\", \"selectionF\": \"\", \"selectionG\": \"\", \"selectionH\": \"\", \"selectionI\": \"\", \"selectionJ\": \"\"}], \"ManualQuestion\": [{\"id\": 2, \"type\": \"20191022\", \"title\": \"\\u9488\\u5bf9\\u5b66\\u6821\\u7684\\u5b89\\u5168\\u4fdd\\u536b\\uff0c\\u4f60\\u6709\\u4ec0\\u4e48\\u770b\\u6cd5\\uff1f\"}]}\n";
//            testinfo= GsonUtils.handleMessages1(cache);
////                testinfo = GsonUtils.handleMessages1(result);
//            Log.d("test","s"+testinfo.data1.size() + "");
//            Log.d("test","s"+testinfo.data2.size() + "");
//            Log.d("test",""+testinfo.data3.size() + "");
//            Log.d("test",""+mlist.size() + "");
//            mlist.addAll(testinfo.data1);
//            mlist.addAll(testinfo.data2);
//            mlist.addAll(testinfo.data3);
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
//                Log.d("test","s"+jsonObject + "");
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
                editor.putString("SurveyType"+"20191017", result);
                editor.apply();

                testinfo= GsonUtils.handleMessages1(result);
//                testinfo = GsonUtils.handleMessages1(result);
                Log.d("test","s"+testinfo.data1.size() + "");
                Log.d("test","s"+testinfo.data2.size() + "");
                Log.d("test",""+testinfo.data3.size() + "");
                Log.d("test",""+mlist.size() + "");
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

}
