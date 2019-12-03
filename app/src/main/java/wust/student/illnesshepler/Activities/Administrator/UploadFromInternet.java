package wust.student.illnesshepler.Activities.Administrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Activities.LoginAndRegister.LoginActivity;
import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.CustomViews.MyErrorDialog;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.ImageUtil;
import wust.student.illnesshepler.Utils.ScreenUtil;
import wust.student.illnesshepler.Utils.StatusBarUtil;

import static org.litepal.LitePalApplication.getContext;

public class UploadFromInternet extends AppCompatActivity implements View.OnClickListener {
    public ActionBar actionBar;
    public Drawable drawable;
    ;
    private String uploadtype = "0";
    private long submittime;
    private String themeid;
    List<String> imglist = new ArrayList<String>();
    public String html = "";
    private EditText upload_title;
    private EditText url;
    private ImageView img;
    private String submitUrl;
    private Handler handler;

    final String[] array = {"普通的推文", "首页上方的轮播推文"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_upload_from_internet);
        //标题栏
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.upload_from_internet);
        }
        initlayout();
    }

    public void initlayout() {
        upload_title = (EditText) findViewById(R.id.t_i_title);
        url = (EditText) findViewById(R.id.t_i_url);
        img = (ImageView) findViewById(R.id.t_i_img);
        img.setOnClickListener(this);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 1:
                        JSONObject imageurl = null;
                        try {
                            imageurl = new JSONObject(msg.obj.toString());
                            JSONArray allimageurl = imageurl.getJSONArray("ImageList");
                            if (allimageurl.length() != 0) {
                                submitUrl = allimageurl.get(0).toString() + "";
                            } else {
                                submitUrl = "null";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        secondsubmit();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tweet_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showexitdilog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_submit:
                MaterialDialog chosedialog = new MaterialDialog.Builder(this)
                        .title("请选择上传类型")
                        .content("默认为普通的推文")
                        .items(array)
                        .itemsCallbackSingleChoice(0,
                                new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView,
                                                               int which, CharSequence text) {
                                        uploadtype = which + "";
                                        Log.d("test", "UploadTweet   选了 :" + which);
                                        return true;
                                    }
                                })
                        .positiveText("确认")
                        .negativeText("取消")
                        .negativeColor(getColor(R.color.optioncolorcolor))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog,
                                                @NonNull DialogAction which) {
                                submit();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog,
                                                @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                //第一次发送请求多图片上传
                break;
            case android.R.id.home:
                showexitdilog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //退出确认框
    public void showexitdilog() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.quit_title)
                .content(R.string.quit_message)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .negativeColor(getColor(R.color.optioncolorcolor))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //第一次发送请求多图片上传
    public void submit() {
        submittime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        themeid = "NFT" + format.format(submittime);
        if (imglist.size() != 0) {
            Httputil.ImagesUpload(themeid, imglist, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d("test", "ImagesUpload   onFailure :");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String result = response.body().string();

                    Message message = new Message();
                    message.what = 1;
                    message.obj = result;
                    handler.sendMessage(message);
                    Log.d("test", "ImagesUpload   result :" + result);
                }
            });
        } else {
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.t_i_img:
                Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
                intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intentToPickPic, 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        int width;
        int height;
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();
                    final String path = ImageUtil.getRealPathFromURI(UploadFromInternet.this,
                            uri) + "";

                    final String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                    Glide.with(this).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<
                                ? super Bitmap> transition) {
                            Bitmap bitmap1 = ImageUtil.compressBmpToFile(bitmap,
                                    MainActivity.ImagesDruaction + "/" + name, 500); //压缩图片 为1024
                            img.setImageBitmap(bitmap1);
                            imglist.clear();
                            imglist.add(MainActivity.ImagesDruaction + "/" + name);
                        }
                    });
                }

                break;
        }
    }

    //第二次发送数据 发送编辑推文信息
    public void secondsubmit() {
        String contains = url.getText().toString();
        String title = upload_title.getText().toString();
        Httputil.NotificationPost(MainActivity.userInfo.getPhoneid(), themeid,
                MainActivity.userInfo.getUserId(), title, contains, submittime + "", submitUrl,
                uploadtype, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UploadFromInternet.this, "onFailure",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("test  result", result);
                                if (result.equals("1"))
                                    Toast.makeText(UploadFromInternet.this, "成功",
                                            Toast.LENGTH_SHORT).show();
                                else if (result.equals("2")) {
                                    Toast.makeText(UploadFromInternet.this, "异地登陆",
                                            Toast.LENGTH_SHORT).show();
                                    MyErrorDialog dialog = new MyErrorDialog(getContext());
                                    dialog.setCancelable(false);
                                    dialog.setOnButtonClickListener(new MyErrorDialog.OnButtonClickListener() {
                                        @Override
                                        public void onPositiveButtonClicked() {
                                            startActivity(new Intent(UploadFromInternet.this,
                                                    LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        }
                                    });
                                    dialog.show();
                                } else if (result.equals("0"))
                                    Toast.makeText(UploadFromInternet.this, "失败", Toast.LENGTH_SHORT).show();
                                else {
                                    Toast.makeText(UploadFromInternet.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }
}
