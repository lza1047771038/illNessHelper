package wust.student.illnesshepler.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wust.student.illnesshepler.Bean.Data_course;
import wust.student.illnesshepler.R;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> implements View.OnClickListener {

    private List<Data_course> list;//数据源
    private Context context;//上下文

    public CourseAdapter(List<Data_course> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Data_course data = list.get(position);
        holder.Title.setText(data.getTitle());
        holder.Author.setText(data.getAuthor());
        if (data.getUri() != null)
            holder.pictuer.setImageURI(data.getUri());

        holder.itemView.setTag(position);
    }


    //有多少个item？
    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建MyViewHolder继承RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Title, Author;
        private ImageView pictuer;

        public MyViewHolder(View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.Course_Title);
            Author = itemView.findViewById(R.id.Course_teacher);
            pictuer = itemView.findViewById(R.id.Picture);

            // 为ItemView添加点击事件
            itemView.setOnClickListener(CourseAdapter.this);
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
        void onItemClick(View v, HomeWorkAdapter.ViewName viewName, int position);

        void onItemLongClick(View v);
    }

    private HomeWorkAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(HomeWorkAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                case R.id.classes:
                    mOnItemClickListener.onItemClick(v, HomeWorkAdapter.ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, HomeWorkAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }
}
