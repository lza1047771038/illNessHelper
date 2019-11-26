package wust.student.illnesshepler.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import wust.student.illnesshepler.Bean.Data_HomeWork;
import wust.student.illnesshepler.R;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.MyViewHolder> implements View.OnClickListener {

    private List<Data_HomeWork> list;//数据源
    private Context context;//上下文

    public HomeWorkAdapter(List<Data_HomeWork> list, Context context) {
        this.list= list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homework, parent, false);
        return new MyViewHolder(view);
    }


    //绑定
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data_HomeWork data = list.get(position);
        String[]Options=data.getOptions();
        holder.Theme.setText(data.getTheme());
        holder.radioButtonA.setText(Options[0]);
        holder.radioButtonB.setText(Options[1]);
        holder.radioButtonC.setText(Options[2]);
        holder.radioButtonD.setText(Options[3]);

        holder.itemView.setTag(position);
    }

    //有多少个item？
    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建MyViewHolder继承RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Theme;
        private RadioGroup radioGroup;
        private RadioButton radioButtonA;
        private RadioButton radioButtonB;
        private RadioButton radioButtonC;
        private RadioButton radioButtonD;

        public MyViewHolder(View itemView) {
            super(itemView);

            Theme=itemView.findViewById(R.id.Theme);
            radioGroup=itemView.findViewById(R.id.RgOfAnswer);
            radioButtonA=itemView.findViewById(R.id.A);
            radioButtonB=itemView.findViewById(R.id.B);
            radioButtonC=itemView.findViewById(R.id.C);
            radioButtonD=itemView.findViewById(R.id.D);

            // 为ItemView添加点击事件
            itemView.setOnClickListener(HomeWorkAdapter.this);
        }

    }

    //=======================以下为item中的view控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v, ViewName viewName, int position);

        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                case R.id.subject:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }
}
