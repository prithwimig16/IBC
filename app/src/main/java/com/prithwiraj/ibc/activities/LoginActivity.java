package com.prithwiraj.ibc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prithwiraj.ibc.R;
import com.prithwiraj.ibc.utils.CustomTitleTextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    CustomTitleTextView tvSignUpText,tvBack;
    Button buttonGenarteOtp;
    EditText etMobileOrEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        this.tvSignUpText = (CustomTitleTextView) findViewById(R.id.tv_signuptext_login);
        this.etMobileOrEmail = (EditText) findViewById(R.id.et_mobile_no_login);
        this.buttonGenarteOtp = (Button) findViewById(R.id.bt_ganarate_otp_login);
        this.buttonGenarteOtp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(etMobileOrEmail.getText().toString().equalsIgnoreCase("ADMIN")){
            Intent i = new Intent(LoginActivity.this, AdminActivity.class);
            i.putExtra("userName",etMobileOrEmail.getText().toString());
            startActivity(i);
        }
        else{

        Intent i = new Intent(LoginActivity.this, DashBoardActivity.class);
        i.putExtra("userName",etMobileOrEmail.getText().toString());
        startActivity(i);
        }

    }
}
