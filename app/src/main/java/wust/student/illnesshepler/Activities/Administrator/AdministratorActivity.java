package wust.student.illnesshepler.Activities.Administrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import wust.student.illnesshepler.R;

public class AdministratorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView local;
    private TextView internet;
    private TextView classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Drawable drawable = getDrawable(R.color.white);

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("管理员界面");
            actionBar.setBackgroundDrawable(drawable);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_administrator);

        local = (TextView) findViewById(R.id.upload_local);
        internet = (TextView) findViewById(R.id.upload_internet);
        classes = (TextView) findViewById(R.id.upload_class);

        local.setOnClickListener(this);
        internet.setOnClickListener(this);
        classes.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_local:
                Intent intent = new Intent(AdministratorActivity.this, UploadTweet.class);
                startActivity(intent);
                break;
            case R.id.upload_internet:
                Intent intent1 = new Intent(AdministratorActivity.this, UploadFromInternet.class);
                startActivity(intent1);
                break;
            case R.id.upload_class:
//                Intent intent2=new Intent(AdministratorActivity.this,UploadTweet.class);
//                startActivity(intent2);
                break;

        }
    }
}
