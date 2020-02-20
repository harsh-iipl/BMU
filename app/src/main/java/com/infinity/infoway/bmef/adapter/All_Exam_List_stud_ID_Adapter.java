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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.infinity.infoway.bmef.CommonCls.CustomTextView;
import com.infinity.infoway.bmef.CommonCls.DialogUtils;
import com.infinity.infoway.bmef.CommonCls.URl;
import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.activity.End_Sem_Get_Student_Result_Marksheet_BMU;
import com.infinity.infoway.bmef.app.DataStorage;
import com.infinity.infoway.bmef.app.MarshMallowPermission;
import com.infinity.infoway.bmef.model.All_Exam_List_stud_Id;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class All_Exam_List_stud_ID_Adapter extends BaseAdapter {
    MarshMallowPermission marshMallowPermission;

    private Context ctx;
    ViewHolder holder = null;
    private LayoutInflater inflater;
    View view;
    DataStorage storage;
    Activity a;
    All_Exam_List_stud_Id all_exam_list_stud_id;
    Activity _Act;

    public All_Exam_List_stud_ID_Adapter(Context ctx, Activity a, All_Exam_List_stud_Id assignView, Activity _Act) {

        this.ctx = ctx;
        this.a = a;
        this.all_exam_list_stud_id = assignView;
        this._Act = _Act;
        inflater = LayoutInflater.from(this.ctx);
        storage = new DataStorage("Login_Detail", ctx);


    }

    @Override
    public int getCount() {
        return all_exam_list_stud_id.getData().size();
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

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.all_exam_list_stud_if_adapter, null);
            holder = new ViewHolder();


            holder.iv_file = (ImageView) view.findViewById(R.id.iv_file);

            holder.date = (CustomTextView) view.findViewById(R.id.date);
            holder.tv_an = (CustomTextView) view.findViewById(R.id.tv_an);
            holder.tv_course = (CustomTextView) view.findViewById(R.id.tv_course);
            holder.tv_sem = (CustomTextView) view.findViewById(R.id.tv_sem);
            holder.tv_div = (CustomTextView) view.findViewById(R.id.tv_div);
            holder.tv_sub_name = (CustomTextView) view.findViewById(R.id.tv_sub_name);
            holder.tv_view = (CustomTextView) view.findViewById(R.id.tv_view);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        marshMallowPermission = new MarshMallowPermission(a);
        holder.tv_an.setText("" + all_exam_list_stud_id.getData().get(position).getDegree_Name() + "");
        holder.tv_course.setText("" + all_exam_list_stud_id.getData().get(position).getSemester_Name() + "");
        holder.tv_sem.setText("" + all_exam_list_stud_id.getData().get(position).getExam_name() + "");
        holder.tv_div.setText("" + all_exam_list_stud_id.getData().get(position).getStudent_exam_type() + "");
        holder.tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(_Act, End_Sem_Get_Student_Result_Marksheet_BMU.class);
//                 : 1) Key = v1d1dRrPaNcg
//                  2) year_id = 1 (For Testing)
//                  3) college_id = 1 (For Testing)
//                  4) sem_id = 1 (For Testing)
//                  5) term_id = 1 (For Testing)
//                  6) student_id = 7 (For Testing)
                i.putExtra("YEAR_ID",  all_exam_list_stud_id.getData().get(position).getSwd_year_id()+"");
                i.putExtra("COLLEGE_ID",  all_exam_list_stud_id.getData().get(position).getSwd_college_id()+"");
                i.putExtra("SEM_ID",  all_exam_list_stud_id.getData().get(position).getSwd_sem_id()+"");
                i.putExtra("TERM_ID",  all_exam_list_stud_id.getData().get(position).getSwd_term_id()+"");
                i.putExtra("STUDENT_ID",  all_exam_list_stud_id.getData().get(position).getStudent_Id()+"");

                ctx.startActivity(i);

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
                dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+URl.FOLDER_NAME);

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
