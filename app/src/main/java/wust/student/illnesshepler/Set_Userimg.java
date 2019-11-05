package wust.student.illnesshepler;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.Utils.FileUtil;
import wust.student.illnesshepler.Utils.StatusBarUtil;

import static org.litepal.LitePalApplication.getContext;

public class Set_Userimg extends AppCompatActivity {

    View statusBarBackground;
    Uri uri;
    ImageView user_img;
    FileUtil fileUtil;
    String Tag = "checkpoint";

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    int REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__userimg);

        Button btn_select_img = (Button) findViewById(R.id.select_img);
        user_img = (ImageView) findViewById(R.id.img);


        btn_select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAuthorize(Set_Userimg.this, Set_Userimg.this)) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 1);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        statusBarBackground = findViewById(R.id.statusBarBackground);
        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusBarBackground.setLayoutParams(params);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                user_img.setImageURI(uri);
                FileUtil fileUtil = new FileUtil();
                String photo_uri = fileUtil.getFileAbsolutePath(this, uri);
                Up_Img_Uri("User_Image_Uri", photo_uri);
            }
    }

    private void Up_Img_Uri(String name, String uri1) {
        ContentValues values = new ContentValues();
        values.put(name, uri1);
        Log.d("test_LitePal", "Up_Img_Uri:" + uri1);
        LitePal.updateAll(User_information.class, values);
    }

    public void onStart() {
        super.onStart();
        Log.d(Tag, "onReStart_Set");
        List<User_information> all = LitePal.findAll(User_information.class);//查询功能
        if (all.get(0).getUser_Image_Uri() != null)
            user_img.setImageBitmap(fileUtil.getBitmap(all.get(0).getUser_Image_Uri()));
    }

    public boolean getAuthorize(Context context, Activity activity) {
        List<String> mPermissionList = new ArrayList<>();// 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++)
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        if (mPermissionList.isEmpty()) {
            return true;
        } else {
            String[] permission = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(activity, permission, REQUEST_CODE);
            mPermissionList.clear();
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//允许
                Toast.makeText(getContext(), "授权成功", Toast.LENGTH_SHORT).show();
                return;
            } else if (grantResults[0]==PackageManager.PERMISSION_DENIED){//拒绝
                Toast.makeText(getContext(), "授权失败", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
