package wust.student.illnesshepler.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import wust.student.illnesshepler.R;

public class Dialog_prompt extends Dialog {
    private Context context;
    private int layoutResID;//布局文件id
    private int[] listenedItem;//监听的控件id
    private TextView prompt_text;
    private Button prompt_begin;
    String text = "具体内容";

    public Dialog_prompt(@NonNull Context context, int themeResId, int[] listenedItem) {
        super(context);
        this.context = context;
        this.layoutResID = themeResId;
        this.listenedItem = listenedItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提前设置Dialog的一些样式
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);//设置dialog显示居中
        dialogWindow.setWindowAnimations(0);//设置动画效果
        setContentView(layoutResID);

        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 4 / 5;// 设置dialog宽度为屏幕的4/5
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);//点击外部Dialog不消失
        //遍历控件id添加点击注册
        for(int id:listenedItem){
            if(id==R.id.prompt_begin)
                prompt_begin=findViewById(id);
            else
                prompt_text=findViewById(id);
        }

        prompt_text.setText(text);
        prompt_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
