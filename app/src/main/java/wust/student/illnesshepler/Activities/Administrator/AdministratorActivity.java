package wust.student.illnesshepler.Activities.Administrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import wust.student.illnesshepler.R;

public class AdministratorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView local;
    private TextView internet;
    private TextView classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("管理员");
        this.setTitleColor(getResources().getColor(R.color.white));
        setContentView(R.layout.activity_administrator);

        local = (TextView) findViewById(R.id.upload_local);
        internet = (TextView) findViewById(R.id.upload_internet);
        classes = (TextView) findViewById(R.id.upload_class);

        local.setOnClickListener(this);
        internet.setOnClickListener(this);
        classes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_local:
                Intent intent=new Intent(AdministratorActivity.this,UploadTweet.class);
                startActivity(intent);
                break;
            case R.id.upload_internet:
                Intent intent1=new Intent(AdministratorActivity.this,UploadFromInternet.class);
                startActivity(intent1);

                break;
            case R.id.upload_class:
//                Intent intent2=new Intent(AdministratorActivity.this,UploadTweet.class);
//                startActivity(intent2);
                break;

        }
    }
}
