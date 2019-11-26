package wust.student.illnesshepler.Activities.ClassAndWork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.Activities.Fragments.CurseFragment;
import wust.student.illnesshepler.Activities.Fragments.HomeworkFragment;
import wust.student.illnesshepler.CustomViews.MyViewPager;
import wust.student.illnesshepler.R;

public class CurseAndWork extends AppCompatActivity {


    MyViewPager viewPager;
    Fragment CurseFragment=new CurseFragment();
    Fragment HomeworkFragment=new HomeworkFragment();
    List<Fragment> fragmentList = new ArrayList<>();
    TextView Curse;
    TextView Homework;

    //aike
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    public SegmentTabLayout tabLayout;
    private String[] mTitles_2 = {"课程", "作业"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_and_work);

        init();

//        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @NonNull
//            @Override
//            public Fragment getItem(int position) {
//                return fragmentList.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return fragmentList.size();
//            }
//        };

//
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setOffscreenPageLimit(fragmentList.size() - 1);

//        Curse.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(0);
//                Curse.setTextSize(18);
//                Homework.setTextSize(12);
//            }
//        });
//
//        Homework.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(1);
//                Homework.setTextSize(18);
//                Curse.setTextSize(12);
//            }
//        });

    }

    public void init(){
        mFragments.add(CurseFragment);
        mFragments.add(HomeworkFragment);

        tabLayout = findViewById(R.id.tapbayout);

//        Curse=(TextView)findViewById(R.id.Curse);
//        Homework=(TextView)findViewById(R.id.HomeWork);
        tl_3();
        viewPager=findViewById(R.id.classANDwork);
        fragmentList.add(CurseFragment);
        fragmentList.add(HomeworkFragment);
    }
    private void tl_3() {
        final ViewPager vp_3 =(ViewPager) findViewById(R.id.vp_2);
        vp_3.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tabLayout.setTabData(mTitles_2);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp_3.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vp_3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_3.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles_2[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
