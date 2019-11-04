package wust.student.illnesshepler;

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
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.LoginFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import wust.student.illnesshepler.Utils.Dialog_prompt;
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
    private String file = Environment.getExternalStorageDirectory().getPath() + "/illnesshepler" + "/Images";
    private ImageView upload_img;
    private ImageView upload_right;
    private ImageView upload_left;
    private ImageView upload_center;
    private ImageView text_backgrond;
    private TextView text_color;
    private TextView font_b;
    private Spinner textSize;
    boolean Bold_flog = false;
    List<String> names = new ArrayList<String>() ;
    //    public String html="<img src=\"content://media/external/images/media/270642\" alt=\"picvision\" style=\"margin-top:10px;max-width:100%;\"><br><div style=\"text-align: center;\">哈哈哈<img src=\"content://media/external/images/media/221012\" alt=\"picvision\" style=\"margin-top: 10px; max-width: 100%;\"></div><div style=\"text-align: center;\">傻逼</div><div style=\"text-align: center;\"><img src=\"content://media/external/images/media/258545\" alt=\"picvision\" style=\"margin-top: 10px; max-width: 100%;\"></div><br>\n";
    public String html = "<img src=\"content://media/external/images/media/258545\" alt=\"picvision\" 72\"=\"\" width=\"72\"><br><br>";
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
            drawable.setAlpha(0);
            actionBar.setBackgroundDrawable(drawable);
        }


        initlayout();


//        textSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                size= Integer.parseInt(textSize.getItemAtPosition(position).toString());
//            }
//        });


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
                submit();
                break;
            case android.R.id.home:
                showexitdilog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submit() {
        long submittime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        final String themeid="NFT"+format.format(submittime);
        html = richEditor.getHtml();
        final List<String> imglist = richEditor.getAllSrcAndHref();
        Httputil.ImagesUpload(themeid, imglist, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("test", "ImagesUpload   onFailure :");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
               String result = response.body().string();

                try {
                    JSONObject imageurl = new JSONObject(result);
                    JSONArray allimageurl=imageurl.getJSONArray("ImageList");
                    for (int i = 0; i < allimageurl.length(); i++) {
                        names.add(allimageurl.get(i).toString());
                        Log.d("test",  allimageurl.get(i).toString());
                    }
                    html = richEditor.getHtml() + "";
                    Log.d("test", "before  html :" + html);
                    for (int i = 0; i < imglist.size(); i++) {
                        html = html.replaceAll(imglist.get(i), names.get(i));
                        Log.d("test", "after  html :" + html);
                    }
                } catch (JSONException e) {

                }
                Log.d("test", "ImagesUpload   result :" + result);
            }
        });

    }

    public void initlayout() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    Log.d("test", "msg1" + msg.arg1);
                    richEditor.setTextBackgroundColor(msg.arg1);
                } else {
                    Log.d("test", "msg2" + msg.arg1);
                    richEditor.setTextColor(msg.arg1);
                }
                return false;
            }
        });


        richEditor = findViewById(R.id.richEditor);
        richEditor.setPadding(10, 10, 10, 10);
        richEditor.setFontSize(size);
        richEditor.setHint("请输入正文");
        textSize = (Spinner) findViewById(R.id.test_size);

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
//                richEditor.loadRichEditorCode(html);
                break;
            case R.id.test_size:
                showtestsizedilog();
                richEditor.setFontSize(size);
                break;

        }


    }


    public void InsertImg() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, 100);
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
                    final String path = ImageUtil.getRealPathFromURI(UploadTweet.this, uri) + "";

                    final String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                    Glide.with(this).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            bitmap = ImageUtil.compressBmpToFile(bitmap, file + "/" + name, 1024); //压缩图片 为1024
                            int width = bitmap.getWidth();
                            int screenwdith = (ScreenUtil.getScreenWidth(UploadTweet.this));
                            int temp = ScreenUtil.px2sp(UploadTweet.this, screenwdith);
                            int bili = (width * 100 / temp);
                            Toast.makeText(getApplication(), width + "/" + screenwdith + "=" + path, Toast.LENGTH_LONG).show();
                            if (bili < 100) {
                                String style = "margin-top:10px;max-width:" + bili + "%;";
                                richEditor.insertImage("" + file + "/" + name, "picvision", style);
                            } else {
                                richEditor.insertImage("" + file + "/" + name);
                            }
                        }
                    });
                }
                break;
        }
    }

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

    public void showtestsizedilog() {
        new MaterialDialog.Builder(this)
                .title("标题")
                .positiveText("确认")
                .negativeText("取消")
                .autoDismiss(true)//自动消失
                .items(R.array.test_size)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        size = Integer.valueOf(text.toString());

                        Toast.makeText(UploadTweet.this, size + "", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }

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
