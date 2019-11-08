package wust.student.illnesshepler.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import wust.student.illnesshepler.Bean.Tweets;
import wust.student.illnesshepler.R;
import wust.student.illnesshepler.Utils.GsonUtils;
import wust.student.illnesshepler.Utils.Httputil;

public class TweetsListAdapter extends RecyclerView.Adapter<TweetsListAdapter.ViewHolder> {
    private List<Tweets.Item> mlist;
    private Context mcontext;


    public TweetsListAdapter(Context context, List<Tweets.Item> mlist) {
        this.mcontext = context;
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
        holder.title.setText(formattitle(mlist.get(position).title));
        holder.auther.setText(mlist.get(position).username);
        Log.d("test", "adapter num:"+mlist.get(position).visitNum);
        holder.visitNum.setText(mlist.get(position).visitNum + " ");
        Glide.with(mcontext).load(mlist.get(position).imageUrl).apply(new RequestOptions().transforms(new CenterCrop())).into(holder.imageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(position);
            }
        });
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);

    }

    private TweetsListAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(TweetsListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView auther;
        public TextView visitNum;
        public ImageView imageView;
        public LinearLayout linearLayout;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tweet_title);
            auther = itemView.findViewById(R.id.tweet_auther);
            visitNum = itemView.findViewById(R.id.tweet_visit_num);
            imageView = itemView.findViewById(R.id.tweet_image);
            linearLayout = itemView.findViewById(R.id.tweet_linearlayout);
            cardView = itemView.findViewById(R.id.tweet_image_card);
        }
    }
    public String formattitle(String title)
    {
        String temp=title;
        if (title.length()>40)
        {
            temp=temp.substring(0,35)+"···";
        }
        return temp;
    }
}
