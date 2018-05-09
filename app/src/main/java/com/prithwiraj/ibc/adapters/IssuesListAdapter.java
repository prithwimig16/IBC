package com.prithwiraj.ibc.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.prithwiraj.ibc.R;
import com.prithwiraj.ibc.models.Issues;
import com.prithwiraj.ibc.utils.CustomTitleTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class IssuesListAdapter extends ArrayAdapter {

    int resource;
    Context context;
    LayoutInflater layoutInflater;
    public ArrayList<Issues> arrayList;

    public IssuesListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.arrayList=new ArrayList<Issues>();
        this.resource = resource;
        this.context =context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }





    @Override
    public int getCount() {


        return arrayList.size();


        //return this.arrayList.size();
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int o=position;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(resource, null);


           holder. tvIssueNo = (CustomTitleTextView) convertView.findViewById(R.id.tv_col1);
            holder.tvissueNAme = (CustomTitleTextView) convertView.findViewById(R.id.tv_col2);
            holder. tvRaisedBy = (CustomTitleTextView) convertView.findViewById(R.id.tv_col3);
            holder. checkBox=(CheckBox)convertView.findViewById(R.id.chk_col4);


            convertView.setTag(holder);
        }else {
            holder = (IssuesListAdapter.ViewHolder) convertView.getTag();

            System.gc();

        }


        if(this.arrayList.size()>position) {

            Issues news = this.arrayList.get(position);
            holder.tvIssueNo.setText(news.getOffiseNo());
            holder.tvissueNAme.setText(news.getIsuue());
            holder.tvRaisedBy.setText(news.getRaisedBy());

        }







        return convertView;
    }

    static class ViewHolder {

        public CustomTitleTextView tvIssueNo, tvissueNAme,tvRaisedBy;
        CheckBox checkBox;

    }


}
