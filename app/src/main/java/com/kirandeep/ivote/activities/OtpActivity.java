package com.kirandeep.ivote.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.kirandeep.ivote.R;
import com.kirandeep.ivote.customWidgets.OtpEditText;
import com.kirandeep.ivote.models.EntryAadharData;

import java.util.Timer;
import java.util.TimerTask;

public class OtpActivity extends AppCompatActivity {

    private TextView tvAadhar;
    private ProgressDialog mProgressDialog;
    private OtpEditText mOtpEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        String aadharNumber = getIntent().getStringExtra("AadharNumber");
        final EntryAadharData verifiedData = (EntryAadharData) getIntent().getSerializableExtra("AadharDetails");
        tvAadhar = (TextView) findViewById(R.id.tvAadhar);
        mOtpEditText = findViewById(R.id.otpEditText);
        tvAadhar.setText("Welcome "+ verifiedData.getName());

        mOtpEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start,
                                          int count,
                                          int after) {}

            @Override
            public void onTextChanged(CharSequence s,
                                      int start,
                                      int before,
                                      int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("1234")) {
                    Toast.makeText(OtpActivity.this,
                            "Success", Toast.LENGTH_SHORT).show();
                    Intent gotoElectionActivity = new Intent(OtpActivity.this, ElectionActivity.class);
                    gotoElectionActivity.putExtra("AadharDetails", verifiedData);
                    startActivity(gotoElectionActivity);
                    finish();
                } else if (s.length() == "1234".length()) {
                    Toast.makeText(OtpActivity.this,
                            "Incorrect", Toast.LENGTH_SHORT).show();
                    mOtpEditText.setText(null);
                }
                //validateOtp(s.toString());
              }
        });

    }

    private Boolean validateOtp(String otp) {
        Log.d("otp", otp);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Verifying...");
        Log.d("otp", otp);
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        mProgressDialog.dismiss();
                    }
                },
                2000
        );
        return true;
    }
}
