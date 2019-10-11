package wust.student.illnesshepler.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

import wust.student.illnesshepler.Bean.Posting;
import wust.student.illnesshepler.R;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {

    private List<Posting> list;

    public ThemeAdapter(List<Posting> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userid.setText(list.get(position).author_id + "");
        holder.time.setText(timeFormat(list.get(position).theme_id));
        holder.contains.setText(list.get(position).contains);
        holder.comments_num.setText(list.get(position).comments_num + "");
        holder.likes.setText(list.get(position).likes + "");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView userid;
        TextView time;
        TextView contains;
        TextView likes;
        TextView comments_num;

        public ViewHolder(View view) {
            super(view);
            userid = view.findViewById(R.id.item_userid);
            time = view.findViewById(R.id.item_time);
            contains = view.findViewById(R.id.item_contains);
            likes = view.findViewById(R.id.likes);
            comments_num = view.findViewById(R.id.comments);
        }
    }

    public String timeFormat(String temp) {
        int nd = 24 * 60 * 60 * 1000;
        int nh = 60 * 60 * 1000;
        int nm = 60 * 1000;
        int ns = 1000;
        long day, hour, minute;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        long oldtime = Long.parseLong(temp);

        long result = System.currentTimeMillis() - oldtime;

        day = result / nd;
        hour = result / nh % 24;
        minute = result / nm % 60;

        Log.d("tag123time", temp + "\t" + result + "\t" + day + "天" + hour + "时" + minute);

        if (day == 0 && hour == 0 && minute < 5) {          //小于5分钟时
            return "刚刚";
        } else if (day == 0 && hour == 0 && minute > 5) {  //小于1小时
            return minute + "分钟前";
        } else if (day == 0 && hour != 0) {     //小于一天
            return hour + "小时前";
        } else if (day < 10) {              //小于10天
            return day + "天前";
        } else {                //10天以上
            return format.format(oldtime);
        }
    }
}
