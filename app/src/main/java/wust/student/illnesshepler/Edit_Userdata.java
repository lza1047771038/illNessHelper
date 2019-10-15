package wust.student.illnesshepler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Edit_Userdata extends AppCompatActivity {

    private View v,statusBarBackground;
    AlertDialog.Builder builder;
    TextView user_name;
    TextView user_age;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__userdata);

        v=getLayoutInflater().inflate(R.layout.dialoglayout,null);

        builder=new AlertDialog.Builder(this);

        LinearLayout User_img=(LinearLayout)findViewById(R.id.edit_user_img);
        LinearLayout User_age=(LinearLayout)findViewById(R.id.edit_user_age);
        LinearLayout User_integra=(LinearLayout)findViewById(R.id.edit_user_integral);
        LinearLayout User_name=(LinearLayout)findViewById(R.id.edit_user_name);

        user_name=(TextView)findViewById(R.id.user_name);
        user_age=(TextView)findViewById(R.id.user_age);
        input=v.findViewById(R.id.input);

        User_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Edit_Userdata.this, Set_Userimg.class);
                startActivity(intent);
            }
        });

        User_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(user_name.getText().toString());
                username_edit();
            }
        });

        User_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(user_age.getText().toString());
                userage_edit();
            }
        });
    }

    public void username_edit(){
        final String[] name = {""};
        builder.setTitle("修改用户名");
        if(v.getParent()!=null){
            ViewGroup vg=(ViewGroup)v.getParent();
            vg.removeView(v);
        }

        builder.setView(v);

        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name[0] =input.getText().toString();
                user_name.setText(name[0]);
                Toast.makeText(Edit_Userdata.this,"已经修改用户名",Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Edit_Userdata.this,"已取消，当前用户名："+user_name.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void userage_edit(){
        builder.setTitle("修改年龄");
        if(v.getParent()!=null){
            ViewGroup vg=(ViewGroup)v.getParent();
            vg.removeView(v);
        }

        builder.setView(v);

        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isNumericZidai(input.getText().toString())) {
                    user_age.setText(input.getText().toString());
                    Toast.makeText(Edit_Userdata.this, "已经修改年龄", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Edit_Userdata.this, "年龄只能是数字奥", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Edit_Userdata.this,"已取消，当前年龄："+user_age.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public static boolean isNumericZidai(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
