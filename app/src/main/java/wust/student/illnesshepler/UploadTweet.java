package wust.student.illnesshepler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rex.editor.view.RichEditorNew;

import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.FileUtil;

public class UploadTweet extends AppCompatActivity implements View.OnClickListener {
    private RichEditorNew richEditor;
    private TextView tupian;
    private TextView jiacu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_tweet);

        richEditor = findViewById(R.id.richEditor);
        richEditor.setPadding(10, 10, 10, 10);

        tupian=(TextView) findViewById(R.id.upload_tupian);
        jiacu=(TextView) findViewById(R.id.upload_jiacu);
        tupian.setOnClickListener(this);
        jiacu.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.upload_tupian:
                Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
                intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intentToPickPic, 100);
//                richEditor.insertImage("http://47.100.93.91:8996/MediaFiles/mediaImages/9f52537a7a5583e2ac542c12d486f532.jpg");
                break;
            case R.id.upload_jiacu:
                richEditor.setBold();
                break;
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Log.d("test","uri"+uri);
                    richEditor.insertImage(""+uri);
                }
                break;
        }
    }
}
