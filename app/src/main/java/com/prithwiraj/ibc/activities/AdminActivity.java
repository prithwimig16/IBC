package com.prithwiraj.ibc.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.android.volley.toolbox.Volley;
import com.prithwiraj.ibc.R;
import com.prithwiraj.ibc.adapters.IssueListAdapter;
import com.prithwiraj.ibc.adapters.IssuesListAdapter;
import com.prithwiraj.ibc.models.Issues;
import com.prithwiraj.ibc.networks.IbcHttpCom;
import com.prithwiraj.ibc.networks.IbcHtttpComCallBack;
import com.prithwiraj.ibc.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class AdminActivity extends AppCompatActivity implements IbcHtttpComCallBack {
    private  String userName="";
    ArrayList<Issues> newsList;
    IssuesListAdapter adapter;
    LinearLayoutManager manager;
    JSONObject jsonObject;
    ListView listView;
    Issues issues;
    Thread r;
    boolean activityClosed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Utils.volleyRequestQueue = Volley.newRequestQueue(this);
        this.userName=getIntent().getStringExtra("userName");
        init();
    }

    private void init(){

        this.adapter=new IssuesListAdapter(this,R.layout.issue_single_item);
        this.listView=(ListView)findViewById(R.id.list_view);
        this.listView.setAdapter(this.adapter);

    }

    private void networkCall(){
       // Utils.getInstance().displayLoading(this);
        IbcHttpCom.getNewInstance(this).callIssuesService();

    }

    @Override
    public void onResume() {
        super.onResume();
        activityClosed=false;
        final Handler handler = new Handler();
        r = new Thread() {
            public void run() {
                if (activityClosed == false) {
                    // DO WORK
                    if(adapter!=null||adapter.arrayList.size()>0){
                        adapter.arrayList.clear();
                    }
                    networkCall();
                    // Call function.
                    handler.postDelayed(this, 4000);
                }

            }
        };
        r.start();


    }
    public void onStop() {
        super.onStop();
        this.activityClosed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.activityClosed=true;
    }

    @Override
    public void onSuccess(boolean status, int tag, JSONObject jsonResponse, int sequence) {
        Utils.getInstance().hideLoading();
        if (tag == IbcHttpCom.ISSUES_SERVICE) {
            if (jsonResponse != null) {
                if (status) {
                    JSONArray data = jsonResponse.optJSONArray("data");
                    if (data != null) {

                        for (int i = 0; i < data.length(); i++) {

                            this.jsonObject = data.optJSONObject(i);

                            if(jsonObject!=null){
                            this.issues = new Issues(jsonObject);
                            //this.newsList.add(issues);
                            this.adapter.arrayList.add(issues);
                            }

                            }

//                        Collections.reverse(this.newsList);
                        this.adapter.notifyDataSetChanged();

                    }
                }
            }
        }
    }

    @Override
    public void onFailure(JSONObject error, int tag) {
        Utils.getInstance().hideLoading();
    }
}
