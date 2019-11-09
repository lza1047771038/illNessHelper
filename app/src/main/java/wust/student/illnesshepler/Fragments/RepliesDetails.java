package wust.student.illnesshepler.Fragments;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.StatusBarUtil;

public class RepliesDetails extends BottomSheetDialogFragment {

    View view;
    Toolbar toolbar;

    // 创建View
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_repliesdetails, container);
        InitViews();
        return view;
    }

    private void InitViews() {
        toolbar = view.findViewById(R.id.toolbar);
        getActivity().setActionBar(toolbar);
        ActionBar actionbar = getActivity().getActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }


    // 构造方法
    public static RepliesDetails newInstance(Long feedId) {
        Bundle args = new Bundle();
        args.putLong("FEED_ID", feedId);
        RepliesDetails fragment = new RepliesDetails();
        fragment.setArguments(args);
        return fragment;
    }

    public void show(@NotNull FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


}
