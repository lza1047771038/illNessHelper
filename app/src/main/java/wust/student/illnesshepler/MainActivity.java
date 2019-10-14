package wust.student.illnesshepler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.Fragments.ChatFragment;
import wust.student.illnesshepler.Fragments.ClassFragment;
import wust.student.illnesshepler.Fragments.HomeFragment;
import wust.student.illnesshepler.Fragments.MeFragment;
import wust.student.illnesshepler.Fragments.ToolsFragment;
import wust.student.illnesshepler.Utils.StatusBarUtil;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView bottom_navigation;

    List<Fragment> fragmentList = new ArrayList<>();
    List<Integer> menuList = new ArrayList<>();

    HomeFragment home = new HomeFragment();
    ClassFragment classes = new ClassFragment();
    ChatFragment chat = new ChatFragment();
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
        }

        StatusBarUtil.setStatusBarDarkTheme(this, true);
        setContentView(R.layout.activity_main);

        menuList.add(R.id.navigation_home);
        menuList.add(R.id.navigation_class);
        menuList.add(R.id.navigation_chat);
        menuList.add(R.id.navigation_me);

        bottom_navigation = findViewById(R.id.bottom_navigation);

        viewPager = findViewById(R.id.MainActivity_ViewPager);
        fragmentList.add(home);
        fragmentList.add(classes);
        fragmentList.add(chat);
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

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size() - 1);
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
                    case R.id.navigation_me:
                        viewPager.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
