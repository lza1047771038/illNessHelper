package wust.student.illnesshepler.Login_Enrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import wust.student.illnesshepler.MainActivity;
import wust.student.illnesshepler.R;

public class Login extends AppCompatActivity {

    TextView enrol;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        enrol=findViewById(R.id.enrol);
        btn_login=findViewById(R.id.btn_login);

        enrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Enrol.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }
}
