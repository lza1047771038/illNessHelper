package wust.student.illnesshepler.Fragments;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.InvestigationList;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.GlideImageLoader;
import wust.student.illnesshepler.Utils.StatusBarUtil;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view, statusBarBackground;
    private Banner banner;
    private TabLayout tabLayout;
    private TextView sruvey;
    private TextView libraries;
    private TextView doctors;
    private TextView tools;

    private List<String> images = new ArrayList<>();
    private List<String> title = new ArrayList<>();

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

        banner = view.findViewById(R.id.banner);
        statusBarBackground = view.findViewById(R.id.statusBarBackground);

        sruvey    =(TextView)view.findViewById(R.id.survey   );
        libraries =(TextView)view.findViewById(R.id.libraries);
        doctors   =(TextView)view.findViewById(R.id.doctors  );
        tools     =(TextView)view.findViewById(R.id.tools    );

        sruvey   .setOnClickListener(this);
        libraries.setOnClickListener(this);
        doctors  .setOnClickListener(this);
        tools    .setOnClickListener(this);
        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusBarBackground.setLayoutParams(params);
        layoutInit();
    }

    private void layoutInit() {
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

        banner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 10);
            }
        });


        banner.setClipToOutline(true);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.survey    :
                    Intent intent=new Intent(getActivity().getApplicationContext(), InvestigationList.class);
                    startActivity(intent);
                ; break;
            case R.id.libraries :  Toast.makeText(getContext(), "你点击了调查", Toast.LENGTH_SHORT).show(); break;
            case R.id.doctors   :  Toast.makeText(getContext(), "你点击了调查", Toast.LENGTH_SHORT).show(); break;
            case R.id.tools     :  Toast.makeText(getContext(), "你点击了调查", Toast.LENGTH_SHORT).show(); break;
        }
    }
}
