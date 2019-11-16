package wust.student.illnesshepler.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.stx.xhb.xbanner.XBanner;
import com.yalantis.phoenix.PullToRefreshView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Adapters.TweetsListAdapter;
import wust.student.illnesshepler.Bean.Tweets;
import wust.student.illnesshepler.CustomViews.MyNestScrollView;
import wust.student.illnesshepler.InvestigationList;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.ShowTweet;
import wust.student.illnesshepler.Utils.GsonUtils;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.ScreenUtil;
import wust.student.illnesshepler.Utils.StatusBarUtil;


public class HomeFragment extends Fragment implements View.OnClickListener,
        TweetsListAdapter.OnItemClickListener, MyNestScrollView.MyScrollViewListener {

    private View view;
    private XBanner mXBanner;
    private LinearLayout sruvey;
    private LinearLayout libraries;
    private LinearLayout doctors;
    private LinearLayout tools;
    private LinearLayout toolbar;
    private PullToRefreshView refreshView;
    private NestedScrollView scrollView;

    private RecyclerView twRecyclerView;

    private List<String> images = new ArrayList<>();
    private List<Tweets.Item> mlist = new ArrayList<>();
    private List<Tweets.Item> xlist = new ArrayList<>();
    private Handler handler;
    private TweetsListAdapter tweetsListAdapter;
    private Tweets tweets;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //绑定和初始化控件
        initlayout();
        setadapter();
        getdata();




        //请求服务器数据 数据和控件绑定在onResponse 里调用setadapter（）
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1:
                        setbanner();
                }
                return false;
            }
        });

    }

    private void initlayout() {
        scrollView = view.findViewById(R.id.scrollViews);
        toolbar = view.findViewById(R.id.RainBowTitle);
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
        params.setMargins(0, StatusBarUtil.getStatusBarHeight(getContext()), 0, 0);
        toolbar.setLayoutParams(params);


        refreshView = view.findViewById(R.id.refreshLayout);
        sruvey = (LinearLayout) view.findViewById(R.id.survey);
        libraries = (LinearLayout) view.findViewById(R.id.libraries);
        doctors = (LinearLayout) view.findViewById(R.id.doctors);
        tools = (LinearLayout) view.findViewById(R.id.tools);
        twRecyclerView = (RecyclerView) view.findViewById(R.id.tweets_recycle);


        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        ScreenUtil.getScreenWidth(view.getContext()) / 2);
        mXBanner = (XBanner) view.findViewById(R.id.xbanner);
        mXBanner.setLayoutParams(layoutParams);
        mXBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                showtweet(position, xlist);
            }
        });

        sruvey.setOnClickListener(this);
        libraries.setOnClickListener(this);
        doctors.setOnClickListener(this);
        tools.setOnClickListener(this);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY,
                                       int oldScrollX, int oldScrollY) {
                refreshView.setEnabled(scrollY <= 0);
            }
        });
        refreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getdata();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getdata() {
        Httputil.sendokhttpNotificationList(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Message message=new Message();
                        Toast.makeText(getContext(), "无法连接至服务器，请稍后重试", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp=getActivity().getSharedPreferences("Tweets", Context.MODE_PRIVATE);
                        String result=sp.getString("tweets_result","");
                        if(result.equals("")||result==null)
                        {
                            message.what=2;
                        }
                        else {
                            analysisJson(result);
                        }
                        refreshView.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                SharedPreferences sp=getActivity().getSharedPreferences("Tweets", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("tweets_result", result);
                editor.apply();
                Log.d("test", "Home result" + result);
                analysisJson(result);
            }
        });

    }

    public void analysisJson(String result)
    {
        tweets = GsonUtils.handleMessages3(result);
        if (tweets != null) {
            mlist.clear();
            xlist.clear();
            for (int i = 0; i < tweets.data.size(); i++) {
                if (tweets.data.get(i).uploadtype.equals("1")) {
                    xlist.add(tweets.data.get(i));
                } else {
                    mlist.add(tweets.data.get(i));
                }
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tweetsListAdapter.notifyDataSetChanged();
                    refreshView.setRefreshing(false);
                }
            });
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    private void setadapter() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        twRecyclerView.setLayoutManager(manager);
        twRecyclerView.setNestedScrollingEnabled(false);
        tweetsListAdapter = new TweetsListAdapter(getContext(), mlist);
        tweetsListAdapter.setOnItemClickListener(this);  //监听
        twRecyclerView.setAdapter(tweetsListAdapter);


    }

    private void setbanner() {
        images.clear();
        for (int i = 0; i < xlist.size(); i++) {
            images.add(xlist.get(i).imageUrl);
        }
        Log.d("test", "HomeFragment images" + images);
        mXBanner.setAutoPlayAble(images.size() > 1);
        mXBanner.setIsClipChildrenMode(images.size() > 2);
        mXBanner.setData(images, null);
        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(view.getContext()).load(images.get(position))
                        .apply(new RequestOptions()
                                .transforms(new CenterCrop(), new RoundedCorners(16)))
                        .into((ImageView) view);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.survey:
                Intent intent = new Intent(getActivity(), InvestigationList.class);
                startActivity(intent);
                break;
            case R.id.libraries:
                Toast.makeText(getContext(), "你点击了资料", Toast.LENGTH_SHORT).show();
                break;
            case R.id.doctors:
                Toast.makeText(getContext(), "你点击了专家", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tools:
                Toast.makeText(getContext(), "你点击了工具", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void OnItemClick(int position) {
        showtweet(position, mlist);
    }

    private void showtweet(int position, List<Tweets.Item> list) {
        Intent intent = new Intent(view.getContext(), ShowTweet.class);
        Bundle bundle = new Bundle();
        bundle.putString("themeid", list.get(position).themeid);
        bundle.putString("time", list.get(position).post_time);
        bundle.putString("authername", list.get(position).username);
        bundle.putString("title", list.get(position).title);
        bundle.putInt("number", list.get(position).visitNum);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onScrollChanged(MyNestScrollView scrollView, int x, int y, int oldx, int oldy) {

    }
}
