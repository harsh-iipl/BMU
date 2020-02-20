package com.infinity.infoway.bmef.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.infinity.infoway.bmef.CommonCls.CustomButton;
import com.infinity.infoway.bmef.CommonCls.CustomEditText;
import com.infinity.infoway.bmef.CommonCls.CustomTextView;
import com.infinity.infoway.bmef.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class Fill_Form_Activity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView2;
    private LinearLayout llpersonal;
    /**
     * Personal Details
     */
    private CustomTextView textView3;
    private CustomEditText edtname;
    private CustomEditText edtmiddlename;
    private CustomEditText edtfathersName;
    private CustomEditText edtfathersoccupation;
    private CustomEditText edtmothername;
    private CustomEditText edtmotheroccupation;
    private CustomEditText edtbirthdate;
    private CustomEditText edtbirthplace;
    /**
     * Male
     */
    private RadioButton rb_male;
    /**
     * Female
     */
    private RadioButton rb_female;
    private RadioGroup rg;
    private SearchableSpinner spin_bloodgroup;
    private SearchableSpinner spin_category;
    private CustomEditText edtadharno;
    private SearchableSpinner edtnationality;
    /**
     * Religion
     */
    private CustomTextView textView7;
    private SearchableSpinner spin_religionpersonal;
    /**
     * Next
     */
    private CustomButton btnnext;
    private LinearLayout llstudentpersonaldetail;
    /**
     * Name
     */
    private CustomTextView textView4;
    /**
     * ---
     */
    private CustomTextView txtnameemp;
    /**
     * ---
     */
    private CustomTextView txtgenderemp;
    /**
     * ---
     */
    private CustomTextView txtcategoryemp;
    /**
     * ---
     */
    private CustomTextView txtcasteemp;
    /**
     * ---
     */
    private CustomTextView txtbirthdateemp;
    /**
     * ---
     */
    private CustomTextView txtadharnoemp;
    /**
     * ---
     */
    private CustomTextView txtpannoemp;
    /**
     * ---
     */
    private CustomTextView txtpassportnoemp;
    /**
     * ---
     */
    private CustomTextView txtdrivingnoemp;
    /**
     * ---
     */
    private CustomTextView txtbloodgroupemp;
    /**
     * ---
     */
    private CustomTextView txtemailemp;
    /**
     * ---
     */
    private CustomTextView txtmobilenoemp;
    private LinearLayout llemppersonaldetail;
    private LinearLayout layout4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.personal_details_form);
        initView();
    }

    private void initView() {
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        llpersonal = (LinearLayout) findViewById(R.id.llpersonal);
        textView3 = (CustomTextView) findViewById(R.id.textView3);
        edtname = (CustomEditText) findViewById(R.id.edtname);
        edtmiddlename = (CustomEditText) findViewById(R.id.edtmiddlename);
        edtfathersName = (CustomEditText) findViewById(R.id.edtfathersName);
        edtfathersoccupation = (CustomEditText) findViewById(R.id.edtfathersoccupation);
        edtmothername = (CustomEditText) findViewById(R.id.edtmothername);
        edtmotheroccupation = (CustomEditText) findViewById(R.id.edtmotheroccupation);
        edtbirthdate = (CustomEditText) findViewById(R.id.edtbirthdate);
        edtbirthplace = (CustomEditText) findViewById(R.id.edtbirthplace);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
        rg = (RadioGroup) findViewById(R.id.rg);
        spin_bloodgroup = (SearchableSpinner) findViewById(R.id.spin_bloodgroup);
        spin_category = (SearchableSpinner) findViewById(R.id.spin_category);
        edtadharno = (CustomEditText) findViewById(R.id.edtadharno);
        edtnationality = (SearchableSpinner) findViewById(R.id.edtnationality);
        textView7 = (CustomTextView) findViewById(R.id.textView7);
        spin_religionpersonal = (SearchableSpinner) findViewById(R.id.spin_religionpersonal);
        btnnext = (CustomButton) findViewById(R.id.btnnext);
        btnnext.setOnClickListener(this);
        llstudentpersonaldetail = (LinearLayout) findViewById(R.id.llstudentpersonaldetail);
        textView4 = (CustomTextView) findViewById(R.id.textView4);
        txtnameemp = (CustomTextView) findViewById(R.id.txtnameemp);
        txtgenderemp = (CustomTextView) findViewById(R.id.txtgenderemp);
        txtcategoryemp = (CustomTextView) findViewById(R.id.txtcategoryemp);
        txtcasteemp = (CustomTextView) findViewById(R.id.txtcasteemp);
        txtbirthdateemp = (CustomTextView) findViewById(R.id.txtbirthdateemp);
        txtadharnoemp = (CustomTextView) findViewById(R.id.txtadharnoemp);
        txtpannoemp = (CustomTextView) findViewById(R.id.txtpannoemp);
        txtpassportnoemp = (CustomTextView) findViewById(R.id.txtpassportnoemp);
        txtdrivingnoemp = (CustomTextView) findViewById(R.id.txtdrivingnoemp);
        txtbloodgroupemp = (CustomTextView) findViewById(R.id.txtbloodgroupemp);
        txtemailemp = (CustomTextView) findViewById(R.id.txtemailemp);
        txtmobilenoemp = (CustomTextView) findViewById(R.id.txtmobilenoemp);
        llemppersonaldetail = (LinearLayout) findViewById(R.id.llemppersonaldetail);
        layout4 = (LinearLayout) findViewById(R.id.layout4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnnext:
                Intent i = new Intent(Fill_Form_Activity.this, Fill_Form_Contact_detail.class);


                startActivity(i);                break;
        }
    }
}
