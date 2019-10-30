package wust.student.illnesshepler.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wust.student.illnesshepler.Bean.GetInvaestigationList;
import wust.student.illnesshepler.InvestigationList;
import wust.student.illnesshepler.R;

public class InvestigationListAdapter extends RecyclerView.Adapter<InvestigationListAdapter.ViewHolder> {
    private List<GetInvaestigationList.Item> mlist;

    public InvestigationListAdapter(List<GetInvaestigationList.Item> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investigation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.inTitle.setText(mlist.get(position).intitle);
            holder.inType.setText(mlist.get(position).intype);
            holder.inMonth.setText(mlist.get(position).intype.substring(4,6)+"月"+mlist.get(position).intype.substring(6,8)+"号");
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(position);
                    notifyItemChanged(position);
                }
            });
    }
    public interface OnItemClickListener{
        void OnItemClick(int position);

    }
    private InvestigationListAdapter.OnItemClickListener onItemClickListener=null;

    public void setOnItemClickListener(InvestigationListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public int getItemCount() {

        return mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView inYear;
        TextView inMonth;
        TextView inTitle;
        TextView inType;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View view) {
            super(view);
            inYear=view.findViewById(R.id.in_year);
            inMonth=view.findViewById(R.id.in_month);
            inTitle=view.findViewById(R.id.in_title);
            inType=view.findViewById(R.id.in_type);
            linearLayout=view.findViewById(R.id.linear);
        }
    }
}
