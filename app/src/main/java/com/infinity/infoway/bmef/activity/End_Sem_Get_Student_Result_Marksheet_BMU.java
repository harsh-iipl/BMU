package com.infinity.infoway.bmef.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.Strings;
import com.google.gson.Gson;
import com.infinity.infoway.bmef.CommonCls.CustomTextView;
import com.infinity.infoway.bmef.CommonCls.DialogUtils;
import com.infinity.infoway.bmef.CommonCls.URl;
import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.adapter.All_Exam_List_stud_ID_Adapter;
import com.infinity.infoway.bmef.adapter.Get_Student_Result_Marksheet_BMU_Adapter;
import com.infinity.infoway.bmef.app.DataStorage;
import com.infinity.infoway.bmef.model.All_Exam_List_stud_Id;
import com.infinity.infoway.bmef.model.Get_Student_Result_Marksheet_BMU_POJO;

public class End_Sem_Get_Student_Result_Marksheet_BMU extends AppCompatActivity {
    Toolbar toolbar;
    Context ctx;
    DataStorage storage;
    RequestQueue queue;
    /**
     * No Records Found
     */
    private CustomTextView txt_records;
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_sem_result_u);
        Intent intent = getIntent();

//        i.putExtra("YEAR_ID",  all_exam_list_stud.getData().get(position).getSwd_year_id()+"");
//        i.putExtra("COLLEGE_ID",  all_exam_list_stud.getData().get(position).getSwd_college_id()+"");
//        i.putExtra("SEM_ID",  all_exam_list_stud.getData().get(position).getSwd_sem_id()+"");
//        i.putExtra("TERM_ID",  all_exam_list_stud.getData().get(position).getSwd_term_id()+"");
//        i.putExtra("STUDENT_ID",  all_exam_list_stud.getData().get(position).getStudent_Id()+"");
        YEAR_ID = intent.getStringExtra("YEAR_ID") + "";
        COLLEGE_ID = intent.getStringExtra("COLLEGE_ID") + "";
        SEM_ID = intent.getStringExtra("SEM_ID") + "";
        TERM_ID = intent.getStringExtra("TERM_ID") + "";
        STUDENT_ID = intent.getStringExtra("STUDENT_ID") + "";
        System.out.println("YEAR_ID:::::::::" + YEAR_ID + "");
        System.out.println("COLLEGE_ID:::::::::" + COLLEGE_ID + "");
        initView();
        _Act = End_Sem_Get_Student_Result_Marksheet_BMU.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);
                // toolbar.setBackgroundColor(Color.GREEN);
            }
        });
        storage = new DataStorage("Login_Detail", this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        // ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);


        if (!storage.isOnline(End_Sem_Get_Student_Result_Marksheet_BMU.this)) {
            showDialog(DataStorage.DIALOG_ERROR_CONNECTION);
        }

        if (storage.CheckLogin("stud_id", this)) {

        }
        txt_records = (CustomTextView) findViewById(R.id.txt_records);
        this.lv = (ListView) findViewById(R.id.lv_assignment_fact);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  this.title = (CustomTextView) findViewById(R.id.title);
        queue = Volley.newRequestQueue(End_Sem_Get_Student_Result_Marksheet_BMU.this);
        // Get_Student_Result_Marksheet_BMU("v1d1dRrPaNcg", "1", "1", "1", "1", "7");
        Get_Student_Result_Marksheet_BMU(URl.API_STUDENT_RESULT_COMMAN_KEY, YEAR_ID + "", COLLEGE_ID + "", SEM_ID + "", TERM_ID + "", STUDENT_ID + "");

    }

    Activity _Act;


    void Get_Student_Result_Marksheet_BMU(final  String key,final  String YEAR_ID,final String COL_ID,final  String SEM_ID, final String TERM_ID,final String student_id) {
        DialogUtils.showProgressDialog(End_Sem_Get_Student_Result_Marksheet_BMU.this, "");
        //http://bmef.icrp.in/exam/API_Student_Result_Comman.asmx/get_all_exam_list_student_id?Key=v1d1dRrPaNcg&student_id=7
        String Url ="";// URl.BMU_STUDENT_URL + "get_all_exam_list_student_id" + "?Key=" + key + "&student_id=" + student_id + "";
//        Url = "http://bmef.icrp.in/exam/API_Student_Result_Comman.asmx/Get_Student_Result_Marksheet_BMU?Key=v1d1dRrPaNcg&student_id=7";
        //  Url = "http://bmef.icrp.in/exam/API_Student_Result_Comman.asmx/Get_Student_Result_Marksheet_BMU?Key=v1d1dRrPaNcg&year_id=" + YEAR_ID + "&college_id=" + COL_ID + "&sem_id=" + SEM_ID + "&term_id=" + TERM_ID + "&student_id=" + student_id + "";
       // Url = "http://bmef.icrp.in/exam/API_Student_Result_Comman.asmx/Get_Student_Result_Marksheet_BMU?Key=" + key + "&year_id=" + YEAR_ID + "&college_id=" + COL_ID + "&sem_id=" + SEM_ID + "&term_id=" + TERM_ID + "&student_id=" + student_id + "";
        Url =URl.API_STUDENT_RESULT_COMMAN+ "Get_Student_Result_Marksheet_BMU?Key=" + key + "&year_id=" + YEAR_ID + "&college_id=" + COL_ID + "&sem_id=" + SEM_ID + "&term_id=" + TERM_ID + "&student_id=" + student_id + "";

        Url = Url.replace(" ", "%20");
        System.out.println("Get_Student_Result_Marksheet_BMU   " + Url + "");
        StringRequest req = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response!!!!!! " + response + "");
                DialogUtils.hideProgressDialog();
                response = "{\"data\":" + response + "}";
                if (response.length() > 10) {
                    Gson gson = new Gson();

                    Get_Student_Result_Marksheet_BMU_POJO assignView = gson.fromJson(response, Get_Student_Result_Marksheet_BMU_POJO.class);

                    if (assignView != null && assignView.getData().size() > 0) {
                        txt_records.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);

                        Get_Student_Result_Marksheet_BMU_Adapter assignmentViewAdapter = new Get_Student_Result_Marksheet_BMU_Adapter(End_Sem_Get_Student_Result_Marksheet_BMU.this, End_Sem_Get_Student_Result_Marksheet_BMU.this, assignView, _Act,YEAR_ID+"",COL_ID+"",SEM_ID+"",TERM_ID+"",student_id+"");

                        lv.setAdapter(assignmentViewAdapter);


                    } else {
                        txt_records.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.GONE);
                        DialogUtils.Show_Toast(End_Sem_Get_Student_Result_Marksheet_BMU.this, "No Records Found");
                    }
                } else {
                    DialogUtils.Show_Toast(End_Sem_Get_Student_Result_Marksheet_BMU.this, "No Records Found");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.hideProgressDialog();
                System.out.println("this is error!!!!!!!!!!! ");
                error.printStackTrace();
            }
        });

        queue.add(req);
    }

    String YEAR_ID = "", COLLEGE_ID = "", SEM_ID = "", TERM_ID = "", STUDENT_ID = "";

    private void initView() {
        txt_records = (CustomTextView) findViewById(R.id.txt_records);
        lv = (ListView) findViewById(R.id.lv);


    }
}
