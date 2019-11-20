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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rex.editor.view.RichEditorNew;


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
import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.Httputil;
import wust.student.illnesshepler.Utils.ImageUtil;
import wust.student.illnesshepler.Utils.ScreenUtil;
import wust.student.illnesshepler.Utils.StatusBarUtil;

public class UploadTweet extends AppCompatActivity implements View.OnClickListener, ColorChooserDialog.ColorCallback {
    ActionBar actionBar;
    Drawable drawable;
    Handler handler;
    private RichEditorNew richEditor;
    private TextView tupian;
    private TextView jiacu;
    private String file = MainActivity.ImagesDruaction;
    private ImageView upload_img;
    private ImageView upload_right;
    private ImageView upload_left;
    private ImageView upload_center;
    private ImageView text_backgrond;
    private TextView text_color;
    private TextView font_b;
    private Spinner textSize;
    private EditText update_title;

    boolean Bold_flog = false;
    private String themeid;
    private long submittime;
    List<String> names = new ArrayList<String>();
    List<String> imglist = new ArrayList<String>();   public String html = "";
    public String headerimage ;
    private String uploadtype="0";
    public int size = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//全屏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_upload_tweet);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        //标题栏
        drawable = getDrawable(R.color.white);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("推文编辑");
            actionBar.setBackgroundDrawable(drawable);
        }
