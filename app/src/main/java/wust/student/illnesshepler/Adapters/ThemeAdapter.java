package wust.student.illnesshepler.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;
import wust.student.illnesshepler.Bean.Posting;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.Utils;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> implements View.OnClickListener {

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
        holder.time.setText(Utils.timeFormat(list.get(position).theme_id));
        holder.contains.setText(list.get(position).contains);
        holder.comments_num.setText(list.get(position).comments_num + "");
        holder.likes.setText(list.get(position).likes + "");

        holder.itemView.setTag(position);
        holder.comments_area.setTag(position);
        holder.shares_area.setTag(position);
        holder.likes_area.setTag(position);
        holder.hole_area.setTag(position);
    }

    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        switch (v.getId()){
            case R.id.comments_area:
                Log.d("test","comments_area");
                //这是头像的点击事件
                onItemClickListener.OnItemClick(v,position);
                break;
            case R.id.likes_area:
                Log.d("test","likes_area");
                //这是头像的点击事件
                onItemClickListener.OnItemClick(v,position);
                break;
            case R.id.shares_area:
                Log.d("test","shares_area");
                //这是头像的点击事件
                onItemClickListener.OnItemClick(v,position);
                break;
            case R.id.hole_area:
                Log.d("test","hole_area");
                //这是头像的点击事件
                onItemClickListener.OnItemClick(v,position);
                break;
            default:
                Log.d("test","xx");
                //默认是整个item的点击事件
                onItemClickListener.OnItemClick(v,position);
                break;
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(View view,int position);
    }

    private OnItemClickListener onItemClickListener=null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {
        TextView userid;
        TextView time;
        TextView contains;
        TextView likes;
        TextView comments_num;

        //父部件
        LinearLayout likes_area;
        LinearLayout comments_area;
        LinearLayout shares_area;
        LinearLayout hole_area;

        public ViewHolder(View view) {
            super(view);
            userid = view.findViewById(R.id.item_userid);
            time = view.findViewById(R.id.item_time);
            contains = view.findViewById(R.id.item_contains);
            likes = view.findViewById(R.id.likes);
            comments_num = view.findViewById(R.id.comments);
            likes_area=view.findViewById(R.id.likes_area);
            comments_area=view.findViewById(R.id.comments_area);
            shares_area=view.findViewById(R.id.shares_area);
            hole_area=view.findViewById(R.id.hole_area);

            likes_area.setOnClickListener(ThemeAdapter.this);
            comments_area.setOnClickListener(ThemeAdapter.this);
            shares_area.setOnClickListener(ThemeAdapter.this);
            hole_area.setOnClickListener(ThemeAdapter.this);

        }


        @Override
        public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {

        }

        @Override
        public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(-itemView.getHeight() * 0.3f)
                    .alpha(0)
                    .setDuration(300)
                    .setListener(listener)
                    .start();
        }

        @Override
        public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
            ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
            ViewCompat.setAlpha(itemView, 0);
        }

        @Override
        public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
            ViewCompat.animate(itemView)
                    .translationY(0)
                    .alpha(1)
                    .setDuration(300)
                    .setListener(listener)
                    .start();
        }
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
    }
}
