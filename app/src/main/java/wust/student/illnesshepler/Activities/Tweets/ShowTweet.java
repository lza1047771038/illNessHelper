package wust.student.illnesshepler.Activities.Tweets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.rex.editor.view.RichEditor;
import com.rex.editor.view.RichEditorNew;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import wust.student.illnesshepler.Activities.Fragments.Tweets.RepliesDetails;
import wust.student.illnesshepler.Activities.Fragments.Tweets.WriteComment;
import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.Adapters.TweetsCommentAdapter;
import wust.student.illnesshepler.Bean.GetTweetComments;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.BlurUtils;
import wust.student.illnesshepler.Utils.GsonUtils;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowTweet extends AppCompatActivity implements View.OnClickListener,
        TweetsCommentAdapter.OnItemClickListener {

    private RichEditorNew richEditor;
    private WebView webView;
    private TextView tweetTitle;
    private TextView tweetAuther;
    private TextView tweetTime;

    private TextView tweetPostComment;

    private ImageView collect;
    private ImageView share;
    private ImageView showTweetImg;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2;
    private NestedScrollView scrollView;

    Window window;

    private Handler handler;

    private String title;
    private String auther;
    private String time;
    private String themeid;
    private String html;
    private String number;

    private int page = 1;
    public static RecyclerView recyclerView;
    public static TweetsCommentAdapter adapter;

    public static List<GetTweetComments.Comments> clist = new ArrayList<>();

    private GetTweetComments tweetComments;
    private RepliesDetails repliesDetails;
    private WriteComment writeComment;

    /*
    有个小bug 没有网络的时候，没有缓存过的推文可以输入
    11月17号（明天）修复



     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NoAppTheme);

        setContentView(R.layout.activity_show_tweet);

        initlayout();
        handlemesgge();
        getdata();
    }

    public void initlayout() {
        richEditor = (RichEditorNew) findViewById(R.id.richEditor);
        richEditor.setPadding(10, 10, 10, 10);

        webView = (WebView) findViewById(R.id.webview);
        recyclerView = (RecyclerView) findViewById(R.id.show_comment_recycler);
        recyclerView.setNestedScrollingEnabled(false);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        linearLayout = findViewById(R.id.linearLayout3);
        linearLayout2 = findViewById(R.id.linearLayout4);
        linearLayout.setOnClickListener(this);
        tweetTitle = (TextView) findViewById(R.id.show_tweet_title);
        tweetAuther = (TextView) findViewById(R.id.show_tweet_auther);
        tweetTime = (TextView) findViewById(R.id.show_tweet_time);

        tweetPostComment = (TextView) findViewById(R.id.tweet_post_comment);
        collect = (ImageView) findViewById(R.id.tweet_collect);
        share = (ImageView) findViewById(R.id.tweet_share);
        showTweetImg = (ImageView) findViewById(R.id.show_tweet_img);
        tweetPostComment.setOnClickListener(this);
        tweetPostComment.setOnClickListener(this);
        collect.setOnClickListener(this);
        share.setOnClickListener(this);


        scrollView = findViewById(R.id.scrollViews);

    }

    public void handlemesgge() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1:
                        setdata(msg.obj + "");
                        break;
                    case 4:
                        setcommentsdata();
                        break;
                }
                return false;
            }
        });
    }

    public void getdata() {
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title", "错误");
        auther = bundle.getString("authername", "错误");
        time = bundle.getString("time", "错误");
        themeid = bundle.getString("themeid", "错误");
        number = bundle.getInt("number", 0) + "";

        tweetTitle.setText(title);
        tweetAuther.setText(auther);
        tweetTime.setText(Utils.timeFormat(time));
        SharedPreferences sp = ShowTweet.this.getSharedPreferences("ShowTweets",
                Context.MODE_PRIVATE);
        String result = sp.getString(themeid, null);
        if (result != null) {
            Log.d("test", " Showtweet result NotificationDetails " + result);
            analysisTweetJson(result);
        } else
            Httputil.NotificationDetails(themeid, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    SharedPreferences sp = ShowTweet.this.getSharedPreferences("ShowTweets",
                            Context.MODE_PRIVATE);
                    String result = sp.getString(themeid, null);

                    if (result != null) {
                        analysisTweetJson(result);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ShowTweet.this,
                                        getResources().getString(R.string.connect_fail),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String result = response.body().string();
                    SharedPreferences sp = ShowTweet.this.getSharedPreferences("ShowTweets",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(themeid, result);
                    editor.apply();
                    Log.d("test", " Showtweet result" + result);
                    analysisTweetJson(result);
                }
            });
        Httputil.comment_request(themeid, page + "", 20 + "", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                SharedPreferences sp = ShowTweet.this.getSharedPreferences("TweetsComment",
                        Context.MODE_PRIVATE);
                String result = sp.getString(themeid, null);
                Log.d("test", " Showtweet result comment_request " + result);
                if (result != null) {
                    analysisCommentJson(result);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ShowTweet.this,
                                    getResources().getString(R.string.connect_fail),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                SharedPreferences sp = ShowTweet.this.getSharedPreferences("TweetsComment",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(themeid, result);
                editor.apply();
                MainActivity.refreshed = true;
                analysisCommentJson(result);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.mipmap.half_transparentbitmap);
                final BitmapDrawable drawable = new BitmapDrawable(BlurUtils.blur(ShowTweet.this,
                        bitmap));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toolbar.setBackground(drawable);
                    }
                });
            }
        });
    }

    public void analysisTweetJson(String result) {
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

    public void analysisCommentJson(String result) {

        tweetComments = GsonUtils.getTweetComments(result);
        clist.clear();
        clist.addAll(tweetComments.data);
//        Log.d("akbr","commentid"+clist.get(0).id);
        Message message = new Message();
        message.what = 4;
        handler.sendMessage(message);

    }

    public void setcommentsdata() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new TweetsCommentAdapter(clist, ShowTweet.this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setdata(String html) {
        if (html.substring(0, 4).equals("http")) {
            linearLayout2.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            richEditor.setVisibility(View.GONE);

            WebSettings ws = webView.getSettings();

            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            ws.setBuiltInZoomControls(true);// 隐藏缩放按钮

            ws.setUseWideViewPort(true);// 可任意比例缩放
            ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview

            ws.setSavePassword(true);
            ws.setSaveFormData(true);// 保存表单数据
            ws.setJavaScriptEnabled(true);

            ws.setDomStorageEnabled(true);
            ws.setSupportMultipleWindows(true);// 新加

//            我就是没有这一行，死活不出来。MD，硬是没有人写这一句的
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
//            webView.setWebChromeClient(new WebChromeClient());
            webView.loadUrl(html);
        } else {
            linearLayout2.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            richEditor.setVisibility(View.VISIBLE);
            richEditor.loadRichEditorCode(html);
            richEditor.setOnClickImageTagListener(new RichEditor.OnClickImageTagListener() {
                @Override
                public void onClick(String url) {
                    Toast.makeText(ShowTweet.this, "url:" + url, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayout3:
                break;
            case R.id.tweet_post_comment:
                openwritearea();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void OnItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.comments_linear:
                openwritearea2(position);

                Toast.makeText(ShowTweet.this, "点击了整条评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tweet_comment_likes_area:
                Toast.makeText(ShowTweet.this, "点击了点赞", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tweet_comment_num:
                if (repliesDetails != null)
                    repliesDetails = null;
                repliesDetails = RepliesDetails.newInstance(clist.get(position));

                if (!repliesDetails.isAdded())
                    repliesDetails.show(getSupportFragmentManager(), "Dialog");
                Toast.makeText(ShowTweet.this, "点击了更多评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.comments_image_area:
                Toast.makeText(ShowTweet.this, "点击了头像", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void openwritearea() {
        if (writeComment != null)
            writeComment = null;
        writeComment = WriteComment.newInstance(themeid, "");
        if (!writeComment.isAdded()) {
            writeComment.show(getSupportFragmentManager(), "WriteDialog");
        }
    }

    public void openwritearea2(int position) {
        if (writeComment == null)
            writeComment = null;
        writeComment = WriteComment.newInstance1(clist.get(position).id + "",
                clist.get(position).person_id + "", clist.get(position).person_id + "",
                clist.get(position).username, true, clist.get(position).replies, position);

        if (!writeComment.isAdded()) {
            writeComment.show(getSupportFragmentManager(), "WriteDialog");
        }
    }
}
