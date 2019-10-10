package wust.student.illnesshepler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.Adapter.ListAdapterPostings;
import wust.student.illnesshepler.Bean.Postem;
import wust.student.illnesshepler.Bean.Postings;
import wust.student.illnesshepler.Bean.Result;

public class MainActivity extends AppCompatActivity {
    private List<Postings> postingsList= new ArrayList<>();  //帖子List
    private Banner banner;                    //图片轮播第三方依赖库
    List<String> images=new ArrayList<>();   //定义图片集合
    List<String> title =new ArrayList<>();   //定义活动标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String typeJson1 = "{\n" +
                "  \"status\": \"1\", \n" +
                "  \"data\": [\n" +
                "   {\n" +
                "      \"theme_id\": 19, \n" +
                "      \"author_id_id\": 18, \n" +
                "      \"contains\": \"文本文本文本文本文本文本文本文本\", \n" +
                "      \"time\": 19, \n" +
                "      \"likes\": 44, \n" +
                "      \"comments_num\": 2\n" +
                "       },\n" +
                "  {\n" +
                "      \"theme_id\": 20, \n" +
                "      \"author_id_id\": 20, \n" +
                "      \"contains\": \"文本文本文本文本文本文本文本文本\", \n" +
                "      \"time\": 19, \n" +
                "      \"likes\": 44, \n" +
                "      \"comments_num\": 50\n" +
                "       }\n" +
                "]\n" +
                "\n" +
                "}";

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.posting_recicle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ListAdapterPostings adapter = new ListAdapterPostings(postingsList);
        recyclerView.setAdapter(adapter);



        initbaner();                        //轮播图片  baner
        initnavigationbar();
        initcerycletestdata();
        initposting();
    }
    public void initcerycletestdata()
    {
        for (int i = 0; i < 10; i++) {
            Postings postings=new Postings();
            postings.setAuthor_id(i);
            postings.setTime(1910092109);
             postings.setComments_num(10);
            postings.setContains("这是帖子内容/n这是帖子内容/n这是帖子内容/n这是帖子内容/" +
                    "n这是帖子内容/n这是帖子内容/n这是帖子内容/n这是帖子内容/n这是帖子内容/n"+i);
            postings.setLikes(99);
            postingsList.add(postings);

        }
    }
    public void initposting()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.posting_recicle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ListAdapterPostings adapter = new ListAdapterPostings(postingsList);
        recyclerView.setAdapter(adapter);
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
