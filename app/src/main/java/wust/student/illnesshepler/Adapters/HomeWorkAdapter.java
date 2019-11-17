package wust.student.illnesshepler.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.Title.setText(data.getTitle());
        holder.Author.setText(data.getAuthor());
        holder.isFinsh.setText(isFinsh(data.getIsFinsh()));

        holder.itemView.setTag(position);
    }

    //有多少个item？
    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建MyViewHolder继承RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Title,Author,isFinsh;

        public MyViewHolder(View itemView) {
            super(itemView);

            Title=itemView.findViewById(R.id.Title);
            Author=itemView.findViewById(R.id.Author);
            isFinsh=itemView.findViewById(R.id.isFinsh);

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
                case R.id.recycler:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }

    private String isFinsh(boolean isFinsh){
        String str;
        if (isFinsh)
            str="完成";
        else
            str="未完成";
        return str;
    }
}
