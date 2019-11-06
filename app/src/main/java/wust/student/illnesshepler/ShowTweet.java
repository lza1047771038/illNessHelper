package wust.student.illnesshepler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import wust.student.illnesshepler.Utils.Utils;

public class ShowTweet extends AppCompatActivity {

    RichEditorNew richEditor;
    TextView tweetTitle;
    TextView tweetAuther;
    TextView tweetTime;

    Handler handler;

    private String title;
    private String auther;
    private String time;
    private String themeid;
    private String html;
    private String number;

    RoundImageView  roundImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tweet);
        handlemesgge();
        initlayout();
        getdata();
    }
    public void initlayout()
    {
        richEditor=(RichEditorNew)findViewById(R.id.richEditor);
        richEditor.setPadding(10, 10, 10, 10);

        tweetTitle=(TextView)findViewById(R.id.show_tweet_title);
        tweetAuther=(TextView)findViewById(R.id.show_tweet_auther);
        tweetTime=(TextView)findViewById(R.id.show_tweet_time);
        RoundImageView  roundImageView=findViewById(R.id.show_tweet_img);
    }
    public void handlemesgge() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {  //设置北京颜色
                    setdata(msg.obj+"");
                }

                return false;
            }
        });
    }
    public void getdata()
    {
       Bundle bundle = this.getIntent().getExtras();

       title = bundle.getString("title", "错误");
       auther  = bundle.getString("authername", "错误");
       time  = bundle.getString("time", "错误");
        themeid  = bundle.getString("themeid", "错误");
        number  = bundle.getString("number", "错误");
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
                String result=response.body().string();
                Log.d("test", " Showtweet result" + result);
                try {

                    JSONObject tokenInfo = new JSONObject(result);
                    html=tokenInfo.getString("contains");

                } catch (JSONException e) {

                }
                Message message=new Message();
                message.what=1;
                message.obj=html;
                handler.sendMessage(message);
            }
        });
    }
    public void setdata(String html)
    {
        Log.d("test", "Showtweet html"+html);
        richEditor.loadRichEditorCode(html);
        richEditor.setOnClickImageTagListener(new RichEditor.OnClickImageTagListener() {
            @Override
            public void onClick(String url) {
                Toast.makeText(ShowTweet.this, "url:" + url, Toast.LENGTH_LONG).show();
            }
        });
    }
}
