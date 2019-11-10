package wust.student.illnesshepler.Fragments;


import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Bean.GetTweetComments;
import wust.student.illnesshepler.MainActivity;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.ShowTweet;
import wust.student.illnesshepler.SurveyQuestions.Test;
import wust.student.illnesshepler.Utils.Httputil;

public class WriteComment extends BottomSheetDialogFragment implements View.OnClickListener {
    private View view;
    private TextView send;
    private EditText comments;
    private String themeid;
    private Handler handler;
    private BottomSheetBehavior bottomSheetBehavior;
    public WriteComment writeComment;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(WriteComment.STYLE_NORMAL, R.style.BottomSheetEdit);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("test","WriteComment onCreateDialog");
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.write_comment, null);
        dialog.setContentView(view);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.write_comment, container, false);

        InitViews();
        focous();
        Log.d("test","WriteComment onCreateView");
        return view;
    }
    public void focous()
    {
        comments.requestFocus();


        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.showSoftInput(comments, InputMethodManager.SHOW_IMPLICIT);
//        Window dialogWindow = dialog.getWindow();
////设置软键盘弹出模式
//        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

//
//        InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm2.hideSoftInputFromWindow(tweetPostCommentHigth.getWindowToken(), 0);
    }
    private void InitViews() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case 2:
                        Toast.makeText(getContext(), "发送成功", Toast.LENGTH_SHORT).show();
                        GetTweetComments.Comments temp=new GetTweetComments.Comments();

                        temp.contains=msg.obj.toString();
                        temp.username="tempusername(用户登录时放到static 变量)";
                        temp.time=System.currentTimeMillis()+"";
                        temp.likes=0;
                        temp.userimage="temp";
                        temp.replies=0;
                        temp.comments_num=0;
                        ShowTweet.clist.add(0, temp);
                        ShowTweet.adapter.notifyDataSetChanged();
                        ShowTweet.recyclerView.getLayoutManager().scrollToPosition(0);
                        comments.setText("");
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;
                    case 3:
                        Toast.makeText(getContext(), "发送失败", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        send=view.findViewById(R.id.send_comments);
        comments=view.findViewById(R.id.comments_comnains);

        comments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0)
                {
                    send.setTextColor(getResources().getColor(R.color.colorAccent));
                }
                else
                {
                    send.setTextColor(getResources().getColor(R.color.thingray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        send.setOnClickListener(this);
        ActionBar actionbar = getActivity().getActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onStart() {
        Log.d("test","WriteComment onStart");
        super.onStart();
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_SETTLING);
        ((View) getView().getParent()).setBackground(getResources().getDrawable(R.drawable.top_white_corner));
        if (getDialog() != null && getDialog().getWindow() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Window window = getDialog().getWindow();
            window.findViewById(com.google.android.material.R.id.container).setFitsSystemWindows(false);
            window.findViewById(com.google.android.material.R.id.coordinator).setFitsSystemWindows(false);
            // dark navigation bar icons
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
    }


    // 构造方法
    public static WriteComment newInstance(String themeid) {
        Log.d("test","WriteComment newInstance");
        Bundle args = new Bundle();
        args.putString("themeid", themeid);
        WriteComment fragment = new WriteComment();
        fragment.setArguments(args);
        return fragment;
    }

    public void show(@NotNull FragmentManager manager, String tag) {
        Log.d("test","WriteComment newInstance");
        super.show(manager, tag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send_comments :
                sendcomment();

                break;
        }
    }
    public void sendcomment()
    {
        Bundle args = getArguments();
        themeid = args.getString("themeid", "null");
       final String contains = comments.getText().toString();
        if (contains.length() == 0) {
        }else{
            if(true) //敏感词汇
            {
                Httputil.comment_post(themeid, MainActivity.authorid, contains, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Message message = new Message();
                            message.what = 3;
                            message.obj="null";
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String reslut = response.body().string();
                        Message message = new Message();
                        if (reslut.equals("1")) {
                            message.what = 2;
                            message.obj=contains;
                        } else {
                            message.what = 3;
                            message.obj="null";
                        }
                        handler.sendMessage(message);
                    }
                });
            }
            else
            {
                Toast.makeText(getContext(), "包含铭感词汇，请检查评论内容", Toast.LENGTH_SHORT).show();
            }
            }
        }

}
