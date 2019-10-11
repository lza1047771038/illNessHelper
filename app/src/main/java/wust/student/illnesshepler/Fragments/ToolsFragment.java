package wust.student.illnesshepler.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utills.StatusBarUtil;

public class ToolsFragment extends Fragment {
    View view, statusBarBackground;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_tools,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        statusBarBackground = view.findViewById(R.id.statusBarBackground);
        ViewGroup.LayoutParams params = statusBarBackground.getLayoutParams();
        params.height = StatusBarUtil.getStatusBarHeight(getContext());
        statusBarBackground.setLayoutParams(params);
    }
}