//初始化控件
        initlayout();
        //通过handeler判断完成网络数据
        handlemesgge();
    }

    public void initlayout() {
        richEditor = findViewById(R.id.richEditor);
        richEditor.setPadding(10, 10, 10, 10);
        richEditor.setFontSize(size);
        richEditor.setHint("请输入正文");
        textSize = (Spinner) findViewById(R.id.test_size);

        update_title = (EditText) findViewById(R.id.update_title);
        upload_img = (ImageView) findViewById(R.id.upload_img);
        upload_right = (ImageView) findViewById(R.id.upload_right);
        upload_left = (ImageView) findViewById(R.id.upload_left);
        upload_center = (ImageView) findViewById(R.id.upload_center);
        text_backgrond = (ImageView) findViewById(R.id.text_backgrond);
        text_color = (TextView) findViewById(R.id.text_color);
        font_b = (TextView) findViewById(R.id.font_b);

        upload_img.setOnClickListener(this);
        upload_right.setOnClickListener(this);
        upload_left.setOnClickListener(this);
        upload_center.setOnClickListener(this);
        text_backgrond.setOnClickListener(this);
        text_color.setOnClickListener(this);
        font_b.setOnClickListener(this);

        textSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] item = getResources().getStringArray(R.array.test_size);
                size = Integer.valueOf(item[position].toString());
                richEditor.setFontSize(size);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void handlemesgge() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {  //设置北京颜色
                    Log.d("test", "msg1" + msg.arg1);
                    richEditor.setTextBackgroundColor(msg.arg1);
                }
                if (msg.what == 2) { //字体颜色
                    Log.d("test", "msg2" + msg.arg1);
                    richEditor.setTextColor(msg.arg1);
                }
                if (msg.what == 3) {  //图片上传完成
//                    Toast.makeText(UploadTweet.this, "result" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject imageurl = new JSONObject(msg.obj.toString());
                        JSONArray allimageurl = imageurl.getJSONArray("ImageList");
                        if(allimageurl.length()!=0) {
                            headerimage = allimageurl.get(0).toString() + "";
                        }
                        else {
                            headerimage ="null";
                        }
                        names.clear();
                        for (int i = 0; i < allimageurl.length(); i++) {
                            names.add(allimageurl.get(i).toString());
                            Log.d("test", allimageurl.get(i).toString());
                        }

                    } catch (JSONException e) {

                    }
                    html = richEditor.getHtml() + "";
                    for (int i = 0; i < imglist.size(); i++) {
                        html = html.replaceAll(imglist.get(i), names.get(i));
                        Log.d("test", "after  html :" + html);
                    }
                    //第二次发送数据 发送编辑推文信息
                    secondsubmit(html);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_submit:
                final String[] array = {"普通的推文","首页上方的轮播推文"};
                MaterialDialog chosedialog = new MaterialDialog.Builder(this)
                        .title("请选择上传类型")
                        .content("默认为普通的推文")
                        .items(array)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                uploadtype=which+"";
                                Log.d("test", "UploadTweet   选了 :"+which);
                                return true;
                            }
                        })
                        .positiveText("确认")
                        .negativeText("取消")
                        .negativeColor(getColor(R.color.optioncolorcolor))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                submit();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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

    //第一次发送请求多图片上传
    public void submit() {
        submittime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        themeid = "NFT" + format.format(submittime);
        html = richEditor.getHtml();
        imglist = richEditor.getAllSrcAndHref();

        Httputil.ImagesUpload(themeid, imglist, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("test", "ImagesUpload   onFailure :");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();

                Message message = new Message();
                message.what = 3;
                message.obj = result;
                handler.sendMessage(message);
                Log.d("test", "ImagesUpload   result :" + result);
            }


        });


    }

    //第二次发送数据 发送编辑推文信息
    public void secondsubmit(String contains) {
        String title = update_title.getText().toString();
        Httputil.NotificationPost(MainActivity.userInfo.getPhoneid(),themeid,MainActivity.userInfo.getUserId(), title, contains,submittime + "",headerimage ,uploadtype, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UploadTweet.this, "onFailure", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", result);
                        if (result.equals("1"))
                            Toast.makeText(UploadTweet.this, "成功", Toast.LENGTH_SHORT).show();
                        else if(result.equals("2"))
                        {
                            Toast.makeText(UploadTweet.this, "异地登陆", Toast.LENGTH_SHORT).show();
                        }
                        else if (result.equals("0"))
                            Toast.makeText(UploadTweet.this, "失败", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(UploadTweet.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_img:
                InsertImg();
                break;
            case R.id.upload_right:
                richEditor.setAlignRight();
                break;
            case R.id.upload_left:
                richEditor.setAlignLeft();
                break;
            case R.id.upload_center:
                richEditor.setAlignCenter();
                break;
            case R.id.text_backgrond:
                showdilog("background");

                break;
            case R.id.text_color:
                showdilog("text_color");
                break;
            case R.id.font_b:

                richEditor.setBold();
                if (Bold_flog == false) {
                    Log.d("test", "Bold_flog " + false);
                    font_b.getPaint().setFakeBoldText(true);
                    font_b.setTextColor(getResources().getColor(R.color.optioncolorcolor));
                    Bold_flog = true;
                } else if (Bold_flog == true) {
                    Log.d("test", "Bold_flog  " + true);
                    font_b.getPaint().setFakeBoldText(false);
                    font_b.setTextColor(getResources().getColor(R.color.darkgray));
                    Bold_flog = false;
                }
//                richEditor.loadRichEditorCode(tttt);
                break;
//            case R.id.test_size:
//                showtestsizedilog();
//                richEditor.setFontSize(size);
//                break;

        }


    }

    //选择图片
    public void InsertImg() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, 100);
    }

    //选择图片回调  以及压缩插入图片
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
                    final String path = ImageUtil.getRealPathFromURI(UploadTweet.this, uri) + "";

                    final String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                    Glide.with(this).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            bitmap = ImageUtil.compressBmpToFile(bitmap, file + "/" + name, 500); //压缩图片 为1024
                            int width = bitmap.getWidth();
                            int screenwdith = (ScreenUtil.getScreenWidth(UploadTweet.this));
                            int temp = ScreenUtil.px2sp(UploadTweet.this, screenwdith);
                            int bili = (width * 100 / temp);
                            Toast.makeText(getApplication(), width + "/" + screenwdith + "=" + path, Toast.LENGTH_LONG).show();
                            if (bili < 100) {
                                String style = "margin-top:10px;max-width:" + bili + "%;";
                                richEditor.setAlignCenter();
                                richEditor.insertImage("" + file + "/" + name, "picvision", style);
                                richEditor.setAlignLeft();
                            } else {
                                richEditor.insertImage("" + file + "/" + name);
                            }
                        }
                    });
                }
                break;
        }
    }

    //调用dialog
    public void showdilog(String flog) {
        new ColorChooserDialog.Builder(UploadTweet.this, R.string.app_name)
                .titleSub(R.string.input_hint)  // title of dialog when viewing shades of a color
                .tag(flog)
                .accentMode(false)  // when true, will display accent palette instead of primary palette
                .doneButton(R.string.md_done)  // changes label of the done button
                .cancelButton(R.string.md_cancel)  // changes label of the cancel button
                .backButton(R.string.md_back)  // changes label of the back button
                .preselect(Color.RED)  // 开始的时候的默认颜色
                .dynamicButtonColor(true)// defaults to true, false will disable changing action buttons' color to currently selected color
                .show();

    }

    //选中颜色的颜色
    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, int selectedColor) {
        if (dialog.tag().equals("background")) {
            Message message = new Message();
            message.what = 1;
            message.arg1 = selectedColor;
            handler.sendMessage(message);
        }
        if (dialog.tag().equals("text_color")) {
            Message message = new Message();
            message.what = 2;
            message.arg1 = selectedColor;
            handler.sendMessage(message);
        }

    }

    //退出确认框
    public void showexitdilog() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("您确认要退出吗")
                .content("点击确认推出")
                .positiveText("确认")
                .negativeText("取消")
                .negativeColor(getColor(R.color.optioncolorcolor))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
