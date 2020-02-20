package com.infinity.infoway.bmef.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.infinity.infoway.bmef.CommonCls.CustomButton;
import com.infinity.infoway.bmef.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fill_Form_Photo extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView iv_student_image;
    /**
     * Add Image
     */
    private TextView textView11;
    private CircleImageView iv_student_signaturephoto;
    /**
     * Add your Signature
     */
    private TextView textView12;
    /**
     * Save
     */
    private CustomButton btn_save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phot_sign_layout);
        initView();
    }

    private void initView() {
        iv_student_image = (CircleImageView) findViewById(R.id.iv_student_image);
        iv_student_image.setOnClickListener(this);
        textView11 = (TextView) findViewById(R.id.textView11);
        iv_student_signaturephoto = (CircleImageView) findViewById(R.id.iv_student_signaturephoto);
        iv_student_signaturephoto.setOnClickListener(this);
        textView12 = (TextView) findViewById(R.id.textView12);
        btn_save = (CustomButton) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_student_image:
                break;
            case R.id.iv_student_signaturephoto:
                break;
            case R.id.btn_save:
                break;
        }
    }
}
