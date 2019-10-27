package wust.student.illnesshepler.Fragments;

import
        android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import org.litepal.LitePal;

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
}
