package wust.student.illnesshepler.Fragments;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

//import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.InvestigationList;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.ScreenUtil;
import wust.student.illnesshepler.Utils.StatusBarUtil;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View view, statusBarBackground;
    private XBanner mXBanner;
    private TabLayout tabLayout;
    private LinearLayout sruvey;
    private LinearLayout libraries;
    private LinearLayout doctors;
    private LinearLayout tools;

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
        statusBarBackground = view.findViewById(R.id.statusBarBackground);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(view.getContext()) / 2);
        mXBanner = (XBanner) view.findViewById(R.id.xbanner);
        mXBanner.setLayoutParams(layoutParams);

        sruvey = (LinearLayout) view.findViewById(R.id.survey);
        libraries = (LinearLayout) view.findViewById(R.id.libraries);
        doctors = (LinearLayout) view.findViewById(R.id.doctors);
        tools = (LinearLayout) view.findViewById(R.id.tools);

        sruvey.setOnClickListener(this);
        libraries.setOnClickListener(this);
        doctors.setOnClickListener(this);
        tools.setOnClickListener(this);

        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusBarBackground.setLayoutParams(params);
        layoutInit();
    }

    private void layoutInit() {
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);  //圆点指示器和标题其他默认
        images.add("http://47.100.93.91/MediaFiles/mediaImages/ad555577f82d5221349638bbba35d917.jpg");
        images.add("http://47.100.93.91/MediaFiles/mediaImages/1.jpg");
        images.add("http://47.100.93.91/MediaFiles/mediaImages/1.jpg");  //图片路径

        title.add("第一个图片");
        title.add("第二个图片");
        title.add("第三个图片");

        mXBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getContext().getResources().getDisplayMetrics());
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, getContext().getResources().getDisplayMetrics());
                Glide.with(view.getContext()).load(images.get(position))
                        .apply(new RequestOptions()
                                .transforms(new CenterCrop(), new RoundedCorners(16)))
                        .into((ImageView) view);
            }
        });
        mXBanner.setAutoPlayAble(images.size() > 1);
        mXBanner.setIsClipChildrenMode(true);
        mXBanner.setData(images, title);
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
                Toast.makeText(getContext(), "你点击了调查", Toast.LENGTH_SHORT).show();
                break;
            case R.id.doctors:
                Toast.makeText(getContext(), "你点击了调查", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tools:
                Toast.makeText(getContext(), "你点击了调查", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
