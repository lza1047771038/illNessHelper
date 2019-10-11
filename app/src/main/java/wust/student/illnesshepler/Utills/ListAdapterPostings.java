package wust.student.illnesshepler.Utills;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        holder.item_userid.setText(postings.getAuthor_id()+"");
        holder.item_time.setText( TimeFrmat(postings.getTime()));
        holder.item_contains.setText(postings.getContains());
        holder.likes.setText(postings.getLikes()+"");
        holder.comments.setText(postings.getComments_num()+"");
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
    public String TimeFrmat(String time) {
        long number = 0;
        long time1=Long.parseLong(time);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.d("test",  "fomat"+format1.format(time1));
        long time2=System.currentTimeMillis();
        number=time2-time1;
        if(number<=60000*5)//一分钟*5
        {
            return "刚刚";
        }
        else if(number>60000&&number<3600000){

            return (number/60000)+"分钟前";
        }
        else if(number>=3600000&&number<3600000*24){

            return (number/3600000)+"小时前";
        }else if(number>86400000&&number<86400000*10)
        {
            return (number/86400000)+"天前";
        }
        else if(number>86400000*10&&number<31536000)
        {
            SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
            return format.format(time1);
        }else {
            SimpleDateFormat format = new SimpleDateFormat("YY年MM月dd日");
            return format.format(time1);
        }
    }
}
