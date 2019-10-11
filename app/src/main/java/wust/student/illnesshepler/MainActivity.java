package wust.student.illnesshepler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import wust.student.illnesshepler.Fragments.HomeFragment;
import wust.student.illnesshepler.Utills.GlideImageLoader;
import wust.student.illnesshepler.Utills.Httputil;
import wust.student.illnesshepler.Adapters.ListAdapterPostings;
import wust.student.illnesshepler.Bean.Posting;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView bottom_navigation;

    List<Fragment> fragmentList = new ArrayList<>();
    HomeFragment home = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation = findViewById(R.id.bottom_navigation);

        viewPager = findViewById(R.id.MainActivity_ViewPager);
        fragmentList.add(home);

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

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_gongjv:
                        break;
                    case R.id.navigation_wenhao:
                        break;
                    case R.id.navigation_wenhao1:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
