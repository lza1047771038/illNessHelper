package wust.student.illnesshepler.CustomViews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.ScreenUtil;

public class MyErrorDialog extends Dialog {

    private OnButtonClickListener listener;

    public interface OnButtonClickListener {
        void onPositiveButtonClicked();
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    private TextView confirm;

    public MyErrorDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ScreenUtil.dp2px(context, 300);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        setContentView(R.layout.myerrordialog);

        InitViews();
        SetLayoutInfo();
    }

    private void SetLayoutInfo() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveButtonClicked();
            }
        });
    }

    private void InitViews() {
        confirm = findViewById(R.id.MyErrorDialog_Confirm);
    }
}
