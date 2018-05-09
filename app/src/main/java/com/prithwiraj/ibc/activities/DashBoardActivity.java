package com.prithwiraj.ibc.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prithwiraj.ibc.R;
import com.prithwiraj.ibc.models.Issues;
import com.prithwiraj.ibc.utils.CustomTitleTextView;
import com.prithwiraj.ibc.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REPAIR = 600;
    private static final int FEEDBACK = 300;
    private static final int EMERGENCY = 400;
    CustomTitleTextView tvDashBoard,tvTitleText,tvSelectBellowTag;
    Button btToiletSelection,btMyOfficeSelection,btLobbySelection,buttonSubmitForHouseKeeping,buttonSubmit;
    RelativeLayout rlToilet,rlOffice,rlLobby;
    Boolean selectFirstToilet=false,selectFirstOffice=false,selectFirstLobby=false;
    int sToilet,sLobby,sOffice;
    EditText etOfficeNumber,etTapHere,etComments,etSerialNumber;
    String strScreenName;
    LinearLayout liHouseKeepingView,liRepairView;
    JSONArray itemSelectedJson = new JSONArray();
    String toilet="",lobby="",office="",username="";
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        this.username=getIntent().getStringExtra("userName");
        this.strScreenName="Repair";
initializationControl();
    }

    private void initializationControl(){
        this.tvDashBoard=(CustomTitleTextView)findViewById(R.id.tv_dashboard_details);
        this.tvDashBoard.setOnClickListener(this);

        this.tvTitleText=(CustomTitleTextView)findViewById(R.id.tv_toolbar_title_details);

        this.btToiletSelection=(Button)findViewById(R.id.bt_select1);
        this.btToiletSelection.setOnClickListener(this);

        this.btMyOfficeSelection=(Button)findViewById(R.id.bt_select2);
        this.btMyOfficeSelection.setOnClickListener(this);

        this.btLobbySelection=(Button)findViewById(R.id.bt_select3);
        this.btLobbySelection.setOnClickListener(this);

        this.rlToilet=(RelativeLayout)findViewById(R.id.relativeLayout1);
        this.rlToilet.setOnClickListener(this);

        this.rlOffice=(RelativeLayout)findViewById(R.id.relativeLayout2);
        this.rlOffice.setOnClickListener(this);

        this.rlLobby=(RelativeLayout)findViewById(R.id.relativeLayout3);
        this.rlLobby.setOnClickListener(this);

        this.etSerialNumber=(EditText)findViewById(R.id.et_serial_no);

        this.etOfficeNumber=(EditText)findViewById(R.id.et_office_no_details);

        this.buttonSubmitForHouseKeeping=(Button)findViewById(R.id.bt_submit_details);
        this.buttonSubmitForHouseKeeping.setOnClickListener(this);

        this.buttonSubmit=(Button)findViewById(R.id.bt_submit_details1);
        this.buttonSubmit.setOnClickListener(this);

        this.liHouseKeepingView=(LinearLayout)findViewById(R.id.view2);
        this.liRepairView=(LinearLayout)findViewById(R.id.view3);//gone

        this.etTapHere=(EditText)findViewById(R.id.et_tap_details);
        this.etTapHere.setOnClickListener(this);

        this.etComments=(EditText)findViewById(R.id.et_comments_details);

        this.tvSelectBellowTag=(CustomTitleTextView)findViewById(R.id.tv_select_bellow_tag);

        if(this.strScreenName.matches("Repair")){
            this.liHouseKeepingView.setVisibility(View.INVISIBLE);
            this.liRepairView.setVisibility(View.VISIBLE);
            this.tvTitleText.setText("Repair and Maintenance");
            this.buttonSubmitForHouseKeeping.setVisibility(View.INVISIBLE);

        }

        else if(this.strScreenName.matches("Feedback")){
            this.liHouseKeepingView.setVisibility(View.INVISIBLE);
            this.liRepairView.setVisibility(View.VISIBLE);
            this.tvTitleText.setText("Feedback");
            this.buttonSubmitForHouseKeeping.setVisibility(View.INVISIBLE);

        }

        else if(this.strScreenName.matches("Emergency")){
            this.liHouseKeepingView.setVisibility(View.INVISIBLE);
            this.liRepairView.setVisibility(View.VISIBLE);
            this.tvTitleText.setText("Emergency");
            this.tvSelectBellowTag.setText("Select emergency type");
            this.buttonSubmitForHouseKeeping.setVisibility(View.INVISIBLE);

        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_dashboard_details:
                finish();
                break;
            case R.id.et_tap_details:
                if (this.strScreenName.matches("Repair")) {

                    etTapHere.setError(null);
                    this.itemSelectedJson = new JSONArray();

                    this.itemSelectedJson.put("Air Conditioning");
                    this.itemSelectedJson.put("Lighting in my office");
                    this.itemSelectedJson.put("Lighting general");
                    this.itemSelectedJson.put("Technology Assistance");

                    Intent location = new Intent(DashBoardActivity.this, SelectFieldActivity.class);
                    location.putExtra("options", itemSelectedJson.toString());
                    location.putExtra("data_key", "pickRepair");
                    location.putExtra("is_json_array", true);
                    startActivityForResult(location, REPAIR);
                    overridePendingTransition(R.anim.righttoleft, R.anim.stable);

                }

            case R.id.bt_submit_details:

                buttonSubmitClick();
                break;

            case R.id.bt_submit_details1:

                buttonSubmitClick();
                break;
        }
    }


    private void buttonSubmitClick(){
        if(this.etOfficeNumber.getText().length()>0){

            if(this.etTapHere.getText().length()!=0||this.strScreenName.matches("Housekeeping")){
                Utils.getInstance().displayLoading(DashBoardActivity.this);
                sendData();
            }
            else {
                this.etTapHere.setError("Please select options");
                this.etTapHere.requestFocus();
            }

        }

        else if(this.etTapHere.getText().length()==0){
            this.etTapHere.setError("Please select options");
            this.etTapHere.requestFocus();
        }


        else {
            this.etOfficeNumber.setError("Please enter Number of office");
            this.etOfficeNumber.requestFocus();
        }


    }

    private void sendData(){
        if (this.strScreenName.matches("Repair")) {

            crateFirebaseDb(etComments.getText().toString(), etTapHere.getText().toString(), this.etOfficeNumber.getText().toString());
        }
    }

    private void callReapirService(String comments,String maintenance,String officeNO){

//            this.tag=REPAIR_MAINTENANCE;
//            this.values.put("action","repairMaintenance");
//            this.values.put("comments",comments);
//            this.values.put("office_id",Config.getSharedInstance().OFFICE_ID);
//            this.values.put("maintenance",maintenance);
//            this.values.put("office_no",officeNO);
//            this.values.put("user_id",PyoCurrentUser.getSharedInstance().getUserID());
//            this.processConnection();



    }

    private  void crateFirebaseDb(String comments,String issue,String officeNO){
        if(this.username.length()<=0||this.username==null){
            this.username="Test User";
        }

        if(etSerialNumber.getText().toString().length()<=0){
            Utils.alert("Please put Serial number",this);
        }

        else{
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("issues");
        String userId = mDatabase.push().getKey();
        Issues issues=new Issues (officeNO,this.username,issue,comments);
        mDatabase.child("data").child(etSerialNumber.getText().toString()).setValue(issues);
        Utils.getInstance().hideLoading();

        final AlertDialog alertDialog = new AlertDialog.Builder(DashBoardActivity.this).create();
        alertDialog.setTitle("IBC");
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage("We have successfully raised your request. Thank you.");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "great", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == Activity.RESULT_OK) {

            if (intent == null)
                return;

            String optionsJson = intent.getStringExtra("options");
            String dataKey = intent.getStringExtra("data_key");
            int selectedIndex = intent.getIntExtra("selected_index", -1);

            boolean isJsonArray = intent.getBooleanExtra("is_json_array", false);
            if (selectedIndex < 0)
                return;

            String value = "";
            String id = "";

            try {

                JSONArray jsonArray = new JSONArray(optionsJson);
                if (jsonArray.length() > selectedIndex) {
                    if (isJsonArray == true) {


                        JSONObject jsonObject = jsonArray.getJSONObject(selectedIndex);
                        value = jsonObject.optString(dataKey);
                        id = jsonObject.optString("id");

                    } else {

                        value = jsonArray.optString(selectedIndex);
                    }

                }

            } catch (JSONException e) {
                Utils.consoleLog(SelectFieldActivity.class, e.getLocalizedMessage());
                Utils.alert("Something went wrong while selecting option, please try again", this);
            }

            // check if the request code is same as what is passed  here it is 2
            if (requestCode == REPAIR) {

                this.etTapHere.setText(value);
                this.etTapHere.setTag(id);
            }

            else if (requestCode == FEEDBACK) {

                this.etTapHere.setText(value);
                this.etTapHere.setTag(id);
            }

            else if (requestCode == EMERGENCY) {

                this.etTapHere.setText(value);
                this.etTapHere.setTag(id);
            }

        }
    }
}
