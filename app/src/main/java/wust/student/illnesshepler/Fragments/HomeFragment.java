package wust.student.illnesshepler.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

//import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.stx.xhb.xbanner.XBanner;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import wust.student.illnesshepler.Adapters.InvestigationListAdapter;
import wust.student.illnesshepler.Adapters.TweetsListAdapter;
import wust.student.illnesshepler.Bean.Tweets;
import wust.student.illnesshepler.Investigation;
import wust.student.illnesshepler.InvestigationList;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.GsonUtils;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.ScreenUtil;
import wust.student.illnesshepler.Utils.StatusBarUtil;

import static org.litepal.LitePalApplication.getContext;

public class HomeFragment extends Fragment implements View.OnClickListener, TweetsListAdapter.OnItemClickListener {

    public View view, statusBarBackground;
    public XBanner mXBanner;
    public TabLayout tabLayout;
    public LinearLayout sruvey;
    public LinearLayout libraries;
    public LinearLayout doctors;
    public LinearLayout tools;
    public LinearLayout tweet_linear;

    public RecyclerView twRecyclerView;


    public List<String> images = new ArrayList<>();
    public List<String> title = new ArrayList<>();
    public List<Tweets.Item> mlist=new ArrayList<>();

    public TweetsListAdapter tweetsListAdapter;
    public Tweets tweets;

    public List<String> idlist=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        tabLayout = view.findViewById(R.id.tabLayout);
        statusBarBackground = view.findViewById(R.id.statusBarBackground);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(view.getContext()) / 2);
        mXBanner = (XBanner) view.findViewById(R.id.xbanner);
        mXBanner.setLayoutParams(layoutParams);

        sruvey = (LinearLayout) view.findViewById(R.id.survey);
        libraries = (LinearLayout) view.findViewById(R.id.libraries);
        doctors = (LinearLayout) view.findViewById(R.id.doctors);
        tools = (LinearLayout) view.findViewById(R.id.tools);

        twRecyclerView=(RecyclerView)view.findViewById(R.id.tweets_recycle) ;

        sruvey.setOnClickListener(this);
        libraries.setOnClickListener(this);
        doctors.setOnClickListener(this);
        tools.setOnClickListener(this);

        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusBarBackground.setLayoutParams(params);

        getdata();


    }
    public void getdata(){
        Httputil.sendokhttpNotificationList(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.d("test","run"+result);

                tweets= GsonUtils.handleMessages3(result);
                mlist.addAll(tweets.data);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layoutInit();
                        tweetsListAdapter.notifyDataSetChanged();
                    }
                });
            }

        });

    }
    //    public void getUserName(String id,int postion) {
//
//        Httputil.sendokhttpqueryForUserInfo(id,new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                idlist.add("null");
//                Log.d("test","null ");
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//
//                String result = response.body().string();
//                Log.d("test",result);
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    Log.d("test","jj"+jsonObject.getString("username"));
//                    idlist.add(jsonObject.getString("username"));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
    private void layoutInit() {
        Log.d("test","run2");
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);  //圆点指示器和标题其他默认
        images.add("http://47.100.93.91:8996/MediaFiles/mediaImages/de8394d1b5492cb065574cbbc1c589e8.jpg");
        images.add("http://47.100.93.91:8996/MediaFiles/mediaImages/9f52537a7a5583e2ac542c12d486f532.jpg");
        images.add("http://47.100.93.91:8996/MediaFiles/mediaImages/1.jpg");  //图片路径

        title.add("第一个图片");
        title.add("第二个图片");
        title.add("第三个图片");

        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(view.getContext()).load(images.get(position))
                        .apply(new RequestOptions()
                                .transforms(new CenterCrop(), new RoundedCorners(16)))
                        .into((ImageView) view);
            }
        });
        mXBanner.setAutoPlayAble(images.size() > 1);
        mXBanner.setIsClipChildrenMode(true);
        mXBanner.setData(images, title);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        twRecyclerView.setLayoutManager(manager);
        tweetsListAdapter = new TweetsListAdapter(getContext(),mlist);
        tweetsListAdapter.setOnItemClickListener(this);  //监听
        twRecyclerView.setAdapter(tweetsListAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.survey:
                Intent intent = new Intent(getActivity().getApplicationContext(), InvestigationList.class);
                startActivity(intent);
                ;
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
        Toast.makeText(getContext(), "你点击了第"+position+"个item  ", Toast.LENGTH_SHORT).show();
    }
}
