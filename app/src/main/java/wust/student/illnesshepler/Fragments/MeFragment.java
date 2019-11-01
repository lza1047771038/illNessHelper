package wust.student.illnesshepler.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import
        android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import wust.student.illnesshepler.R;
import wust.student.illnesshepler.User_Information_LitePal.User_information;
import wust.student.illnesshepler.Utils.FileUtil;
import wust.student.illnesshepler.Utils.StatusBarUtil;
import wust.student.illnesshepler.Edit_Userdata;

public class MeFragment extends Fragment {
    View view, statusBarBackground;
    ImageView imageView;
    FileUtil fileUtil;
    String Tag="checkpoint";



    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAuthorize(getContext(),getActivity());
        statusBarBackground = view.findViewById(R.id.statusBarBackground);
        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusBarBackground.setLayoutParams(params);

        final Intent intent = new Intent(getContext(), Edit_Userdata.class);
        ConstraintLayout Edit_information = (ConstraintLayout) view.findViewById(R.id.Edit_information);
        Edit_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void setImageView(){
        imageView = (ImageView) view.findViewById(R.id.imageView);
        List<User_information> all = LitePal.findAll(User_information.class);//查询功能
        if (all.get(0).getUser_Image_Uri() != null)
            imageView.setImageBitmap(fileUtil.getBitmap(all.get(0).getUser_Image_Uri()));
    }

    public void onStart(){
        super.onStart();
        Log.d(Tag,"Me_onStart");
        setImageView();
    }
    public void getAuthorize(Context context, Activity activity) {
        int REQUEST_CODE=1;
        List<String> mPermissionList = new ArrayList<>();// 声明一个集合，在后面的代码中用来存储用户拒绝授权的权

        mPermissionList.clear();
        while (true) {
            for (int i = 0; i < permissions.length; i++)
                if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            if (mPermissionList.isEmpty())
                break;
            else {
                String[] permission = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(activity, permission, REQUEST_CODE++);
                mPermissionList.clear();
            }
        }
    }

}
