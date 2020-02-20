package com.infinity.infoway.bmef.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.infinity.infoway.bmef.CommonCls.CustomButton;
import com.infinity.infoway.bmef.CommonCls.CustomEditText;
import com.infinity.infoway.bmef.CommonCls.CustomTextView;
import com.infinity.infoway.bmef.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class Fill_Form_Contact_detail extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout imageView4;
    /**
     * Contact Details
     */
    private CustomTextView textView5;
    private RelativeLayout relativeLayout;
    private CustomEditText txtmobilenouser;
    /**
     * Father Mobile No.
     */
    private CustomTextView txtfthermobilenoheader;
    private CustomEditText txtfathermobileno;
    private CustomEditText txtpresentaddress;
    private CustomEditText txtemail;
    private SearchableSpinner spin_state;
    private SearchableSpinner spin_district;
    private SearchableSpinner spin_city;
    private CustomEditText txtlandmark;
    private CustomEditText txtpincode;
    private SearchableSpinner spin_countrycontact;
    /**
     * Next
     */
    private CustomButton btnnext;
    private LinearLayout linearLayout2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details_form);
        initView();
    }

    private void initView() {
        imageView4 = (LinearLayout) findViewById(R.id.imageView4);
        textView5 = (CustomTextView) findViewById(R.id.textView5);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        txtmobilenouser = (CustomEditText) findViewById(R.id.txtmobilenouser);
        txtfthermobilenoheader = (CustomTextView) findViewById(R.id.txtfthermobilenoheader);
        txtfathermobileno = (CustomEditText) findViewById(R.id.txtfathermobileno);
        txtpresentaddress = (CustomEditText) findViewById(R.id.txtpresentaddress);
        txtemail = (CustomEditText) findViewById(R.id.txtemail);
        spin_state = (SearchableSpinner) findViewById(R.id.spin_state);
        spin_district = (SearchableSpinner) findViewById(R.id.spin_district);
        spin_city = (SearchableSpinner) findViewById(R.id.spin_city);
        txtlandmark = (CustomEditText) findViewById(R.id.txtlandmark);
        txtpincode = (CustomEditText) findViewById(R.id.txtpincode);
        spin_countrycontact = (SearchableSpinner) findViewById(R.id.spin_countrycontact);
        btnnext = (CustomButton) findViewById(R.id.btnnext);
        btnnext.setOnClickListener(this);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnnext:
                break;
        }
    }
}
