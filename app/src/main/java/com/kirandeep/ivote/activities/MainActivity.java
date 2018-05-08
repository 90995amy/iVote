package com.kirandeep.ivote.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kirandeep.ivote.R;
import com.kirandeep.ivote.models.CheckIfPresentResponse;
import com.kirandeep.ivote.models.EntryAadharData;
import com.kirandeep.ivote.retrofitBase.ApiClient;
import com.kirandeep.ivote.retrofitBase.ApiInterface;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String BASE_URL = "http://192.168.43.34:3000/" ;
    private ImageView ivScan;
    private TextInputEditText etAadhar;
    private ImageView ivSubmitAadhar;
    private int strOldlen = 0;
    private String str = "";
    private String aadharNumber = "";
    private ProgressDialog progressDialog;
    private EntryAadharData verifiedAadharData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivScan = (ImageView) findViewById(R.id.ivScan);
        etAadhar = (TextInputEditText) findViewById(R.id.et_input_aadhar);
        ivSubmitAadhar = (ImageView) findViewById(R.id.ivSubmitAadhar);

        etAadhar.addTextChangedListener(this);

        progressDialog = new ProgressDialog(this);

        ivSubmitAadhar.setOnClickListener(this);
        ivScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivScan:
                Intent QrScan = new Intent(this,QrActivity.class);
                startActivityForResult(QrScan, 100);
                break;
                
            case R.id.ivSubmitAadhar:
                aadharNumber = etAadhar.getText().toString();
                if (aadharNumber.contains(" "))
                    aadharNumber = aadharNumber.replace(" ","");
                if (aadharNumber.length() < 12)
                    Toast.makeText(this,"Please enter valid 12-digit UID",Toast.LENGTH_LONG).show();

                else {
                    showProcessDialog();
                    checkIfUidPresent(aadharNumber);
                }


                break;
        }
    }

    private void showProcessDialog() {
        progressDialog.setMessage("Verifying...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void checkIfUidPresent(String aadharNumber) {
        Retrofit retrofit = ApiClient
                .with()
                .baseUrl(BASE_URL)
                .build();

        ApiInterface checkId = retrofit.create(ApiInterface.class);

        Call<CheckIfPresentResponse> call = checkId.checkIfUidPresent(aadharNumber);
        call.enqueue(new Callback<CheckIfPresentResponse>() {
            @Override
            public void onResponse(Call<CheckIfPresentResponse> call, Response<CheckIfPresentResponse> response) {

                Log.d("Response", response.body().toString());
                progressDialog.dismiss();
                verifiedAadharData = response.body().getData();
                gotoOtpActivity();
            }

            @Override
            public void onFailure(Call<CheckIfPresentResponse> call, Throwable t) {
                Log.e("Response", t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,"You are not enrolled in the voter list. Please get yourself enrolled",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void gotoOtpActivity() {
        Intent submitAadhar = new Intent(MainActivity.this,OtpActivity.class);
        submitAadhar.putExtra("AadharNumber", this.aadharNumber);
        submitAadhar.putExtra("AadharDetails", verifiedAadharData);
        startActivity(submitAadhar);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
           // String scannedAadharNumber = "";
            EntryAadharData scannedDataEntry = parseAadharXml(data.getStringExtra("AadharDetails"));
            if (scannedDataEntry != null)
                 aadharNumber = scannedDataEntry.getUid();

            checkIfUidPresent(aadharNumber);
//            Intent submitAadhar = new Intent(this,OtpActivity.class);
//            submitAadhar.putExtra("AadharNumber", aadharNumber);
//            startActivity(submitAadhar);
//            finish();
        }
    }

    private EntryAadharData parseAadharXml(String data) {
            Log.d("ScannedXml",data);

        EntryAadharData scannedDataEntry = new EntryAadharData();
            XmlPullParserFactory pullParserFactory;
            try {
                // init the parserfactory
                pullParserFactory = XmlPullParserFactory.newInstance();
                // get the parser
                XmlPullParser parser = pullParserFactory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(new StringReader(data));
                // parse the XML
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        Log.d("Rajdeol","Start document");
                } else if(eventType == XmlPullParser.START_TAG && getResources().getString(R.string.AADHAAR_DATA_TAG).equals(parser.getName())) {
                        // extract data from tag
                        scannedDataEntry.setUid(parser.getAttributeValue(null,getResources().getString(R.string.AADHAR_UID_ATTR)));
                        scannedDataEntry.setName(parser.getAttributeValue(null, getResources().getString(R.string.AADHAR_NAME_ATTR)));
                        scannedDataEntry.setGender(parser.getAttributeValue(null, getResources().getString(R.string.AADHAR_GENDER_ATTR)));
                        scannedDataEntry.setYearOfBirth(parser.getAttributeValue(null, getResources().getString(R.string.AADHAR_YOB_ATTR)));
                        scannedDataEntry.setCareOf(parser.getAttributeValue(null, getResources().getString(R.string.AADHAR_CO_ATTR)));
                        scannedDataEntry.setVillageTehsil(parser.getAttributeValue(null, getResources().getString(R.string.AADHAR_VTC_ATTR)));
                        scannedDataEntry.setPostOffice(parser.getAttributeValue(null, getResources().getString(R.string.AADHAR_PO_ATTR)));
                        scannedDataEntry.setDistrict(parser.getAttributeValue(null, getResources().getString(R.string.AADHAR_DIST_ATTR)));
                        scannedDataEntry.setState(parser.getAttributeValue(null, getResources().getString(R.string.AADHAR_STATE_ATTR)));
                        scannedDataEntry.setPostCode(parser.getAttributeValue(null, getResources().getString(R.string.AADHAR_PC_ATTR)));
                        return scannedDataEntry;
                    }
                    else if(eventType == XmlPullParser.END_TAG) {
                        Log.d("Rajdeol","End tag "+parser.getName());
                    }
                    else if(eventType == XmlPullParser.TEXT) {
                        Log.d("Rajdeol","Text "+parser.getText());
                    }
                    // update eventType
                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        str = etAadhar.getText().toString();
        int strLen = str.length();

                if(strOldlen<strLen) {
                    if (strLen > 0) {
                        if (strLen == 4 || strLen == 9) {
                            str=str+" ";
                            etAadhar.setText(str);
                            etAadhar.setSelection(etAadhar.getText().length());
                        }
                        else{
                            if(strLen==5){
                                if(!str.contains(" ")){
                                    String tempStr=str.substring(0,strLen-1);
                                    tempStr +=" "+str.substring(strLen-1,strLen);
                                    etAadhar.setText(tempStr);
                                    etAadhar.setSelection(etAadhar.getText().length());
                                }
                            }
                            if(strLen==10){
                                if(str.lastIndexOf(" ")!=9){
                                    String tempStr=str.substring(0,strLen-1);
                                    tempStr +=" "+str.substring(strLen-1,strLen);
                                    etAadhar.setText(tempStr);
                                    etAadhar.setSelection(etAadhar.getText().length());
                                }
                            }
                            strOldlen = strLen;
                        }
                    }
                    else{
                        return;
                    }

                }
                else{
                    strOldlen = strLen;

                    Log.i("MainActivity ","keyDel is Pressed ::: strLen : "+strLen+"\n old Str Len : "+strOldlen);
                }

    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
