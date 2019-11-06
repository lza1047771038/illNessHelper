package wust.student.illnesshepler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rex.editor.view.RichEditor;
import com.rex.editor.view.RichEditorNew;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.SurveyQuestions.Problem;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.RoundImageView;
import wust.student.illnesshepler.Utils.StatusBarUtil;
import wust.student.illnesshepler.Utils.Utils;

public class ShowTweet extends AppCompatActivity {

    RichEditorNew richEditor;
    TextView tweetTitle;
    TextView tweetAuther;
    TextView tweetTime;
    Toolbar toolbar;
    LinearLayout linearLayout;
    ScrollView scrollView;

    Window window;

    Handler handler;

    private String title;
    private String auther;
    private String time;
    private String themeid;
    private String html;
    private String number;

    RoundImageView roundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NoAppTheme);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
            window = getWindow();
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        StatusBarUtil.setStatusBarDarkTheme(this, true);

        setContentView(R.layout.activity_show_tweet);
        initlayout();
        handlemesgge();


        getdata();
    }

    public void initlayout() {
        richEditor = (RichEditorNew) findViewById(R.id.richEditor);
        richEditor.setPadding(10, 10, 10, 10);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setAlpha(0);
        toolbar.setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
        setSupportActionBar(toolbar);

        linearLayout = findViewById(R.id.linearLayout);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        params.setMargins(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
        linearLayout.setLayoutParams(params);

        tweetTitle = (TextView) findViewById(R.id.show_tweet_title);
        tweetAuther = (TextView) findViewById(R.id.show_tweet_auther);
        tweetTime = (TextView) findViewById(R.id.show_tweet_time);
        RoundImageView roundImageView = findViewById(R.id.show_tweet_img);


        scrollView = findViewById(R.id.scrollViews);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int screen_height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
                if (scrollY <= screen_height / 2f) {
                    toolbar.setAlpha(scrollY / (screen_height / 4f));
                }
                StatusBarUtil.setStatusBarDarkTheme(ShowTweet.this, scrollY < 400);
            }
        });

    }

    public void handlemesgge() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {  //设置北京颜色
                    setdata(msg.obj + "");
                }

                return false;
            }
        });
    }

    public void getdata() {
        Bundle bundle = this.getIntent().getExtras();

        title = bundle.getString("title", "错误");
        auther = bundle.getString("authername", "错误");
        time = bundle.getString("time", "错误");
        themeid = bundle.getString("themeid", "错误");
        number = bundle.getString("number", "错误");
        tweetTitle.setText(title);
        tweetAuther.setText(auther);
        tweetTime.setText(Utils.timeFormat(time));

//        Httputil.NotificationDetails(new )
        Httputil.NotificationDetails(themeid, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.d("test", " Showtweet result" + result);
                try {

                    JSONObject tokenInfo = new JSONObject(result);
                    html = tokenInfo.getString("contains");

                } catch (JSONException e) {

                }
                Message message = new Message();
                message.what = 1;
                message.obj = html;
                handler.sendMessage(message);
            }
        });
    }

    public void setdata(String html) {
        Log.d("test", "Showtweet html" + html);
        richEditor.loadRichEditorCode(html);
        richEditor.setOnClickImageTagListener(new RichEditor.OnClickImageTagListener() {
            @Override
            public void onClick(String url) {
                Toast.makeText(ShowTweet.this, "url:" + url, Toast.LENGTH_LONG).show();
            }
        });
    }
}
