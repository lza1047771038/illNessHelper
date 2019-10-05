package wust.student.illnesshepler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Banner banner;                    //图片轮播第三方依赖库
    List<String> images=new ArrayList<>();   //定义图片集合
    List<String> title =new ArrayList<>();   //定义活动标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initbaner();                        //轮播图片  baner
        initnavigationbar();
    }
    public void initnavigationbar()
    {
        BottomNavigationView bottom_navigation=findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(MainActivity.this,"你点击了首页",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_gongjv:
                        Toast.makeText(MainActivity.this,"你点击了工具",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_wenhao:
                        Toast.makeText(MainActivity.this,"你点击了待定",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_wenhao1:
                        Toast.makeText(MainActivity.this,"你点击了待定1",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    public void initbaner()
    {

        banner=findViewById(R.id.banner);
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
                Toast.makeText(MainActivity.this,"你点击了"+position+"个图片",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
