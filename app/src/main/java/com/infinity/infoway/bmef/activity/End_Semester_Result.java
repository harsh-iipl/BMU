package com.infinity.infoway.bmef.activity;

import android.app.Activity;
import android.content.Context;
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
import com.google.gson.Gson;
import com.infinity.infoway.bmef.CommonCls.CustomTextView;
import com.infinity.infoway.bmef.CommonCls.DialogUtils;
import com.infinity.infoway.bmef.CommonCls.URl;
import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.adapter.All_Exam_List_stud_ID_Adapter;
import com.infinity.infoway.bmef.adapter.AssignmentViewAdapter;
import com.infinity.infoway.bmef.app.DataStorage;
import com.infinity.infoway.bmef.model.All_Exam_List_stud_Id;
import com.infinity.infoway.bmef.model.AssignView;

public class End_Semester_Result extends AppCompatActivity {
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
        setContentView(R.layout.end_sem_result);
        initView();
        _Act = End_Semester_Result.this;
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


        if (!storage.isOnline(End_Semester_Result.this)) {
            showDialog(DataStorage.DIALOG_ERROR_CONNECTION);
        }

        if (storage.CheckLogin("stud_id", this)) {

        }
        txt_records = (CustomTextView) findViewById(R.id.txt_records);
        this.lv = (ListView) findViewById(R.id.lv_assignment_fact);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  this.title = (CustomTextView) findViewById(R.id.title);
        queue = Volley.newRequestQueue(End_Semester_Result.this);

     String   STUDENT_ID = storage.read("stud_id", 3) + "";
      //  get_all_exam_list_student_id( "7");//if no data found static
        get_all_exam_list_student_id( STUDENT_ID+"");

    }

    Activity _Act;

    void get_all_exam_list_student_id( String student_id) {
        DialogUtils.showProgressDialog(End_Semester_Result.this, "");
        //http://bmef.icrp.in/exam/API_Student_Result_Comman.asmx/get_all_exam_list_student_id?Key=v1d1dRrPaNcg&student_id=7
        String Url = //URl.BMU_STUDENT_URL + "get_all_exam_list_student_id" + "?Key=" + key + "&student_id=" + student_id + "";
//        Url = "http://bmef.icrp.in/exam/API_Student_Result_Comman.asmx/get_all_exam_list_student_id?Key=v1d1dRrPaNcg&student_id=7";
        Url = URl.API_STUDENT_RESULT_COMMAN + "get_all_exam_list_student_id?Key=" + URl.API_STUDENT_RESULT_COMMAN_KEY + "&student_id="+student_id+"";

        Url = Url.replace(" ", "%20");
        System.out.println("get_all_exam_list_student_id   " + Url + "");
        StringRequest req = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response!!!!!! " + response + "");
                DialogUtils.hideProgressDialog();
                try {
                    response = "{\"data\":" + response + "}";

                    if (response.length() > 10) {
                        Gson gson = new Gson();

                        All_Exam_List_stud_Id assignView = gson.fromJson(response, All_Exam_List_stud_Id.class);

                        if (assignView != null && assignView.getData().size() > 0) {
                            txt_records.setVisibility(View.GONE);
                            lv.setVisibility(View.VISIBLE);

                            All_Exam_List_stud_ID_Adapter assignmentViewAdapter = new All_Exam_List_stud_ID_Adapter(End_Semester_Result.this, End_Semester_Result.this, assignView, _Act);

                            lv.setAdapter(assignmentViewAdapter);


                        } else {
                            txt_records.setVisibility(View.VISIBLE);
                            lv.setVisibility(View.GONE);
                            DialogUtils.Show_Toast(End_Semester_Result.this, "No Records Found");
                        }
                    } else {
                        DialogUtils.Show_Toast(End_Semester_Result.this, "No Records Found");
                    }

                }catch (Exception e)
                {
                    System.out.println("Error");
                    DialogUtils.Show_Toast(End_Semester_Result.this, "No Records Found");
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

    private void initView() {
        txt_records = (CustomTextView) findViewById(R.id.txt_records);
        lv = (ListView) findViewById(R.id.lv);
    }
}
