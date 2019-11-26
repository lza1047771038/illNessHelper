package wust.student.illnesshepler.Activities.ClassAndWork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_and_work);

        init();

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };


        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size() - 1);

        Curse.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                Curse.setTextSize(18);
                Homework.setTextSize(12);
            }
        });

        Homework.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                Homework.setTextSize(18);
                Curse.setTextSize(12);
            }
        });

    }

    public void init(){
        Curse=(TextView)findViewById(R.id.Curse);
        Homework=(TextView)findViewById(R.id.HomeWork);

        viewPager=findViewById(R.id.classANDwork);
        fragmentList.add(CurseFragment);
        fragmentList.add(HomeworkFragment);
    }
}
