package com.prithwiraj.ibc.networks;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.prithwiraj.ibc.utils.Utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Prithwi on 03/04/18.
 */

public class IbcHttpCom {

    private IbcHttpCom(){}

    public static final int NEWS_SERVICE=1000;
    public static final int GYAN_SERVICE=1001;
    public static final int ISSUES_SERVICE=1002;

     int currentSeq=0;
    private Context context;
    private IbcHtttpComCallBack callback;
    private int tag;
    private String table_name;
    private JSONObject values;
    private JSONArray values1;
    private String finalUrl;

    public static final String GENERIC_ERROR_MESSAGE = "There was some problem in connecting to our server, " +
            "please try again!\nIf problem persist please " +
            "contact your Relationship Manager.";

    public IbcHttpCom(Context context) {
        this.context = context;
        this.callback = (IbcHtttpComCallBack) context;
        this.values = new JSONObject();

    }

    public IbcHttpCom(Context context, IbcHtttpComCallBack fragment) {
        this.context = context;
        this.callback = fragment;
        this.values = new JSONObject();
    }

    public static IbcHttpCom getNewInstance(Context context)
    {
        return new IbcHttpCom(context);
    }

    public static IbcHttpCom getNewInstance(Context context, IbcHtttpComCallBack fragment)
    {
        return new IbcHttpCom(context,fragment);
    }

    private void processConnection() {
        this.finalUrl="https://ibcofficemanagemant.firebaseio.com/issues.json?auth=mAbr6EucjupbqWYRuOTBwWR5WPV7yt3muawUzaJj";
        Utils.consoleLog(IbcHttpCom.class,"Post Data:"+this.values.toString());
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, this.finalUrl, this.values, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {

                Utils.consoleLog(getClass(), response.toString());
                try {
                    //boolean status=true;
                    boolean status=response.getBoolean("status");







                    if(callback!=null) {
                        callback.onSuccess(status, tag, response,currentSeq);
                        callback = null;
                    }
                } catch (JSONException e) {
                    Utils.consoleLog(getClass(),e.getLocalizedMessage());
                    if(callback!=null) {
                        callback.onFailure(null, tag);
                        callback = null;
                    }
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

                Utils.consoleLog(getClass(), error.toString());

                JSONObject rootObj = new JSONObject();
                JSONObject errorObj = new JSONObject();
                try {
                    errorObj.put("error_code", "400");
                    errorObj.put("error_msg", error.toString());
                    rootObj.put("error",errorObj);
                } catch (JSONException e) {
                    Utils.consoleLog(getClass(),e.getLocalizedMessage());
                    if(callback!=null) {
                        callback.onFailure(null, tag);
                        callback = null;
                    }
                }
                if(error.networkResponse!=null) {
                    if (callback != null) {

                        callback.onFailure(rootObj, tag);
                        callback = null;
                    }
                }
                else {
                    if (callback != null) {

                        callback.onFailure(null, tag);
                        callback = null;
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
//                if(tag!=STARTUP_SERVICE) {
//
//                    String auth = "Basic " + Base64.encodeToString(Config.getSharedInstance().B_AUTH.getBytes(), Base64.NO_WRAP);
//                    headers.put("Authorization", auth);
//                }

//                headers.put("Content-Type", "application/json");
//                headers.put("Host","appdev.clamhub.com");

                return headers;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        Utils.consoleLog(IbcHttpCom.class,jsObjRequest.toString());
        try{
            Utils.volleyRequestQueue.add(jsObjRequest);
        }catch (Exception e){
            if(callback!=null) {
                callback.onFailure(null, tag);
                callback = null;
            }
        }
    }


    public void callNewsService(String tableName){


            this.tag=NEWS_SERVICE;
            this.table_name=tableName;
            this.processConnection();
    }

    public void callGyanService(String tableName){


        this.tag=GYAN_SERVICE;
        this.table_name=tableName;
        this.processConnection();
    }

    public void callIssuesService(){
        this.tag=ISSUES_SERVICE;
        this.processConnection();
    }

}
