package wust.student.illnesshepler.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.Bean.Posting;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utills.GlideImageLoader;

public class HomeFragment extends Fragment {


    View view;
    Banner banner;
    ScrollView scrollView;
    RecyclerView recyclerView;


    List<Posting> postingsList = new ArrayList<>();  //帖子List
    List<String> images = new ArrayList<>();   //定义图片集合
    List<String> title = new ArrayList<>();   //定义活动标题

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        scrollView = view.findViewById(R.id.scrollViews);
        banner = view.findViewById(R.id.banner);
        recyclerView =  view.findViewById(R.id.posting_recicle);
        layoutInit();
    }

    public void layoutInit(){


        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);  //圆点指示器和标题其他默认
        images.add("https://img.ivsky.com/img/bizhi/slides/201908/12/the_angry_birds_movie.jpg");
        images.add("https://img.ivsky.com/img/bizhi/slides/201909/04/ne_zha-005.jpg");
        images.add("https://img.ivsky.com/img/bizhi/pre/201908/12/the_angry_birds_movie-004.jpg");  //图片路径

        title.add("第一个图片");
        title.add("第二个图片");
        title.add("第三个图片");
        banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
        banner.setImages(images);  //设置banner中显示图片
        banner.setBannerTitles(title);
        banner.start();  //设置完毕后调用

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(getContext(), "你点击了" + position + "个图片", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
