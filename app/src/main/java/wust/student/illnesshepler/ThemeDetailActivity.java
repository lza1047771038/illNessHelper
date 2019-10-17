package wust.student.illnesshepler;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import wust.student.illnesshepler.Utils.StatusBarUtil;

public class ThemeDetailActivity extends AppCompatActivity {

    View statusBarBackground;
    ScrollView scrollView;
    ActionBar actionBar;
    Drawable drawable;
    Window window;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setStatusBarDarkTheme(this, true);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window = getWindow();
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        StatusBarUtil.setStatusBarDarkTheme(this, true);

        setContentView(R.layout.themedetailactivity);

        Init();
        RootLayoutInit();

    }

    private void setLayoutSettings() {

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int x, int y, int x1, int y1) {
                if (y >= 0 && y < 400) {
                    double que = (double) y % 400.0 / 400.0;
                    drawable.setAlpha((int) (que * 255));
                    actionBar.setBackgroundDrawable(drawable);

                    window.setStatusBarColor(Color.argb((int) (que * 255), 255, 255, 255));
                } else if (y >= 400 && y < 500) {
                    drawable.setAlpha(255);
                    actionBar.setBackgroundDrawable(drawable);
                    window.setStatusBarColor(Color.argb(255, 255, 255, 255));
                } else if (y < 0) {
                    drawable.setAlpha(0);
                    actionBar.setBackgroundDrawable(drawable);
                    window.setStatusBarColor(Color.argb(0, 255, 255, 255));
                }
            }
        });
    }

    public void RootLayoutInit(){
        statusBarBackground = findViewById(R.id.statusBarBackground);
        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(this);
        statusBarBackground.setLayoutParams(params);


        scrollView.setPadding(0, scrollView.getPaddingTop(), 0, 0);

        drawable = getDrawable(R.color.white);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("动态");
            drawable.setAlpha(0);
            actionBar.setBackgroundDrawable(drawable);
        }


        setLayoutSettings();
    }


    private void Init() {
        scrollView = findViewById(R.id.scrollViews);
    }
}
