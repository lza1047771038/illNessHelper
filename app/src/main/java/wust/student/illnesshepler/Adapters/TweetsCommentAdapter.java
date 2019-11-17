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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import wust.student.illnesshepler.Bean.GetTweetComments;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.Utils;

public class TweetsCommentAdapter extends RecyclerView.Adapter<TweetsCommentAdapter.ViewHolder> implements View.OnClickListener {
    private List<GetTweetComments.Comments> mlist;
    private Context mcontext;

    public TweetsCommentAdapter(List<GetTweetComments.Comments> mlist, Context mcontext) {
        this.mlist = mlist;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public TweetsCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments_section, parent, false);
        return new TweetsCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.tweet_comment_auther.setText(mlist.get(position).username);
       holder.tweet_comment_time.setText(Utils.timeFormat(mlist.get(position).time));
        holder.tweet_comment_contains.setText(mlist.get(position).contains);
       holder.tweet_comment_likes_num.setText(mlist.get(position).likes+"");
       if(mlist.get(position).replies==0){
           holder.tweet_comment_num.setVisibility(View.GONE);
       }
       else {
           holder.tweet_comment_num.setText(mlist.get(position).replies + " 条回复>");
       }
        Glide.with(mcontext).load(mlist.get(position).userimage).apply(new RequestOptions().transforms(new CircleCrop())).error(R.drawable.postcard).into(holder.tweet_comment_img);
//        Glide.with(mcontext).load(mlist.get(position).userimage).apply(new RequestOptions().transform(new CircleCrop())).into(holder.tweet_comment_img);
        holder.comments_image_area.setTag(position);
        holder.linearLayout.setTag(position);
        holder.linearlikes.setTag(position);
        holder.tweet_comment_num.setTag(position);
    }

    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        switch (v.getId())
        {
            case R.id.comments_linear:
                onItemClickListener.OnItemClick(v,position);
                break;
            case R.id.tweet_comment_likes_area:
                onItemClickListener.OnItemClick(v,position);
                break;
            case R.id.tweet_comment_num:
                onItemClickListener.OnItemClick(v,position);
                break;
            case R.id.comments_image_area:
                onItemClickListener.OnItemClick(v,position);
                break;
        }
    }

    public static interface OnItemClickListener{
        void OnItemClick(View view,int position);
    }
    TweetsCommentAdapter.OnItemClickListener onItemClickListener=null;

    public void setOnItemClickListener(TweetsCommentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public LinearLayout linearlikes;
        public LinearLayout comments_image_area;
        public TextView tweet_comment_auther;
        public TextView tweet_comment_time;
        public TextView tweet_comment_likes_num;
        public TextView tweet_comment_num;
        public TextView tweet_comment_contains;
        public ImageView tweet_comment_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.comments_linear);
            linearlikes = itemView.findViewById(R.id.tweet_comment_likes_area);
            comments_image_area = itemView.findViewById(R.id.comments_image_area);
           tweet_comment_auther=itemView.findViewById(R.id.tweet_comment_auther);
           tweet_comment_time=itemView.findViewById(R.id.tweet_comment_time);
           tweet_comment_likes_num=itemView.findViewById(R.id.tweet_comment_likes_num);
           tweet_comment_num=itemView.findViewById(R.id.tweet_comment_num);
            tweet_comment_contains=itemView.findViewById(R.id.tweet_comment_contains);
           tweet_comment_img=itemView.findViewById(R.id.tweet_comment_img);

            linearLayout.setOnClickListener(TweetsCommentAdapter.this);
            linearlikes.setOnClickListener(TweetsCommentAdapter.this);
            tweet_comment_num .setOnClickListener(TweetsCommentAdapter.this);
            comments_image_area.setOnClickListener(TweetsCommentAdapter.this);

        }
    }
}
