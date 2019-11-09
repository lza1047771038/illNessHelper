package wust.student.illnesshepler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rex.editor.view.RichEditor;
import com.rex.editor.view.RichEditorNew;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Adapters.TweetsCommentAdapter;
import wust.student.illnesshepler.Bean.GetTweetComments;
import wust.student.illnesshepler.Fragments.RepliesDetails;
import wust.student.illnesshepler.Utils.GsonUtils;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.RoundImageView;
import wust.student.illnesshepler.Utils.SensitiveWordsUtils;
import wust.student.illnesshepler.Utils.StatusBarUtil;
import wust.student.illnesshepler.Utils.Utils;

public class ShowTweet extends AppCompatActivity implements View.OnClickListener, TweetsCommentAdapter.OnItemClickListener {

    RichEditorNew richEditor;
    private TextView tweetTitle;
    private TextView tweetAuther;
    private TextView tweetTime;
    private TextView send;

    private EditText tweetPostComment;
    private EditText tweetPostCommentHigth;

    private ImageView collect;
    private ImageView share;

    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private NestedScrollView scrollView;

    Window window;

    private Handler handler;

    private String title;
    private String auther;
    private String time;
    private String themeid;
    private String html;
    private String number;
    private RecyclerView recyclerView;
    private int page = 1;

    private TweetsCommentAdapter adapter;

    public List<GetTweetComments.Comments> clist = new ArrayList<>();

    private GetTweetComments tweetComments;
    private RepliesDetails repliesDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NoAppTheme);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
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

        recyclerView = (RecyclerView) findViewById(R.id.show_comment_recycler);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setAlpha(0);
        toolbar.setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
        setSupportActionBar(toolbar);


        linearLayout = findViewById(R.id.linearLayout3);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        params.setMargins(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);
        linearLayout.setLayoutParams(params);
        linearLayout.setOnClickListener(this);
        tweetTitle = (TextView) findViewById(R.id.show_tweet_title);
        tweetAuther = (TextView) findViewById(R.id.show_tweet_auther);
        tweetTime = (TextView) findViewById(R.id.show_tweet_time);
        send = (TextView) findViewById(R.id.tweet_send_comment);

        tweetPostComment = (EditText) findViewById(R.id.tweet_post_comment);
        tweetPostCommentHigth = (EditText) findViewById(R.id.tweet_post_comment_higth);
        collect = (ImageView) findViewById(R.id.tweet_collect);
        share = (ImageView) findViewById(R.id.tweet_share);


        tweetPostComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tweetPostComment.setVisibility(View.GONE);
                    tweetPostCommentHigth.setVisibility(View.VISIBLE);
                    tweetPostCommentHigth.setFocusableInTouchMode(true);
                    tweetPostCommentHigth.requestFocus();
                    send.setVisibility(View.VISIBLE);
                    collect.setVisibility(View.GONE);
                    share.setVisibility(View.GONE);
                }
            }
        });
        tweetPostCommentHigth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    tweetPostComment.setVisibility(View.VISIBLE);
                    tweetPostCommentHigth.setVisibility(View.GONE);
                    send.setVisibility(View.GONE);
                    collect.setVisibility(View.VISIBLE);
                    share.setVisibility(View.VISIBLE);
                }
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.showSoftInput(tweetPostCommentHigth, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        tweetPostCommentHigth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    send.setTextColor(getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tweetPostComment.setOnClickListener(this);
        collect.setOnClickListener(this);
        share.setOnClickListener(this);
        send.setOnClickListener(this);
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
                switch (msg.what) {
                    case 1:
                        setdata(msg.obj + "");
                        break;
                    case 2:
                        Toast.makeText(ShowTweet.this, "发送成功", Toast.LENGTH_SHORT).show();
                        tweetPostCommentHigth.setText("");
                        tweetPostCommentHigth.clearFocus();
                        tweetPostComment.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        boolean isOpen = imm.isActive();
                        imm.hideSoftInputFromWindow(tweetPostCommentHigth.getWindowToken(), 0);
//                        GetTweetComments temp=new GetTweetComments();
                        GetTweetComments.Comments temp=new GetTweetComments.Comments();
                        temp.contains=msg.obj.toString();
                        temp.username="tempusername(用户登录时放到static 变量)";
                        temp.time=System.currentTimeMillis()+"";
                        temp.likes=0;
                        temp.userimage="temp";
                        temp.replies=0;
                        temp.comments_num=0;
                        clist.add(0, temp);
                        adapter.notifyDataSetChanged();
                        recyclerView.getLayoutManager().scrollToPosition(0);
                        break;
                    case 3:
                        Toast.makeText(ShowTweet.this, "发送失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        setcommentsdata();
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
        number = bundle.getInt("number", 0)+"";
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
        tweetAuther.setText(auther);
        tweetTime.setText(Utils.timeFormat(time));

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
        Httputil.comment_request(themeid, page + "", 20 + "", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.d("test", " Showtweet result comment_request " + result);
                tweetComments = GsonUtils.getTweetComments(result);
                clist.clear();
                clist.addAll(tweetComments.data);
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);

            }
        });
    }

    public void setcommentsdata() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new TweetsCommentAdapter(clist, ShowTweet.this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tweet_send_comment:
                sendComment();
                break;
            case R.id.linearLayout3:
                tweetPostCommentHigth.clearFocus();
                tweetPostComment.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tweetPostCommentHigth.getWindowToken(), 0);
                break;
            default:
                tweetPostCommentHigth.clearFocus();
                tweetPostComment.clearFocus();
                InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.hideSoftInputFromWindow(tweetPostCommentHigth.getWindowToken(), 0);
                break;
        }
    }

    public void sendComment() {
        final String contains = tweetPostCommentHigth.getText().toString();
        if (contains.length() == 0) {
        } else {
            if (!SensitiveWordsUtils.contains(contains)) {
                Log.d("test", "Show Tweet comment_post" + themeid);
                Log.d("test", "Show Tweet comment_post" + MainActivity.authorid);
                Log.d("test", "Show Tweet comment_post" + contains);
                Httputil.comment_post(themeid, MainActivity.authorid, contains, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String reslut = response.body().string();
                        Message message = new Message();
                        if (reslut.equals("1")) {
                            message.what = 2;
                            message.obj=contains;
                        } else {
                            message.what = 2;
                            message.obj="null";
                        }
                        handler.sendMessage(message);
                    }
                });//String Themeid,String Userid ,String Contains,
            } else {
                Toast.makeText(ShowTweet.this, "包含铭感词汇，请检查评论内容", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void OnItemClick(View view, int position) {
        tweetPostCommentHigth.clearFocus();
        tweetPostComment.clearFocus();
        InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm2.hideSoftInputFromWindow(tweetPostCommentHigth.getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.comments_linear:
                Toast.makeText(ShowTweet.this, "点击了整条评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tweet_comment_likes_area:
                Toast.makeText(ShowTweet.this, "点击了点赞", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tweet_comment_num:
                if (repliesDetails == null) {
                    repliesDetails = RepliesDetails.newInstance(123L);
                }
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
}
