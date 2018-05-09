package com.prithwiraj.ibc.models;

import org.json.JSONObject;

public class Issues {

    public Issues(JSONObject object) {
        this.offiseNo=object.optString("offiseNo");
        this.raisedBy=object.optString("raisedBy");
        this.isuue=object.optString("isuue");
        this.comments=object.optString("comments");
        }

    public Issues(String officeNO,String raisedBy, String issue, String comments) {

        this.offiseNo=officeNO;
        this.raisedBy=raisedBy;
        this.isuue=issue;
        this.comments=comments;
    }


    public String getOffiseNo() {
        return offiseNo;
    }
    public String getRaisedBy() {
        return raisedBy;
    }


    public String getIsuue() {
        return isuue;
    }

    public String getComments() {
        return comments;
    }

    private String offiseNo;
    private String raisedBy;
    private String isuue;
    private String comments;

}
