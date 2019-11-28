package wust.student.illnesshepler.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.litepal.LitePal;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import wust.student.illnesshepler.Activities.Fragments.Main.ChatFragment;
import wust.student.illnesshepler.Activities.Fragments.Main.ClassFragment;
import wust.student.illnesshepler.Activities.Fragments.Main.HomeFragment;
import wust.student.illnesshepler.Activities.Fragments.Main.MeFragment;
import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.CustomViews.BottomNavigationViewItemClickListenner;
import wust.student.illnesshepler.CustomViews.MyViewPager;
import wust.student.illnesshepler.CustomViews.OnScrollViewStateChangeListenner;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.SensitiveWordsUtils;

public class MainActivity extends AppCompatActivity implements OnScrollViewStateChangeListenner {

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    int REQUEST_CODE = 123;

    MyViewPager viewPager;
    RelativeLayout bottomNavigationViewHolder;
    BottomNavigationView bottom_navigation;
    BottomNavigationViewItemClickListenner listenner;

    List<Fragment> fragmentList = new ArrayList<>();
    List<Integer> menuList = new ArrayList<>();

    HomeFragment home = new HomeFragment();
    ClassFragment classes = new ClassFragment();
    ChatFragment chat = new ChatFragment();
    MeFragment me = new MeFragment();
    //本地保存图片的路径
    public final static String ImagesDruaction =
            Environment.getExternalStorageDirectory().getPath() + "/illnesshepler" + "/Images";
    public final static String PdfDDruaction = Environment.getExternalStorageDirectory().getPath();

    public static User_information userInfo;
    public static boolean refreshed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);
        setUserInfo();
        getAuthorize(MainActivity.this, MainActivity.this);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        menuList.add(R.id.navigation_home);
        menuList.add(R.id.navigation_class);
        menuList.add(R.id.navigation_chat);
        menuList.add(R.id.navigation_me);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottomNavigationViewHolder = findViewById(R.id.relativeLayout);

        viewPager = findViewById(R.id.MainActivity_ViewPager);
        fragmentList.add(home);
        fragmentList.add(classes);
        fragmentList.add(chat);
        fragmentList.add(me);

        InputStream inputStream = getResources().openRawResource(R.raw.keywords);    //获取敏感词汇库
        SensitiveWordsUtils.initSensitiveWord(inputStream);                         //加载敏感词汇工具类

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

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        if (viewPager.getCurrentItem() == 0) {
                            home.onClick();
                        }
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

    private long oldMillions = 0;

    protected void onQuit() {
        if (System.currentTimeMillis() - oldMillions > 2000) {
            oldMillions = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onQuit();
            return true;
        }
        return false;
    }

    public void setUserInfo() {
        List<User_information> all = LitePal.findAll(User_information.class);//查询功能
        if (all.size() != 0) {
            userInfo = all.get(0);
        }
    }

    public void getAuthorize(Context context, Activity activity) {
        List<String> mPermissionList = new ArrayList<>();// 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
        mPermissionList.clear();
        for (String s : permissions)
            if (ContextCompat.checkSelfPermission(context, s) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(s);
            }
        if (!mPermissionList.isEmpty()) {
            String[] permission = mPermissionList.toArray(new String[mPermissionList.size()]);
            //将List转为数组
            ActivityCompat.requestPermissions(activity, permission, REQUEST_CODE);
            mPermissionList.clear();
            Toast.makeText(context, "授权失败，可能造成无法进入的问题", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean isBottomShow = true;

    public void onScrollStateChanged(int scrollY, int oldscrollY) {
        if (scrollY - oldscrollY > 0 && isBottomShow) {
            isBottomShow = false;
            //将Y属性变为底部栏高度 (相当于隐藏了)
            bottomNavigationViewHolder.animate().setDuration(700).translationY(bottomNavigationViewHolder.getHeight());
        } else if (scrollY - oldscrollY < 0 && !isBottomShow) {
            isBottomShow = true;
            bottomNavigationViewHolder.animate().setDuration(700).translationY(0);
        }
        /*if(scrollY<bottomNavigationViewHolder.getHeight()){
            bottomNavigationViewHolder.setTranslationY(scrollY);
        }*/
    }
}
