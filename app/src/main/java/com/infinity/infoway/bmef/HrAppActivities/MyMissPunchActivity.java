package com.infinity.infoway.bmef.HrAppActivities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.bmef.CommonCls.CustomBoldTextView;
import com.infinity.infoway.bmef.CommonCls.CustomTextView;
import com.infinity.infoway.bmef.CommonCls.DialogUtils;
import com.infinity.infoway.bmef.CommonCls.MySharedPrefereces;
import com.infinity.infoway.bmef.HrAppAPI.URLS;
import com.infinity.infoway.bmef.HrAppAdapter.MyMissPunchAdapter;
import com.infinity.infoway.bmef.HrAppPojo.MissPunchListPojo;
import com.infinity.infoway.bmef.R;


import java.util.ArrayList;
import java.util.List;

public class MyMissPunchActivity extends AppCompatActivity
{
    CustomBoldTextView txt_act;
    ImageView iv_back;
    ListView lvmisspunch;
    MyMissPunchAdapter myMissPunchAdapter;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    MySharedPrefereces mySharedPrefereces;
    RequestQueue queue;
    static CustomTextView txt_records;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_miss_punch);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        txt_act = (CustomBoldTextView) findViewById(R.id.txt_act);
        txt_act.setText("View Miss Punch");
        initView();

        MyMissPunchViewAPICall(1);
    }

    List<MissPunchListPojo.DataBean> listall;
    MissPunchListPojo missPunchListPojo;

    private void MyMissPunchViewAPICall(final int PageNo)
    {
        String url = URLS.Get_miss_punch_request_list + "&emp_id=" + mySharedPrefereces.getEmpID() + "&RowsPerPage=" + URLS.RowsPerPage + "&PageNumber=" + PageNo + "";
        url = url.replace(" ","%20");
        System.out.println("Get_miss_punch_request_list URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
//                DialogUtils.hideProgressDialog();

                System.out.println("response of Get_miss_punch_request_list !!!!!!!!!!! " + response);
                response = response + "";

                if (response.length() > 5)
                {
                    response = "{\"Data\":" + response + "}";

                    System.out.println("sucess response Get_miss_punch_request_list !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    missPunchListPojo = gson.fromJson(response, MissPunchListPojo.class);
                    listall.addAll(missPunchListPojo.getData());

                    if (missPunchListPojo != null)
                    {
                        if (missPunchListPojo.getData() != null)
                        {
                            if (missPunchListPojo.getData().get(0) != null)
                            {
                                if (missPunchListPojo.getData().size() > 0)
                                {
                                    txt_records.setVisibility(View.GONE);
                                    lvmisspunch.setVisibility(View.VISIBLE);
                                    if (lvmisspunch != null)
                                    {

                                        if (myMissPunchAdapter != null && PageNo > 0)
//                        if (adapter != null && pageNo > 1)
                                        {
                                            myMissPunchAdapter.notifyDataSetChanged();
                                        }
                                        else
                                        {

                                            myMissPunchAdapter = new MyMissPunchAdapter(MyMissPunchActivity.this, missPunchListPojo, listall);
                                            myMissPunchAdapter.notifyDataSetChanged();
                                            lvmisspunch.setAdapter(myMissPunchAdapter);
                                        }

                                        int pg_ = PageNo + 1;

                                        System.out.println("*********miss punch view  *******again called+++++++++++ " + pg_ + "");

                                        //******** for page >1
                                        MyMissPunchViewAPICall(pg_);

                                        myMissPunchAdapter = new MyMissPunchAdapter(MyMissPunchActivity.this, missPunchListPojo, listall);
                                        lvmisspunch.setAdapter(myMissPunchAdapter);

                                    }

                                }
                                else
                                {
                                    txt_records.setVisibility(View.VISIBLE);
                                    lvmisspunch.setVisibility(View.GONE);
                                    System.out.println("else  calll ################");
                                    DialogUtils.Show_Toast(MyMissPunchActivity.this, "No Records Found");
                                }

                            }
                        }
                    }
                }
                else
                {
                    if (PageNo == 1)
                    {
                        listall.clear();
                        txt_records.setVisibility(View.VISIBLE);
                        lvmisspunch.setVisibility(View.GONE);

                        DialogUtils.Show_Toast(MyMissPunchActivity.this, "No Records Found");
                        myMissPunchAdapter = new MyMissPunchAdapter(MyMissPunchActivity.this, missPunchListPojo, listall);
                        myMissPunchAdapter.notifyDataSetChanged();
                        lvmisspunch.setAdapter(myMissPunchAdapter);
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(MyMissPunchActivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private void initView() {
        queue = Volley.newRequestQueue(MyMissPunchActivity.this);
        listall = new ArrayList<>();
        lvmisspunch = (ListView) findViewById(R.id.lv_miss_punch);
        txt_records =(CustomTextView)findViewById(R.id.txt_records);
        mySharedPrefereces = new MySharedPrefereces(getApplicationContext());
        PackageInfo pInfo = null;
        assert pInfo != null;

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv_emp_code = (CustomBoldTextView) findViewById(R.id.tv_emp_code);
        tv_version = (CustomBoldTextView) findViewById(R.id.tv_version);
        tv_version_code = (CustomBoldTextView) findViewById(R.id.tv_version_code);
        //tv_version.setText(pInfo.versionName);
        tv_version.setText("V."+pInfo.versionName);
        tv_emp_code.setText(mySharedPrefereces.getEmpCode());
    }
}
