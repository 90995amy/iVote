package com.kirandeep.ivote.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kirandeep.ivote.R;

import java.util.List;

/**
 * Created by abc on 14-04-2018.
 */

public class ElectionListAdapter extends RecyclerView.Adapter<ElectionListAdapter.ViewHolder> {

    private List<String> mDataSet;

    public static OnItemClickListener electionDetailsListener;

    public interface OnItemClickListener {
        void onNameClick(View view, int position);
        void onArrowClick(View view);
        void onVoteClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        electionDetailsListener = listener;
    }

    public ElectionListAdapter(List<String> dataSet) {
        mDataSet = dataSet;
    }


    @Override
    public ElectionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_election_list, parent, false);
        return new ElectionListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ElectionListAdapter.ViewHolder holder, final int position) {

        holder.tvElectionName.setText(mDataSet.get(position));

        holder.layoutElectionName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                electionDetailsListener.onNameClick(view,position);
            }
        });

        holder.ivCollapseElectionDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                electionDetailsListener.onArrowClick(view);
            }
        });
        holder.tvVoteNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                electionDetailsListener.onVoteClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvElectionName;
        private RelativeLayout layoutElectionName;
        private ImageView ivCollapseElectionDetails;
        private TextView tvVoteNow;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvElectionName = (TextView) itemView.findViewById(R.id.tv_election_name);
            layoutElectionName = itemView.findViewById(R.id.layout_election_name);
            ivCollapseElectionDetails = itemView.findViewById(R.id.btn_collapse_election_cv);
            tvVoteNow = itemView.findViewById(R.id.btn_election);
        }


    }
}
