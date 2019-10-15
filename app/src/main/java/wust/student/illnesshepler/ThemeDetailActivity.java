package wust.student.illnesshepler;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.litepal.util.Const;

import wust.student.illnesshepler.Utils.StatusBarUtil;

public class ThemeDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    View statusBarBackground;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        StatusBarUtil.setStatusBarDarkTheme(this, true);

        setContentView(R.layout.themedetailactivity);

        statusBarBackground = findViewById(R.id.statusBarBackground);
        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(this);
        statusBarBackground.setLayoutParams(params);


        Init();

//        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(toolbar.getLayoutParams());
//        params1.topMargin=StatusBarUtil.getStatusBarHeight(this);
//        toolbar.setLayoutParams(params1);
        toolbar.setPaddingRelative(0,StatusBarUtil.getStatusBarHeight(this),0,0);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("动态");
        }
    }


    private void Init() {
        toolbar = findViewById(R.id.toolbar);
    }
}
