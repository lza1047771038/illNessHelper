package wust.student.illnesshepler.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import wust.student.illnesshepler.Bean.Tweets;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.GsonUtils;

public class TweetsListAdapter extends RecyclerView.Adapter<TweetsListAdapter.ViewHolder>{
    public List<Tweets.Item> mlist;
    public Context mcontext;
    public TweetsListAdapter(Context context,List<Tweets.Item> mlist) {
        this.mcontext=context;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public TweetsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tweets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetsListAdapter.ViewHolder holder, final int position) {
        holder.title.setText(mlist.get(position).title);
        holder.auther.setText(getUserName(mlist.get(position).auther));
        holder.visitNum.setText(mlist.get(position).visitNum+"");
        Glide.with(mcontext).load(mlist.get(position).imageUrl).apply(new RequestOptions().transforms(new CenterCrop())).into(holder.imageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(position);
            }
        });
    }
    public interface OnItemClickListener{
        void OnItemClick(int position);

    }
    private TweetsListAdapter.OnItemClickListener onItemClickListener=null;
    public void setOnItemClickListener(TweetsListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }
    public String getUserName(String id)
    {

        return "null";
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView auther;
        public TextView visitNum;
        public ImageView imageView;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tweet_title);
            auther=itemView.findViewById(R.id.tweet_auther);
            visitNum=itemView.findViewById(R.id.tweet_visit_num);
            imageView=itemView.findViewById(R.id.tweet_image);
            linearLayout=itemView.findViewById(R.id.tweet_linearlayout);
        }
    }
}
