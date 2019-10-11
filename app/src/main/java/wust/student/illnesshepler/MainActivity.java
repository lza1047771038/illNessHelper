package wust.student.illnesshepler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Bean.GetTheme;
import wust.student.illnesshepler.Fragments.ChatFragment;
import wust.student.illnesshepler.Fragments.ClassFragment;
import wust.student.illnesshepler.Fragments.HomeFragment;
import wust.student.illnesshepler.Fragments.MeFragment;
import wust.student.illnesshepler.Fragments.ToolsFragment;
import wust.student.illnesshepler.Utills.GlideImageLoader;
import wust.student.illnesshepler.Utills.Httputil;
import wust.student.illnesshepler.Bean.Posting;
import wust.student.illnesshepler.Utills.StatusBarUtil;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView bottom_navigation;

    List<Fragment> fragmentList = new ArrayList<>();
    List<Integer> menuList = new ArrayList<>();

    HomeFragment home = new HomeFragment();
    ClassFragment classes = new ClassFragment();
    ChatFragment chat = new ChatFragment();
    ToolsFragment tools = new ToolsFragment();
    MeFragment me = new MeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }

        StatusBarUtil.setStatusBarDarkTheme(this, true);
        setContentView(R.layout.activity_main);

        menuList.add(R.id.navigation_home);
        menuList.add(R.id.navigation_class);
        menuList.add(R.id.navigation_chat);
        menuList.add(R.id.navigation_tools);
        menuList.add(R.id.navigation_me);

        bottom_navigation = findViewById(R.id.bottom_navigation);

        viewPager = findViewById(R.id.MainActivity_ViewPager);
        fragmentList.add(home);
        fragmentList.add(classes);
        fragmentList.add(chat);
        fragmentList.add(tools);
        fragmentList.add(me);


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

        viewPager.setAdapter(pagerAdapter);   //设置适配器
        viewPager.setOffscreenPageLimit(fragmentList.size() - 1); //预加载剩下两页
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottom_navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_class:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_chat:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.navigation_tools:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.navigation_me:
                        viewPager.setCurrentItem(4);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
