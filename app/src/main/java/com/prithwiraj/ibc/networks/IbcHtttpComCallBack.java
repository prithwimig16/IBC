package com.prithwiraj.ibc.networks;

import org.json.JSONObject;

/**
 * Created by Prithwi on 03/04/18.
 */

public interface IbcHtttpComCallBack {

    public void onSuccess(boolean status, int tag, JSONObject jsonResponse, int sequence);

    public void onFailure(JSONObject error, int tag);

}
