package com.infinity.infoway.bmef.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.bmef.CommonCls.URl;
import com.infinity.infoway.bmef.HrAppPojo.ChangepswPojo;
import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.app.DataStorage;
import com.infinity.infoway.bmef.model.change_psw;
import com.infinity.infoway.bmef.rest.ApiClient;
import com.infinity.infoway.bmef.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText old_password, new_password, confirm_password;
    Button btn_change_psw;
    DataStorage storage;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.backarrow, null);
                toolbar.setNavigationIcon(d);
                // toolbar.setBackgroundColor(Color.GREEN);
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(ChangePasswordActivity.this,Main3Activity.class);
//                startActivity(intent);
                finish();
            }
        });

        findviews();
    }

    public void findviews() {
        storage = new DataStorage("Login_Detail", ChangePasswordActivity.this);
        queue = Volley.newRequestQueue(this);
        old_password = (EditText) findViewById(R.id.old_psw);
        new_password = (EditText) findViewById(R.id.new_psw);
        confirm_password = (EditText) findViewById(R.id.confirm_psw);
        btn_change_psw = (Button) findViewById(R.id.btn_change_psw);
        btn_change_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("called!!!!!!!!!!!!!!");
              //  Change_psw_Api_call();
                if (TextUtils.isEmpty(old_password.getText().toString())) {
                    old_password.setError("enter old Password.");
                } else if (TextUtils.isEmpty(new_password.getText().toString())) {
                    new_password.setError("enter new Password.");
                } else if (TextUtils.isEmpty(confirm_password.getText().toString())) {
                    confirm_password.setError("enter confirm Password.");
                } else if (!confirm_password.getText().toString().trim().matches(new_password.getText().toString())) {
                    confirm_password.setError("Password not match.");
                } else {
                    change_pwd();
                }
            }
        });
    }

    RequestQueue queue;
    void change_pwd() {

        final ProgressDialog process = new ProgressDialog(ChangePasswordActivity.this, R.style.MyTheme1);
        process.setCancelable(false);
        process.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//            process.setMessage("sending SMS");
        process.setMessage("Please Wait..");
        process.show();
        queue = Volley.newRequestQueue(ChangePasswordActivity.this);
        String URLs = "http://bmef.icrp.in/cms/API_Student_Panel_JSON_Icampus.asmx/application_change_password?" + "&stud_id=" + String.valueOf(storage.read("stud_id", 3)) +
                "&user_name=" +
                String.valueOf(storage.read("stud_admission_no", 3)) +

                "&new_pass=" +

                new_password.getText().toString()+"&old_pass="+old_password.getText().toString()+"";

        URLs = URLs.replace(" ", "%20");
        System.out.println("change_pwd    " + URLs + "");


        StringRequest req=new StringRequest(Request.Method.GET, URLs, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                process.dismiss();
response=response+"";
                Gson gson=new Gson();
                ChangepswPojo pojo=new ChangepswPojo();
                pojo=gson.fromJson(response,ChangepswPojo.class);
                storage.clear();


                Intent it = new Intent(ChangePasswordActivity.this, Login.class);
                startActivity(it);
                finish();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                process.dismiss();
                Toast.makeText(ChangePasswordActivity.this, "Please try again later", Toast.LENGTH_LONG);
            }
        });
        queue.add(req);
    }

    public void Change_psw_Api_call() {
        if (TextUtils.isEmpty(old_password.getText().toString())) {
            old_password.setError("enter old Password.");
        } else if (TextUtils.isEmpty(new_password.getText().toString())) {
            new_password.setError("enter new Password.");
        } else if (TextUtils.isEmpty(confirm_password.getText().toString())) {
            confirm_password.setError("enter confirm Password.");
        } else if (!confirm_password.getText().toString().trim().matches(new_password.getText().toString())) {
            confirm_password.setError("Password not match.");
        } else {
            final ProgressDialog process = new ProgressDialog(ChangePasswordActivity.this, R.style.MyTheme1);
            process.setCancelable(false);
            process.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
//            process.setMessage("sending SMS");
            process.setMessage("Please Wait..");
            process.show();


            Map<String, String> mParams;
            mParams = new HashMap<String, String>();
            mParams.put("stud_id", String.valueOf(storage.read("stud_id", 3)));
            mParams.put("user_name", String.valueOf(storage.read("stud_admission_no", 3)));
            mParams.put("old_pass", old_password.getText().toString());
            mParams.put("new_pass", new_password.getText().toString());


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ArrayList<change_psw>> call = apiService.get_change_password_applicant(mParams);
            call.enqueue(new Callback<ArrayList<change_psw>>() {
                @Override
                public void onResponse(Call<ArrayList<change_psw>> call, Response<ArrayList<change_psw>> response) {

                    System.out.println("response::::::::::  " + call.request() + "");
                    process.dismiss();
                    if (response.isSuccessful()) {

                        for (int i = 0; i < response.body().size(); i++) {
                            if (response.body().get(i).getStatus().equals("1")) {

                                Toast.makeText(ChangePasswordActivity.this, "Password updated Successfully", Toast.LENGTH_LONG).show();
                                // finish();
                                //  storage.write("stud_id","");
                                storage.clear();


                                Intent it = new Intent(ChangePasswordActivity.this, Login.class);
                                startActivity(it);
                                finish();
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Old Password is incorrect", Toast.LENGTH_LONG).show();
                            }
                        }

                    } else {
                        System.out.println("1111111111111111111");
                        Toast.makeText(ChangePasswordActivity.this, "Please try again later", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<change_psw>> call, Throwable t) {

                    process.dismiss();
                    System.out.println("2222222222222");
                    Toast.makeText(ChangePasswordActivity.this, "Please try again later", Toast.LENGTH_LONG).show();

                    // Log error here since request failed
                    // Log.e("ForgotPassword", t.toString());
                }
            });
        }


    }

}

