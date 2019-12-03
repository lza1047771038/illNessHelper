package wust.student.illnesshepler.Activities.SetUserInfo;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yalantis.ucrop.UCrop;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.FileUtil;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.PictureEditor;

import static org.litepal.LitePalApplication.getContext;

public class Set_Userimg extends AppCompatActivity {
    private Handler handler;
    PromptDialog promptDialog;

    Uri uri;
    ImageView user_img;
    TextView btn_select_img, btn_used_img;
    FileUtil fileUtil;
    PictureEditor pictureEditor;
    Bitmap NewBmp = null;
    LinearLayout linearLayout_roung;
    boolean isRequest = true;

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    int REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__userimg);

        btn_select_img =  findViewById(R.id.select_img);
        btn_used_img = findViewById(R.id.used_img);
        user_img = (ImageView) findViewById(R.id.img);
        linearLayout_roung = (LinearLayout) findViewById(R.id.linearLayout4);


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

        btn_used_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NewBmp != null) {
                    NewBmp = pictureEditor.getBitMapFromImageView(user_img);
                    upLoadImage(fileUtil.getFileAbsolutePath(Set_Userimg.this,
                            BmpToUri(Set_Userimg.this, NewBmp)));
                } else
                    Toast.makeText(Set_Userimg.this, "请选择图片", Toast.LENGTH_SHORT).show();
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {

                    case 1:
                        promptDialog.showSuccess("上传成功");
                        finish();
                        break;
                    case 2:
                        promptDialog.showError("上传失败，请稍后重试");
                        break;
                }
                return false;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.head_portrait);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri destinationUri = Uri.fromFile(new File(getExternalCacheDir(), "uCrop.jpg"));
                UCrop.of(data.getData(), destinationUri)
                        .withOptions(options())
                        .start(this);
            } else if (requestCode == UCrop.REQUEST_CROP) {
                uri = UCrop.getOutput(data);
                Log.d("tag123", uri.toString());
                isRequest = false;
                try {
                    user_img.setImageURI(uri);
                    NewBmp = pictureEditor.getBitmapFormUri(Set_Userimg.this, uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //裁剪框架样式
    public UCrop.Options options(){
        UCrop.Options options = new UCrop.Options();
        options.setToolbarWidgetColor(Color.parseColor("#ffffff"));//标题字的颜色以及按钮颜色
        options.setDimmedLayerColor(Color.parseColor("#AA000000"));//设置裁剪外颜色
        options.setToolbarColor(Color.parseColor("#000000")); // 设置标题栏颜色
        options.setStatusBarColor(Color.parseColor("#000000"));//设置状态栏颜色
        return options;
    }

    public void upLoadImage(String url) {
        promptDialog = new PromptDialog(this);
        promptDialog.showLoading("正在上传，请勿退出");
        List<String> pathList = new ArrayList<>();
        pathList.clear();
        pathList.add(url);
        Httputil.ImagesUpload("", pathList, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String ressult = response.body().string();

                try {
                    JSONObject imageurl = new JSONObject(ressult);
                    JSONArray allimageurl = imageurl.getJSONArray("ImageList");
                    String url = allimageurl.get(0).toString() + "";
                    MainActivity.userInfo.setUser_Image_Uri(url);
                    Up_Img_Uri("User_Image_Uri", url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("test", "result" + ressult);
            }
        });

    }

    private void Up_Img_Uri(String name, String uri1) {
        ContentValues values = new ContentValues();
        values.put(name, uri1);
        LitePal.updateAll(User_information.class, values);
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }

    public void onStart() {
        super.onStart();
        if (isRequest) {
            List<User_information> all = LitePal.findAll(User_information.class);//查询功能
            if (all.get(0).getUser_Image_Uri() != null) {
                Glide.with(this).load(all.get(0).getUser_Image_Uri()).apply(new RequestOptions().transforms(new CenterCrop())).into(user_img);
            }
            isRequest = true;
        }
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
            String[] permission = mPermissionList.toArray(new String[mPermissionList.size()]);
            //将List转为数组
            ActivityCompat.requestPermissions(activity, permission, REQUEST_CODE);
            mPermissionList.clear();
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//允许
                Toast.makeText(getContext(), "授权成功", Toast.LENGTH_SHORT).show();
                return;
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {//拒绝
                Toast.makeText(getContext(), "授权失败", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public Uri BmpToUri(Context c, Bitmap bitmap) {

        File path = new File(c.getCacheDir() + File.separator + System.currentTimeMillis());
        try {
            OutputStream os = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();
            return Uri.fromFile(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
