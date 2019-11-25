package wust.student.illnesshepler.Activities.Fragments.Main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import wust.student.illnesshepler.Activities.Administrator.AdministratorActivity;
import wust.student.illnesshepler.Activities.MainActivity;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Bean.User_information;
import wust.student.illnesshepler.Utils.BlurUtils;
import wust.student.illnesshepler.Utils.FileUtil;
import wust.student.illnesshepler.Activities.SetUserInfo.Edit_Userdata;

public class MeFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView imageView, MybackgroundImageView;
    private TextView User_Name;
    private RelativeLayout AdministratorEntry, Edit_information, About;

    FileUtil fileUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_me, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void InitViews() {
        About = view.findViewById(R.id.fragment_me_about);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        MybackgroundImageView = (ImageView) view.findViewById(R.id.Mybackground);
        AdministratorEntry = (RelativeLayout) view.findViewById(R.id.administrator_entry);
        User_Name = (TextView) view.findViewById(R.id.User_Name);
        Edit_information = (RelativeLayout) view.findViewById(R.id.Edit_information);
    }

    private void InitLayout() {
        AdministratorEntry.setOnClickListener(this);
        Edit_information.setOnClickListener(this);
        About.setOnClickListener(this);
    }

    private void setUserName() {
        if (MainActivity.userInfo.getUser_Name() != null)
            User_Name.setText(MainActivity.userInfo.getUser_Name());
    }

    private void setImageView() {
        if (MainActivity.userInfo.getUser_Image_Uri() != null) {
            Glide.with(this).load(MainActivity.userInfo.getUser_Image_Uri()).apply(new RequestOptions().transforms(new CenterCrop())).into(imageView);
            BlurBackground();
        }
    }

    private void BlurBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap1 = null;
                FutureTarget<Bitmap> bitmap = Glide.with(getActivity())
                        .asBitmap()
                        .load(MainActivity.userInfo.getUser_Image_Uri())
                        .submit();
                try {
                    bitmap1 = bitmap.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final Bitmap blurbitmap = BlurUtils.blur(getContext(), bitmap1);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MybackgroundImageView.setImageBitmap(blurbitmap);
                    }
                });
            }
        }).start();
    }

    public void onStart() {
        super.onStart();
        InitViews();
        InitLayout();
        setUserName();
        setImageView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Mybackground: {

                Intent intent = new Intent(view.getContext(), AdministratorActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.Edit_information: {
                Intent intent = new Intent(getContext(), Edit_Userdata.class);
                startActivity(intent);
                break;
            }
            case R.id.administrator_entry: {
                Intent intent = new Intent(getContext(), AdministratorActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.fragment_me_about:{
                Toast.makeText(getContext(), "hahahaha", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }
    }
}
