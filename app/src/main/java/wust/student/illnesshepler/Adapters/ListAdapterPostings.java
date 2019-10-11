package wust.student.illnesshepler.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import wust.student.illnesshepler.Bean.Posting;
import wust.student.illnesshepler.R;

public class ListAdapterPostings extends RecyclerView.Adapter<ListAdapterPostings.ViewHolder>  {
    private List<Posting> mpostingslist;
    public ListAdapterPostings(List<Posting> postingslist)
    {
        mpostingslist=postingslist;
    }

    @NonNull
    @Override
    public ListAdapterPostings.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle,
                parent, false);
        return new ViewHolder(view);//将子项作为参数传给ViewHolder，在ViewHolder里面面实例化子项中的各个对象

    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterPostings.ViewHolder holder, int position) {
        Posting postings = mpostingslist.get(position);//get对应子项对象
        holder.item_userid.setText(postings.author_id+"");
        holder.item_time.setText( timeFormat(postings.time));
        holder.item_contains.setText(postings.contains);
        holder.likes.setText(postings.likes+"");
        holder.comments.setText(postings.comments_num+"");
    }

    @Override
    public int getItemCount() {
       return mpostingslist.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView item_userid;
        TextView item_time;
        TextView item_contains;
        TextView likes;
        TextView comments;

        public ViewHolder(View view){
            super(view);
          item_userid=(TextView) view.findViewById(R.id.item_userid);
          item_time=(TextView) view.findViewById(R.id.item_time);
          item_contains=(TextView) view.findViewById(R.id.item_contains);
          likes=(TextView) view.findViewById(R.id.likes);
          comments=(TextView) view.findViewById(R.id.comments);
        }
    }

    public String timeFormat(String time) {
        int nd = 24 * 60 * 60 * 1000;
        int nh = 60 * 60 * 1000;
        int nm = 60 * 1000;
        int ns = 1000;
        long day, hour, minute;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        long time1 = Long.parseLong(time);

        long result = System.currentTimeMillis() - time1;

        day = result / nd;
        hour = result / nh % 24;
        minute = result / nm % 60;

        if (day == 0 && hour == 0 && minute < 5) {          //小于5分钟时
            return "刚刚";
        } else if (day == 0 && hour == 0 && minute > 5) {  //小于1小时
            return minute + "分钟前";
        } else if (day == 0 && hour != 0) {     //小于一天
            return hour + "小时前";
        } else if (day < 10) {              //小于10天
            return day + "天前";
        } else {                //10天以上
            return format.format(time1);
        }
    }
}
