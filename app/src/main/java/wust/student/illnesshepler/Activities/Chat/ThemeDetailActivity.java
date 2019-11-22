package wust.student.illnesshepler.Activities.Chat;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ScrollView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.StatusBarUtil;

public class ThemeDetailActivity extends AppCompatActivity {

    ScrollView scrollView;
    ActionBar actionBar;
    Window window;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.NoAppTheme);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window = getWindow();
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        StatusBarUtil.setStatusBarDarkTheme(this, true);

        setContentView(R.layout.themedetailactivity);

        Init();

        setSupportActionBar(toolbar);
        RootLayoutInit();

    }

    private void setLayoutSettings() {

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int x, int y, int x1, int y1) {
                int screen_height =
                        getApplicationContext().getResources().getDisplayMetrics().heightPixels;
                if (y <= screen_height / 3f) {
                    toolbar.setAlpha(y / (screen_height / 3f));
                }
            }
        });
    }

    public void RootLayoutInit() {

        toolbar.setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);

        scrollView.setPadding(0, StatusBarUtil.getStatusBarHeight(this), 0, 0);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("动态");
        }
        setLayoutSettings();
    }

    private void Init() {
        scrollView = findViewById(R.id.scrollViews);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setAlpha(0);
    }
}
