package com.infinity.infoway.bmef.HrAppActivities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.infinity.infoway.bmef.HrAppAdapter.LeaveBalanceAdapter;
import com.infinity.infoway.bmef.HrAppPojo.LeaveBalancePojo;
import com.infinity.infoway.bmef.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class LeaveBalanceActivity extends AppCompatActivity {
    CustomBoldTextView txt_act;
    ImageView iv_back;
    ListView lv_balance_leave;
    LeaveBalanceAdapter leaveBalanceAdapter;
    MySharedPrefereces mySharedPreferenses;
    RequestQueue queue;
    CustomBoldTextView tv_emp_code, tv_version, tv_version_code;
    MySharedPrefereces mySharedPrefereces;
    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    CardView card_leave_balance;
    int currentMonth = 0;
    int currentYear = 0;
    CustomTextView txt_records;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_balance);
        card_leave_balance = (CardView) findViewById(R.id.card_leave_balance);
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
        txt_act.setText("Leave Balance");

        queue = Volley.newRequestQueue(LeaveBalanceActivity.this);
        lv_balance_leave = (ListView) findViewById(R.id.lv_balance_leave);
        txt_records = (CustomTextView) findViewById(R.id.txt_records);
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


        Date currentTime = localCalendar.getTime();
        int currentDay = localCalendar.get(Calendar.DATE);
        currentMonth = localCalendar.get(Calendar.MONTH) + 1;
        currentYear = localCalendar.get(Calendar.YEAR);
        int currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
        int currentDayOfMonth = localCalendar.get(Calendar.DAY_OF_MONTH);
        int CurrentDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);


        System.out.println("Current Date: " + currentTime);
        System.out.println("Current Day: " + currentDay);
        System.out.println("Current Month: " + currentMonth);
        System.out.println("Current Year: " + currentYear);
        System.out.println("Current Day of Week: " + currentDayOfWeek);
        System.out.println("Current Day of Month: " + currentDayOfMonth);
        System.out.println("Current Day of Year: " + CurrentDayOfYear);


        LeaveBalanceAPICall(String.valueOf(currentYear));
    }

    //********** leave balance dislay as per web *********
    private void LeaveBalanceAPICall(String year)
    {
        mySharedPreferenses = new MySharedPrefereces(getApplicationContext());
        String url = URLS.Get_User_LeaveBalance + "&user_id=" + mySharedPreferenses.getUserID() + "&year=" + year + "";
//        String url = "http://rku.ierp.co.in/ierphr.asmx/Get_User_LeaveBalance?" + "&user_id=" + "1044"+ "&year="+"2020"+"";
        url = url.replace(" ", "%20");
        System.out.println("Get_User_LeaveBalance URL " + url + "");
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
//                DialogUtils.hideProgressDialog();
                if (response.length() > 10) {
                    System.out.println("response of Get_User_LeaveBalance !!!!!!!!!!! " + response);
                    response = response + "";
                    response = "{\"Data\":" + response + "}";

                    System.out.println("sucess response Get_User_LeaveBalance !!!!!!!!!!!!!!!!!!!" + response + "");
                    Gson gson = new Gson();
                    LeaveBalancePojo leaveBalancePojo = gson.fromJson(response, LeaveBalancePojo.class);
                    if (leaveBalancePojo != null)
                    {
                        if (leaveBalancePojo.getData() != null)
                        {
                            if (leaveBalancePojo.getData().get(0) != null)
                            {
                                if (leaveBalancePojo.getData().size() > 0)
                                {
                                    txt_records.setVisibility(View.GONE);
                                    lv_balance_leave.setVisibility(View.VISIBLE);
                                    card_leave_balance.setVisibility(View.VISIBLE);
                                    if (lv_balance_leave != null)
                                    {
                                        card_leave_balance.setVisibility(View.VISIBLE);
                                        leaveBalanceAdapter = new LeaveBalanceAdapter(LeaveBalanceActivity.this, leaveBalancePojo);
                                        lv_balance_leave.setAdapter(leaveBalanceAdapter);

                                    }

                                }
                                else
                                    {
                                    txt_records.setVisibility(View.VISIBLE);
                                    lv_balance_leave.setVisibility(View.GONE);
                                    //card_leave_balance.setVisibility(View.GONE);
                                    DialogUtils.Show_Toast(LeaveBalanceActivity.this, "No Records Found");

                                }

                            }
                        }
                    }
                } else {
                    txt_records.setVisibility(View.VISIBLE);
                    lv_balance_leave.setVisibility(View.GONE);
                 //   card_leave_balance.setVisibility(View.GONE);
                    DialogUtils.Show_Toast(LeaveBalanceActivity.this, "No Records Found");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(LeaveBalanceActivity.this, "Please Try Again Later");
//                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }
}
