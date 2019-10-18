package wust.student.illnesshepler;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.litepal.LitePal;

import java.io.File;
import java.util.List;

import wust.student.illnesshepler.R;
import wust.student.illnesshepler.User_Information_LitePal.User_information;
import wust.student.illnesshepler.Utils.FileUtil;

public class Set_Userimg extends AppCompatActivity {

    Uri uri;
    ImageView user_img;
    FileUtil fileUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__userimg);

        Button btn_select_img=(Button)findViewById(R.id.select_img);
        user_img=(ImageView)findViewById(R.id.img);


        btn_select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,1);
            }
        });

        List<User_information> all = LitePal.findAll(User_information.class);//查询功能
        if(all.get(0).getUser_Image_Uri()!=null)
            user_img.setImageBitmap(fileUtil.getBitmap(all.get(0).getUser_Image_Uri()));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                user_img.setImageURI(uri);
                FileUtil fileUtil=new FileUtil();
                String photo_uri=fileUtil.getFileAbsolutePath(this,uri);
                Up_Img_Uri("User_Image_Uri",photo_uri);
            }
    }

    private void Up_Img_Uri(String name,String uri1){
        ContentValues values=new ContentValues();
        values.put(name,uri1);
        Log.d("test_LitePal", "Up_Img_Uri:"+uri1);
        LitePal.updateAll(User_information.class,values);
    }

}
