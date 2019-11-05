package wust.student.illnesshepler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.litepal.LitePal;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.Fragments.ChatFragment;
import wust.student.illnesshepler.Fragments.ClassFragment;
import wust.student.illnesshepler.Fragments.HomeFragment;
import wust.student.illnesshepler.Fragments.MeFragment;
import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.Utils.SensitiveWordsUtils;
import wust.student.illnesshepler.Utils.StatusBarUtil;

public class MainActivity extends AppCompatActivity {


    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    int REQUEST_CODE = 123;

    ViewPager viewPager;
    BottomNavigationView bottom_navigation;

    List<Fragment> fragmentList = new ArrayList<>();
    List<Integer> menuList = new ArrayList<>();

    HomeFragment home = new HomeFragment();
    ClassFragment classes = new ClassFragment();
    ChatFragment chat = new ChatFragment();
    MeFragment me = new MeFragment();

    static String authorid="13972008325";
    static String authorname="13147163155";
    static String user_image="hahah";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);

        getAuthorize(MainActivity.this,MainActivity.this);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


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

        litepal_init();//初始化数据库
        List<User_information> all = LitePal.findAll(User_information.class);//查询功能

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

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void litepal_init() {
        List<User_information> all = LitePal.findAll(User_information.class);//查询功能
        if (all.size() == 0) {
            User_information user_information = new User_information();
            user_information.setUser_Name("XXX");
            user_information.setUser_Age("0");
            user_information.setUser_Image_Uri(null);
            user_information.save();
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
            String[] permission = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(activity, permission, REQUEST_CODE);
            mPermissionList.clear();
            Toast.makeText(context,"授权失败，可能造成无法进入的问题",Toast.LENGTH_SHORT).show();
        }
    }

}
