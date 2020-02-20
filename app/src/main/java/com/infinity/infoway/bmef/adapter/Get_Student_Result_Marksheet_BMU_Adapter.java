package com.infinity.infoway.bmef.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.infinity.infoway.bmef.activity.End_Sem_Get_Student_Result_Marksheet_BMU;
import com.infinity.infoway.bmef.app.DataStorage;
import com.infinity.infoway.bmef.app.MarshMallowPermission;
import com.infinity.infoway.bmef.model.All_Exam_List_stud_Id;
import com.infinity.infoway.bmef.model.Get_Student_Result_Marksheet_BMU_POJO;
import com.infinity.infoway.bmef.model.Get_Student_wise_subject_wise_result_report_by_student_id_PoJO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Get_Student_Result_Marksheet_BMU_Adapter extends BaseAdapter {
    MarshMallowPermission marshMallowPermission;

    private Context ctx;
    ViewHolder holder = null;
    private LayoutInflater inflater;
    View view;
    DataStorage storage;
    Activity a;
    Get_Student_Result_Marksheet_BMU_POJO assignView;
    Activity _Act;
    String YEAR_ID = "";
    String COL_ID = "";
    String SEM_ID = "";
    String TERM_ID = "";
    String student_id = "";

    public Get_Student_Result_Marksheet_BMU_Adapter(Context ctx, Activity a, Get_Student_Result_Marksheet_BMU_POJO assignView, Activity _Act, String YEAR_ID, String COL_ID, String SEM_ID, String TERM_ID, String student_id) {

        this.ctx = ctx;
        this.a = a;
        this.assignView = assignView;
        this._Act = _Act;
        inflater = LayoutInflater.from(this.ctx);
        storage = new DataStorage("Login_Detail", ctx);
        this.YEAR_ID = YEAR_ID;
        this.TERM_ID = TERM_ID;
        this.COL_ID = COL_ID;
        this.SEM_ID = SEM_ID;
        this.student_id = student_id;
        queue = Volley.newRequestQueue(_Act);


    }

    @Override
    public int getCount() {
        return assignView.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {
        ImageView iv_file;


        CustomTextView tv_course, date, tv_sem, tv_div, tv_sub_name, tv_an, tv_view;
        CustomTextView tv_name, tv_termName, tv_semestername, tv_enrollmentsnumber;
        CustomTextView tv_final_credit;

        CustomTextView tv_srd_Grade_sub;
        // added by harsh
        CustomTextView backe,backm,backi,backv;
        CustomTextView tv_ssrd_sgpa_credit;
        CustomTextView tv_ssrd_sgpa_earned_grade_point;
        CustomTextView tv_SPI;
        CustomTextView tv_Total_Credits;
        CustomTextView tv_EarnedGradePoints;
        CustomTextView tv_CPI;
        CustomTextView tv_Result;
        LinearLayout lin1;
        LinearLayout lin_header, lin_view;

        CardView card_view;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.get_gtudent_gesult_marksheet_bmu_adapter_u, null);
            holder = new ViewHolder();


            holder.iv_file = (ImageView) view.findViewById(R.id.iv_file);

            holder.date = (CustomTextView) view.findViewById(R.id.date);
            holder.tv_an = (CustomTextView) view.findViewById(R.id.tv_an);
            holder.tv_course = (CustomTextView) view.findViewById(R.id.tv_course);
            holder.tv_sem = (CustomTextView) view.findViewById(R.id.tv_sem);
            holder.tv_div = (CustomTextView) view.findViewById(R.id.tv_div);
            holder.tv_sub_name = (CustomTextView) view.findViewById(R.id.tv_sub_name);
            holder.tv_view = (CustomTextView) view.findViewById(R.id.tv_view);


           // holder.tv_final_credit = (CustomTextView) view.findViewById(R.id.tv_final_credit);
            holder.tv_srd_Grade_sub = (CustomTextView) view.findViewById(R.id.tv_srd_Grade_sub);
            // added by harsh
            holder.backe = (CustomTextView) view.findViewById(R.id.backe);
            holder.backm = (CustomTextView) view.findViewById(R.id.backm);
            holder.backi = (CustomTextView) view.findViewById(R.id.backi);
            holder.backv = (CustomTextView) view.findViewById(R.id.backv);

            holder.tv_ssrd_sgpa_credit = (CustomTextView) view.findViewById(R.id.tv_ssrd_sgpa_credit);
            holder.tv_ssrd_sgpa_earned_grade_point = (CustomTextView) view.findViewById(R.id.tv_ssrd_sgpa_earned_grade_point);
            holder.tv_SPI = (CustomTextView) view.findViewById(R.id.tv_SPI);
            holder.tv_Total_Credits = (CustomTextView) view.findViewById(R.id.tv_Total_Credits);
            holder.tv_EarnedGradePoints = (CustomTextView) view.findViewById(R.id.tv_EarnedGradePoints);
            holder.tv_CPI = (CustomTextView) view.findViewById(R.id.tv_CPI);
            holder.tv_Result = (CustomTextView) view.findViewById(R.id.tv_Result);

            holder.tv_enrollmentsnumber = (CustomTextView) view.findViewById(R.id.tv_enrollmentsnumber);
            holder.tv_semestername = (CustomTextView) view.findViewById(R.id.tv_semestername);
            holder.tv_termName = (CustomTextView) view.findViewById(R.id.tv_termName);
            holder.tv_name = (CustomTextView) view.findViewById(R.id.tv_name);


            holder.tv_view = (CustomTextView) view.findViewById(R.id.tv_view);
            holder.card_view = (CardView) view.findViewById(R.id.card_view);
            holder.lin1 = (LinearLayout) view.findViewById(R.id.lin1);
            holder.lin_header = (LinearLayout) view.findViewById(R.id.lin_header);
            holder.lin_view = (LinearLayout) view.findViewById(R.id.lin_view);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        marshMallowPermission = new MarshMallowPermission(a);
//        holder.tv_an.setText("  " + assignView.getData().get(position).getSub_code() + "");
        holder.tv_an.setText("" + assignView.getData().get(position).getSub_code() + " - " + assignView.getData().get(position).getSubject_name() + "");
        //  holder.tv_course.setText("  " + assignView.getData().get(position).getSubject_name() + "");
        holder.tv_sem.setText("" + assignView.getData().get(position).getSub_credit() + "");
        holder.tv_div.setText("" + assignView.getData().get(position).getSrd_sub_SPI_Credit() + "");


        /*FieldName From Json (Grid View Multiple Row)	sub_code	subject_name	sub_credit	srd_sub_SPI_Credit	final_credit	srd_Grade_sub
 	Credits	Earned Grade Points	SPI	Total Credits	Earned Grade Points	CPI	Result
FieldName From Json (Single Row)	ssrd_sgpa_credit	ssrd_sgpa_earned_grade_point	ssrd_SGPA	ssrd_cgpa_credit	ssrd_cgpa_earned_grade_point	ssrd_CGPA	result*/
        holder.tv_CPI.setText("" + assignView.getData().get(position).getSsrd_CGPA() + "");
        holder.tv_EarnedGradePoints.setText("" + assignView.getData().get(position).getSsrd_cgpa_earned_grade_point() + "");
        holder.tv_Total_Credits.setText("" + assignView.getData().get(position).getSsrd_cgpa_credit() + "");
        holder.tv_SPI.setText("" + assignView.getData().get(position).getSsrd_SGPA() + "");
        holder.tv_ssrd_sgpa_credit.setText("" + assignView.getData().get(position).getSsrd_sgpa_credit() + "");
        holder.tv_srd_Grade_sub.setText("" + assignView.getData().get(position).getSrd_Grade_sub() + "");

        // added by harsh
        holder.backe.setText("" + assignView.getData().get(position) .getTheory_ext_grade_blog_E() +"");
        holder.backm.setText("" + assignView.getData().get(position).getTheory_int_grade_blog_M() + "");
        holder.backi.setText("" + assignView.getData().get(position).getPractical_int_grade_blog_I() + "");
        holder.backv.setText("" + assignView.getData().get(position).getPractical_ext_grade_blog_V() + "");

        // added by harsh

       // holder.tv_final_credit.setText("" + assignView.getData().get(position).getFinal_credit() + "");
        holder.tv_Result.setText("" + assignView.getData().get(position).getResult() + "");
        holder.tv_ssrd_sgpa_earned_grade_point.setText("" + assignView.getData().get(position).getSsrd_sgpa_earned_grade_point() + "");


        holder.tv_name.setText("" + assignView.getData().get(position).getStudent_name() + "");
        holder.tv_enrollmentsnumber.setText("" + assignView.getData().get(position).getEnrollment_no() + "");
        holder.tv_semestername.setText("" + assignView.getData().get(position).getSem_name() + "");
        holder.tv_termName.setText("" + assignView.getData().get(position).getTrm_name() + "");




        if (position == 0) {
            holder.lin1.setVisibility(View.VISIBLE);
            //  holder.card_view.setVisibility(View.VISIBLE);
            holder.lin_header.setVisibility(View.VISIBLE);
        } else {
            holder.lin1.setVisibility(View.GONE);
            holder.lin_header.setVisibility(View.GONE);
            //   holder.card_view.setVisibility(View.GONE);
        }

        try {
            if (position == assignView.getData().size() - 1) {
                holder.lin_view.setVisibility(View.VISIBLE);
            } else {
                holder.lin_view.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            System.out.println("this is error!!!!!!!!!!!!!!!!!!!!!!!!");
        }

        //      holder.credit.setText("  " + all_exam_list_stud_id.getData().get(position).getFinal_credit() + "");


        holder.tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(_Act, End_Sem_Get_Student_Result_Marksheet_BMU.class);
//                ctx.startActivity(i);
                if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                    System.out.println("Permission not available!!!!!! ");
                    marshMallowPermission.requestPermissionForExternalStorage();
                } else {
                    System.out.println("Permission available!!!!!! ");
                    Get_Student_wise_subject_wise_result_report_by_student_id(URl.API_STUDENT_RESULT_COMMAN_KEY, YEAR_ID + "", assignView.getData().get(position).getDegree_Id() + "", SEM_ID, TERM_ID, assignView.getData().get(position).getEnrollment_no() + "", student_id + "");
                }


            }
        });
