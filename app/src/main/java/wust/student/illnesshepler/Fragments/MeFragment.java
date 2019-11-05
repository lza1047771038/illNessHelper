package wust.student.illnesshepler.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import org.litepal.LitePal;

import java.util.List;

import wust.student.illnesshepler.R;
import wust.student.illnesshepler.UploadTweet;
import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.Utils.FileUtil;
import wust.student.illnesshepler.Utils.StatusBarUtil;
import wust.student.illnesshepler.Edit_Userdata;

public class MeFragment extends Fragment implements View.OnClickListener {
    private View view, statusBarBackground;
    private ImageView imageView;
    private TextView AdministratorEntry,User_Name;

    User_information information;

    FileUtil fileUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        statusBarBackground = view.findViewById(R.id.statusBarBackground);
        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusBarBackground.setLayoutParams(params);

        AdministratorEntry=(TextView)view.findViewById(R.id.administrator_entry);

        AdministratorEntry.setOnClickListener(this);

        final Intent intent = new Intent(getContext(), Edit_Userdata.class);
        ConstraintLayout Edit_information = (ConstraintLayout) view.findViewById(R.id.Edit_information);
        Edit_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void setUserName(){
        User_Name=(TextView)view.findViewById(R.id.User_Name);
        List<User_information> all = LitePal.findAll(User_information.class);//查询功能
        User_Name.setText(all.get(0).getUser_Name());
    }

    private void setImageView() {
        imageView = (ImageView) view.findViewById(R.id.imageView);
        List<User_information> all = LitePal.findAll(User_information.class);//查询功能
        if (all.get(0).getUser_Image_Uri() != null)
            imageView.setImageBitmap(fileUtil.getBitmap(all.get(0).getUser_Image_Uri()));
    }

    public void onStart() {
        super.onStart();
        setImageView();
        setUserName();
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(view.getContext(), UploadTweet.class);
        startActivity(intent);
    }
}
