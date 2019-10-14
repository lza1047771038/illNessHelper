package wust.student.illnesshepler.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.time.setText(Utils.timeFormat(list.get(position).theme_id));
        holder.contains.setText(list.get(position).contains);
        holder.comments_num.setText(list.get(position).comments_num + "");
        holder.likes.setText(list.get(position).likes + "");

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

        public ViewHolder(View view) {
            super(view);
            userid = view.findViewById(R.id.item_userid);
            time = view.findViewById(R.id.item_time);
            contains = view.findViewById(R.id.item_contains);
            likes = view.findViewById(R.id.likes);
            comments_num = view.findViewById(R.id.comments);
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
}
