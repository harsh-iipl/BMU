package com.infinity.infoway.bmef.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.bmef.CommonCls.URl;
import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.app.DataStorage;
import com.infinity.infoway.bmef.model.EmpwebPojo2;

public class EmpWebVeiwActivity extends AppCompatActivity {
    WebView webView;
    Toolbar toolbar;
    RequestQueue queue;
    DataStorage storage;
    EmpwebPojo2 empWebPojo;
    String url,uid;
    private android.widget.ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_web_veiw);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        storage = new DataStorage("Login_Detail", this);
        queue = Volley.newRequestQueue(EmpWebVeiwActivity.this);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        webView = (WebView) findViewById(R.id.wvprivacy);
        webView.setInitialScale(180);
        WebSettings webSettings  = webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setLoadWithOverviewMode(true);


        webView.getSettings().setDomStorageEnabled(true);
        // wvprivacy.getSettings().setLoadWithOverviewMode(true);


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
                onBackPressed();
                Intent intent = new Intent(EmpWebVeiwActivity.this, Main3Activity.class);
                startActivity(intent);
                finish();

            }
        });

        uid = String.valueOf(storage.read("emp_id", 3));
        Api_call_payment_gateway_url(uid);
    }

   /* public void Api_call_payment_gateway_url(String emp_id)

    {
        String URLs = URl.Direct_Login_to_Employee_using_emp_id_API + "&emp_id=" + emp_id + "";
        URLs = URLs.replace(" ", "%20");

        System.out.println("Direct_Login_to_Employee_using_emp_id_API" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response + "";
                System.out.println("Direct_Login_to_Employee_using_emp_id_API " + response + "");
//                response = "{\"enroll\":" + response + "}";


                System.out.println("TDirect_Login_to_Employee_using_emp_id_API " + response + "");
                if (response.length() > 10) {
                    Log.e("response", response.toString());

                    Gson gson = new Gson();

                    //PaymentPojo paymentPojo = gson.fromJson(response, PaymentPojo.class);
                    *//*if (paymentPojo != null) {

                      //  STUD_ID = String.valueOf(storage.read("STUD_ID", 3));


                        //url = paymentPojo.getUrl() + STUD_ID;

                       // System.out.println("url of payment::::::::::::::::::" + url);


                       // webView.loadUrl(url);
//
//                        webView.setWebViewClient(new WebViewClient()
//                        {
//                            @Override
//                            public void onPageStarted(WebView view, String url, Bitmap favicon)
//                            {
//                                super.onPageStarted(view, url, favicon);
//                                progressbar.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onPageFinished(WebView view, String url)
//                            {
//                                super.onPageFinished(view, url);
//                                progressbar.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
//                                super.onReceivedError(view, errorCode, description, failingUrl);
//                            }
//                        });

                        // progressbar.setVisibility(View.GONE);
                    }*//*


                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(req);


    }*/

    public void Api_call_payment_gateway_url(String emp_id)

    {

        String URLs = URl.Direct_Login_to_Employee_using_emp_id_API + "&emp_id=" + emp_id + "";


        System.out.println("Direct_Login_to_Employee_using_emp_id_API" + URLs + "");
        StringRequest req = new StringRequest(Request.Method.GET, URLs, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response + "";
                System.out.println("Direct_Login_to_Employee_using_emp_id_API " + response + "");
                response = "{\"enroll\":" + response + "}";


                System.out.println("TDirect_Login_to_Employee_using_emp_id_API " + response + "");
                if (response.length() > 10) {
                    Log.e("response", response.toString());

                    Gson gson = new Gson();

                    empWebPojo = gson.fromJson(response, EmpwebPojo2.class);
                    if (empWebPojo != null) {
                        Log.e("response",response.toString());

                        //  STUD_ID = String.valueOf(storage.read("STUD_ID", 3));


                        url = empWebPojo.getEnroll().get(0).getURL();

                        System.out.println("url of payment::::::::::::::::::" + url);


                        // webView.loadUrl(url);

//                        webView.setWebViewClient(new WebViewClient()
//                        {
//                            @Override
//                            public void onPageStarted(WebView view, String url, Bitmap favicon)
//                            {
//                                super.onPageStarted(view, url, favicon);
//                                progressbar.setVisibility(View.VISIBLE);
//
//                            }
//
//                            @Override
//                            public void onPageFinished(WebView view, String url)
//                            {
//                                super.onPageFinished(view, url);
//
//
//                                progressbar.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
//                                super.onReceivedError(view, errorCode, description, failingUrl);
//                            }
//                        });
                        webView.setWebViewClient(new WebViewClient()
                        {
                            @Override
                            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                super.onPageStarted(view, url, favicon);
                                progressbar.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return true;
                            }

                            @Override
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);
                                progressbar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                                // super.onReceivedError(view, request, error);
                                System.out.println("ERROR!!!!!!!!!!!!!!!");
                            }
                        });


                        webView.loadUrl(url);
                        // progressbar.setVisibility(View.GONE);
                    }


                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(req);


    }
}
