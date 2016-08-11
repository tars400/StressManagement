package com.project.stress.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.stress.R;
import com.project.stress.models.History;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private List<History> histories;
    private Context context;

    public HistoryListAdapter(Context context, List<History> histories) {
        super();
        this.context = context;
        this.histories = histories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_history, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        final History history = histories.get(i);

        holder.tvName.setText(history.getName());
        holder.tvDate.setText(history.getDate());
        holder.tvCluster.setText("Cluster : " + String.valueOf(history.getCluster()));
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView tvName, tvDate, tvCluster;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvCluster = (TextView) itemView.findViewById(R.id.tvCluster);
        }
    }
}
