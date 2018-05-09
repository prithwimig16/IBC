package com.prithwiraj.ibc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.prithwiraj.ibc.R;
import com.prithwiraj.ibc.models.Issues;
import com.prithwiraj.ibc.utils.CustomTitleTextView;


import java.util.ArrayList;

/**
 * Created by Prithwi on 03/04/18.
 */

public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.MyviewHolder> {
    Context mContext;
    public ArrayList<Issues> newsList;



    public IssueListAdapter(Context context, ArrayList<Issues> newsList) {
        this.mContext = context;
//        this.newsList = new ArrayList<>();
        this.newsList = newsList;
        //Collections.reverse(this.newsList);
    }




    @Override
    public IssueListAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.issue_single_item, parent, false);

        return new IssueListAdapter.MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IssueListAdapter.MyviewHolder holder, final int position) {
        Issues news = newsList.get(position);
        if (newsList.size() > 0) {
            holder.tvIssueNo.setText(news.getOffiseNo());
            holder.tvissueNAme.setText(news.getIsuue());
            holder.tvRaisedBy.setText(news.getRaisedBy());

        }
    }

    @Override
    public int getItemCount() {
        return this.newsList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        CustomTitleTextView tvIssueNo, tvissueNAme,tvRaisedBy;
        CheckBox checkBox;

        public MyviewHolder(View itemView) {
            super(itemView);
            tvIssueNo = (CustomTitleTextView) itemView.findViewById(R.id.tv_col1);
            tvissueNAme = (CustomTitleTextView) itemView.findViewById(R.id.tv_col2);
            tvRaisedBy = (CustomTitleTextView) itemView.findViewById(R.id.tv_col3);
            checkBox=(CheckBox)itemView.findViewById(R.id.chk_col4);


        }
    }
}