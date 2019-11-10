package wust.student.illnesshepler.Fragments;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Adapters.TweetsCommentAdapter;
import wust.student.illnesshepler.Bean.GetTweetComments;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.ShowTweet;
import wust.student.illnesshepler.Utils.GsonUtils;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.Utils;

public class RepliesDetails extends BottomSheetDialogFragment implements TweetsCommentAdapter.OnItemClickListener, View.OnClickListener {

    private View view, nestScrollView;
    private Toolbar toolbar;
    private RelativeLayout relativeLayout;
    public static RecyclerView mrecyclerView;

    public TextView userName;
    public TextView time;
    public TextView contains;
    public TextView likesNum;
    public ImageView userImg;
    public LinearLayout likesArea;

    private String root;
    private String id;
    private int page=1;
    private int pagesize=20;
    private Handler handler;
    private RepliesDetails repliesDetails;
    private WriteComment writeComment;
    private BottomSheetBehavior bottomSheetBehavior;

    public static TweetsCommentAdapter replyAdapter;
    public  static List<GetTweetComments.Comments> replyList = new ArrayList<>();
    private GetTweetComments tweetComments;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_repliesdetails, null);
        dialog.setContentView(view);

        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_repliesdetails, container, false);
        InitViews();
        setdata();
        handlemesgge();
        getdata();
        return view;
    }

    public void setdata()
    {
        Bundle bundle = getArguments();
        userName.setText(bundle.getString("username"));
       time.setText(Utils.timeFormat(bundle.getString("time")));
       contains.setText(bundle.getString("contains"));
       likesNum.setText(bundle.getString("likes"));
        Glide.with(getContext()).load(bundle.getString("userimage")).apply(new RequestOptions().transforms(new CircleCrop())).error(R.drawable.postcard).into(userImg);
    }
    public void getdata()
    {
        Bundle args = getArguments();
        root = args.getString("root", "null");
        id = args.getString("id", "null");
        Httputil.reply_request(root, id, page+"", pagesize+"", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result=response.body().string();
                tweetComments = GsonUtils.getTweetComments(result);
                replyList.clear();
                replyList.addAll(tweetComments.data);
                Message message = new Message();
                message.what = 4;
                handler.sendMessage(message);

                Log.d("test","reply_request:"+result);
            }
        });
    }
    public void handlemesgge() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 4:
                        setcommentsdata();
                        break;
                }
                return false;
            }
        });
    }
    public void setcommentsdata() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        mrecyclerView.setLayoutManager(manager);
        replyAdapter = new TweetsCommentAdapter(replyList, getContext());
        replyAdapter.setOnItemClickListener(this);
        mrecyclerView.setAdapter(replyAdapter);
    }
    private void InitViews() {
        userName=view.findViewById(R.id.reply_auther);
        time=view.findViewById(R.id.reply_time);
        contains=view.findViewById(R.id.reply_contains);
        likesNum=view.findViewById(R.id.reply_likes_num);
        userImg=view.findViewById(R.id.reply_img);
        likesArea=view.findViewById(R.id.reply_likes_area);

        likesArea.setOnClickListener(this);


        nestScrollView = view.findViewById(R.id.scrollViews);
        mrecyclerView = view.findViewById(R.id.show_reply_recycler);
        mrecyclerView.setNestedScrollingEnabled(false);

        getActivity().setActionBar(toolbar);
        ActionBar actionbar = getActivity().getActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        ((View) getView().getParent()).setBackground(getResources().getDrawable(R.drawable.toolbar_round_corner));
        if (getDialog() != null && getDialog().getWindow() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Window window = getDialog().getWindow();
            window.findViewById(com.google.android.material.R.id.container).setFitsSystemWindows(false);
            window.findViewById(com.google.android.material.R.id.coordinator).setFitsSystemWindows(false);
            // dark navigation bar icons
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
    }


    // 构造方法
    public static RepliesDetails newInstance(GetTweetComments.Comments comments) {
        Bundle args = new Bundle();
        args.putString("root",comments.person_id+"");
        args.putString("id",comments.id+"");
        args.putString("contains",comments.contains+"");
        args.putString("username",comments.username+"");
        args.putString("userimage",comments.userimage+"");
        args.putString("likes",comments.likes+"");
        args.putString("time",comments.time+"");
        RepliesDetails fragment = new RepliesDetails();
        fragment.setArguments(args);
        return fragment;
    }

    public void show(@NotNull FragmentManager manager, String tag) {
        super.show(manager, tag);
    }


    @Override
    public void OnItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.comments_linear:
                openwritearea(position);

                Toast.makeText(getContext(), "点击了整条评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tweet_comment_likes_area:
                Toast.makeText(getContext(), "点击了点赞", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tweet_comment_num:
//                if (repliesDetails == null)
                    repliesDetails = RepliesDetails.newInstance(replyList.get(position));
                if (!repliesDetails.isAdded())
                    repliesDetails.show(getActivity().getSupportFragmentManager(), "Dialog");
                Toast.makeText(getContext(), "点击了更多评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.comments_image_area:
                Toast.makeText(getContext(), "点击了头像", Toast.LENGTH_SHORT).show();
                break;

        }
    }
    public void openwritearea(int position)
    {
        if(writeComment==null)
        {
            //String id,String root,String parentid
            Log.d("test","writeComment==null");
            writeComment=WriteComment.newInstance1(id+"",root,replyList.get(position).person_id+"",replyList.get(position).username);
        }
        if(!writeComment.isAdded())
        {
            Log.d("test","writeComment.isAdded()");
            writeComment.show(getActivity().getSupportFragmentManager(),"WriteDialog");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.reply_likes_area:
                Toast.makeText(getContext(), "点赞了", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
