package com.infinity.infoway.bmef.OnlineLecture;

import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.bmef.CommonCls.URl;
import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.activity.EmployeeFillAttendanceActivity;
import com.infinity.infoway.bmef.activity.Student_Attendance;
import com.infinity.infoway.bmef.adapter.OnlneLectureAdapter;
import com.infinity.infoway.bmef.app.DataStorage;
import com.infinity.infoway.bmef.model.JoinOnlineLecturePojo;
import com.infinity.infoway.bmef.model.TopicPojo;

import java.util.ArrayList;
import java.util.List;

public class JoinOnlineLectureActivity extends AppCompatActivity {

    private RecyclerView rvonlineLecture;
    private ArrayList<String>onlineLectureList;
    DataStorage storage;
    private RequestQueue queue;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_online_lecture);
        initView();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable()
        {
            @Override
            public void run()
            {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);
            }
        });
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });
        get_student_today_lecture_Api();
    }



    private void initView(){
        storage = new DataStorage("Login_Detail", JoinOnlineLectureActivity.this);
        rvonlineLecture = findViewById(R.id.rvonlineLecture);
        rvonlineLecture.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        onlineLectureList = new ArrayList<>();
        onlineLectureList.add("Lecture-1");
        queue = Volley.newRequestQueue(this);
    }

    private void getOnlineLecture(){


    }


    public void get_student_today_lecture_Api() {
        String URLs = URl.get_student_today_lecture_Api + "&stud_id=" + String.valueOf(storage.read("stud_id", 3)) +"&year_id=" + String.valueOf(storage.read("swd_year_id", 3)) +"";
        URLs = URLs.replace(" ", "%20");
        System.out.println("get_student_today_lecture_Api    " + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  namesNH = new String[0];
                        //DialogUtils.hideProgressDialog();
                        response = response + "";
                        System.out.println("Api_topic      " + response + "");
                        response = "{\"Table\":" + response + "}";


                        // System.out.println("THIS IS StudentsDisplay_fill_attendance RESPONSE     " + response + "");
                        if (response.length() > 5) {


                            Gson gson = new Gson();
                            JoinOnlineLecturePojo joinOnlineLecturePojo = gson.fromJson(response,JoinOnlineLecturePojo.class);
                            System.out.println(joinOnlineLecturePojo);

    OnlneLectureAdapter onlneLectureAdapter = new OnlneLectureAdapter(JoinOnlineLectureActivity.this,joinOnlineLecturePojo);
                            rvonlineLecture.setAdapter(onlneLectureAdapter);








                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //DialogUtils.hideProgressDialog();
            }
        });
        queue.add(req);

    }
}