//        holder.tv_sem.setText("  " + all_exam_list_stud_id.getData().get(position).getSm_name() + "");
//        holder.date.setText("  " + all_exam_list_stud_id.getData().get(position).getCreated_date() + "");
//        holder.iv_file.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = all_exam_list_stud_id.getData().get(position).getAm_file() + "";
//
//                if (url != null && url.length() > 5) {
//                    if (!marshMallowPermission.checkPermissionForExternalStorage()) {
//                        marshMallowPermission.requestPermissionForExternalStorage();
//                    } else {
//                        String extention = url.substring(url.lastIndexOf("."), url.length());
//                        new DownloadFileFromURL(extention).execute(url);
//                    }
//
//                } else {
//                    DialogUtils.Show_Toast(a, "No Documents Available");
//                }
//
//            }
//        });


        return view;
    }

    private void Get_Student_wise_subject_wise_result_report_by_student_id(String key, String YEAR_ID, String degree_id, String SEM_ID, String TERM_ID, String created_by, String student_id) {
        DialogUtils.showProgressDialog(_Act, "");
        //http://bmef.icrp.in/exam/API_Student_Result_Comman.asmx/get_all_exam_list_student_id?Key=v1d1dRrPaNcg&student_id=7
        String Url = "";// URl.BMU_STUDENT_URL + "get_all_exam_list_student_id" + "?Key=" + key + "&student_id=" + student_id + "";
//        Url = "http://bmef.icrp.in/exam/API_Student_Result_Comman.asmx/Get_Student_Result_Marksheet_BMU?Key=v1d1dRrPaNcg&student_id=7";
        //  Url = "http://bmef.icrp.in/exam/API_Student_Result_Comman.asmx/Get_Student_Result_Marksheet_BMU?Key=v1d1dRrPaNcg&year_id=" + YEAR_ID + "&college_id=" + COL_ID + "&sem_id=" + SEM_ID + "&term_id=" + TERM_ID + "&student_id=" + student_id + "";
        // Url = "http://bmef.icrp.in/exam/API_Student_Result_Comman.asmx/Get_Student_Result_Marksheet_BMU?Key=" + key + "&year_id=" + YEAR_ID + "&college_id=" + COL_ID + "&sem_id=" + SEM_ID + "&term_id=" + TERM_ID + "&student_id=" + student_id + "";
        Url = URl.API_STUDENT_RESULT_COMMAN + "Get_Student_wise_subject_wise_result_report_by_student_id?Key=" + key + "&year_id=" + YEAR_ID + "&degree_id=" + degree_id + "&sem_id=" + SEM_ID + "&term_id=" + TERM_ID + "&created_by=" + created_by + "&student_id=" + student_id + "";

        Url = Url.replace(" ", "%20");
        System.out.println("Get_Student_Result_Marksheet_BMU   " + Url + "");
        StringRequest req = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response Get_Student_wise_subject_wise_result_report_by_student_id!!!!!! " + response + "");
                DialogUtils.hideProgressDialog();
//                response = "{\"data\":" + response + "}";
                Gson gson = new Gson();
                Get_Student_wise_subject_wise_result_report_by_student_id_PoJO get_student_result_marksheet_bmu_pojo = gson.fromJson(response, Get_Student_wise_subject_wise_result_report_by_student_id_PoJO.class);
                if (get_student_result_marksheet_bmu_pojo != null) {
                    String url = get_student_result_marksheet_bmu_pojo.getFilename() + "";

//
                    if (url != null && url.length() > 5) {
                        String extention = url.substring(url.lastIndexOf("."), url.length());
                        new DownloadFileFromURL(extention).execute(url);
//                        new DownloadFileFromURL(extention).execute("http://www.pdf995.com/samples/pdf.pdf");
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error Get_Student_wise_subject_wise_result_report_by_student_id!!!!!! " + "");
                DialogUtils.hideProgressDialog();

            }
        });
        queue.add(req);
    }

    RequestQueue queue;

    private class DownloadFileFromURL extends AsyncTask<String, String, String> {


        String nameoffile;
        String Extenson;

        DownloadFileFromURL(String Extenson) {
            this.Extenson = Extenson;

            System.out.println("EXTENSION OF FILE::::::::::" + Extenson);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            DialogUtils.showProgressDialog(a, "");

        }

        /**
         * Downloading file in menubackground thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);


                String urofysllabusl = f_url[0];
                System.out.println("urofysllabusl::::::" + urofysllabusl);
                String[] parts = urofysllabusl.split("/");
                System.out.println("parts::::::::::::::" + parts);
                String result = parts[parts.length - 1];
                System.out.println("result:::::::::::" + result);
                nameoffile = result;

                System.out.println("result::::::doInback::::" + result);
                System.out.println("name in  doInBackground>>>>" + nameoffile);
                URLConnection conection = url.openConnection();
                conection.connect();
                File dir = null;
//                dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/BMU");
                dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + URl.FOLDER_NAME);

                //    File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/GSFC/" + "Assignment");
                dir.mkdirs();


                System.out.println("path of file>>>>>" + dir.getAbsolutePath());
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
                //  Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // Output stream
//                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/SIMS/" + System.currentTimeMillis() + "_" + nameoffile);

                OutputStream output = new FileOutputStream("sdcard/" + URl.FOLDER_NAME + "/" + nameoffile);
                //   OutputStream output = new FileOutputStream(dir.getAbsolutePath()+"/" + URl.FOLDER_NAME + "/" + nameoffile);
//                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Assignment/" + nameoffile);

                System.out.println("output:::::::::" + output.toString());
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //pDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            DialogUtils.hideProgressDialog();

            DialogUtils.Show_Toast(a, "Download Completed Successfully");

            System.out.println("EXTENSION OF FILE onPostExecute::::::::::" + Extenson);
            File file11 = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), URl.FOLDER_NAME + "/" + nameoffile);

//            File file11 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GSFC/" + "/Assignment/", nameoffile);
            Log.d("pathoffile", String.valueOf(file11));


            /// for  opening downloaded documentssssssssssss
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 24) {

                Uri uri = FileProvider.getUriForFile(ctx, ctx.getPackageName() + ".fileprovider", file11);
                ctx.grantUriPermission(ctx.getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent intent = null;
                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(uri, "application/pdf");
                } else if (Extenson.compareToIgnoreCase(".png") == 0 || Extenson.compareToIgnoreCase("png") == 0 || Extenson.compareToIgnoreCase(".jpeg") == 0 || Extenson.compareToIgnoreCase("jpeg") == 0 || Extenson.compareToIgnoreCase(".jpg") == 0 || Extenson.compareToIgnoreCase("jpg") == 0) {
                    target.setDataAndType(uri, "image/*");
                }
                intent = Intent.createChooser(target, "Open File");
                try {
                    ctx.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(ctx, "No Apps can perform This Action");
                }
            } else {


                target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.setDataAndType(Uri.fromFile(file11), "image/*");

                if (Extenson.compareToIgnoreCase(".pdf") == 0 || Extenson.compareToIgnoreCase("pdf") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "application/pdf");
                } else if (Extenson.compareToIgnoreCase(".png") == 0 || Extenson.compareToIgnoreCase("png") == 0 || Extenson.compareToIgnoreCase(".jpeg") == 0 || Extenson.compareToIgnoreCase("jpeg") == 0 || Extenson.compareToIgnoreCase(".jpg") == 0 || Extenson.compareToIgnoreCase("jpg") == 0) {
                    target.setDataAndType(Uri.fromFile(file11), "image/*");

                }

                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                try {
                    ctx.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    DialogUtils.Show_Toast(ctx, "No Apps can perform This Action");

                }

            }


        }

    }

}
