package com.infinity.infoway.bmef.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.infinity.infoway.bmef.CommonCls.CustomButton;
import com.infinity.infoway.bmef.CommonCls.DialogUtils;
import com.infinity.infoway.bmef.CommonCls.FileUtils;
import com.infinity.infoway.bmef.CommonCls.URl;
import com.infinity.infoway.bmef.HrAppPojo.CategoryPojo;
import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.activity.ChangePasswordActivity;
import com.infinity.infoway.bmef.activity.E_Learning_PostList;
import com.infinity.infoway.bmef.activity.Main3Activity;
import com.infinity.infoway.bmef.activity.NewProfileActivity;
import com.infinity.infoway.bmef.app.DataStorage;
import com.infinity.infoway.bmef.app.MarshMallowPermission;
import com.infinity.infoway.bmef.model.DeletePojo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static com.android.volley.VolleyLog.TAG;

public class PhotSignFragment extends Fragment implements View.OnClickListener {

    CircleImageView imgbtnselectphot, imgbtnsignature;
    private PhotSignFragment photSignFragment;

    private static final int GALLERY_PICK = 1;
    private static final int IMAGE_CAPTURE = 1;
    Uri imagesignatureuri, imagephotouri;
    Bitmap bitmap;
    RequestQueue queue;
    boolean sig = false;
    boolean photo = false;
    DataStorage dataStorage;
    private String imgByteSign, imgBytePhoto;
    private View view;
    Context ctx;
    /**
     * Save
     */
    private CustomButton btn_save;

    //checking has runtime permmition or not
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phot_sign_layout, container, false);


        dataStorage = new DataStorage("Login_Detail", getActivity());
        _act = getActivity();
        ctx = getActivity();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        imgbtnselectphot = view.findViewById(R.id.iv_student_image);
        imgbtnsignature = view.findViewById(R.id.iv_student_signaturephoto);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Drawable mDefaultBackground = getResources().getDrawable(R.drawable.defaultprofile);
        Glide.with(getActivity()).load(ProfileFragment.PROFILE_PHOTO).fitCenter().error(mDefaultBackground).into(imgbtnselectphot);
        Glide.with(getActivity()).load(ProfileFragment.PROFILE_SIGN).fitCenter().error(mDefaultBackground).into(imgbtnsignature);


        try {
//                    getBase64EncodedImage();

            bPhoto = getBitmapFromURL(ProfileFragment.PROFILE_PHOTO);
            bSign = getBitmapFromURL(ProfileFragment.PROFILE_SIGN);
            if (bPhoto != null) {
                System.out.println("bb ");
                imgBytePhoto = bitmapToBase64(bPhoto);
                System.out.println("String!!!!!!!!!!!! " + imgBytePhoto + "");

                String ex = ProfileFragment.FILE_PHOTO;
                String e = ex.substring(ex.lastIndexOf("."), ex.length());

                PHOTO_FILE_NAME = ProfileFragment.ADMISSTION_NO + "" + e + "";
                System.out.println("PHOTO_FILE_NAME " + PHOTO_FILE_NAME + "");


            } else {
                System.out.println("NUL!!!!!!!!!!!!!!!!!!!!!");
            }
            if (bSign != null) {
                System.out.println("bSign ");
                imgByteSign = bitmapToBase64(bSign);
                System.out.println("String sign!!!!!!!!!!!! " + imgByteSign + "");

                String ex = ProfileFragment.FILE_SIGN;
                String e = ex.substring(ex.lastIndexOf("."), ex.length());

                SIGN_FILE_NAME = ProfileFragment.ADMISSTION_NO + "" + e + "";
            } else {
                System.out.println("bSign NUL!!!!!!!!!!!!!!!!!!!!!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        String birth = ProfileFragment.STUD_BIRTH_PLACE;
        System.out.println(birth);
        String STUD_RELIGION = ProfileFragment.STUD_RELIGION;
        System.out.println("STUD_RELIGION" + STUD_RELIGION);
      //  String STUD_NATION = ProfileFragment.STUD_NATION;
      //  System.out.println("STUD_NATION" + STUD_NATION);
        String STUD_BDATE = ProfileFragment.STUD_BDATE;
        System.out.println("STUD_BDATE" + STUD_BDATE);
        String STUD_BLOOD = ProfileFragment.STUD_BLOOD;
        System.out.println("STUD_BLOOD" + STUD_BLOOD);
        String STUD_CATEGORY = ProfileFragment.STUD_CATEGORY;
        System.out.println("STUD_CATEGORY" + STUD_CATEGORY);
        int STUD_GENDER = ProfileFragment.SELECTEDGENDER;
        System.out.println("STUD_GENDER" + STUD_GENDER);
        String STUD_AADHAR = ProfileFragment.STUD_AADHAR;
        System.out.println("STUD_AADHAR" + STUD_AADHAR);
        String STUD_EMAIL = ContactDetailsFragment.STUD_EMAIL;
        System.out.println("STUD_EMAIL" + STUD_EMAIL);
        String STUD_FATHER_MOBILE = ContactDetailsFragment.STUD_FATHER_MOBILE;
        System.out.println("STUD_FATHER_MOBILE" + STUD_FATHER_MOBILE);
        String STUD_ADDRESS = ContactDetailsFragment.STUD_ADDRESS.toString();
        System.out.println("STUD_ADDRESS" + STUD_ADDRESS);
        String STUD_CITY = ContactDetailsFragment.STUD_CITY.toString();
        System.out.println("STUD_CITY" + STUD_CITY);
        String STUD_DIST = ContactDetailsFragment.STUD_DIST.toString();
        System.out.println("STUD_DIST" + STUD_DIST);
        String STUD_STATE = ContactDetailsFragment.STUD_STATE.toString();
        System.out.println("STUD_STATE" + STUD_STATE);
        String STUD_PINCODE = ContactDetailsFragment.STUD_PINCODE.toString();
        System.out.println("STUD_PINCODE" + STUD_PINCODE);
        String STUD_NATION_contact = ContactDetailsFragment.STUD_NATION.toString();
        System.out.println("STUD_NATION_contact" + STUD_NATION_contact);
        String STUD_MOBILE = ContactDetailsFragment.STUD_MOBILE.toString();
        System.out.println("STUD_MOBILE" + STUD_MOBILE);


        imgbtnselectphot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo = true;
                sig = false;


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!hasPermissions(getActivity(), RunTimePerMissions)) {
                        System.out.println("22222222222222");
                        ActivityCompat.requestPermissions(getActivity(), RunTimePerMissions, 101);
                    } else {
                        System.out.println("11111111111111");
                        opendialog();
                    }
                } else {
                    System.out.println("3333333333333333");
                    opendialog();
                }
//                opendialog();
            }
        });

        imgbtnsignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo = false;
                sig = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!hasPermissions(ctx, RunTimePerMissions)) {
                        ActivityCompat.requestPermissions(getActivity(), RunTimePerMissions, 101);
                    } else {

                        opendialog();
                    }
                } else {

                    opendialog();
                }
                // opendialog();
            }
        });


        initView(view);
        return view;
    }


    String STUD_PHOTO_FILENAME = "", STUD_PHOTO_STRING = "", STUD_SIGN_FILENAME = "", STUD_SIGN_STRING = "";


    Activity _act;

    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(_act);

    private void opendialog() {
//        if (!marshMallowPermission.checkPermissionForExternalStorage_ctx()) {
//            marshMallowPermission.requestPermissionForExternalStorage();
//        } else {
//            final CharSequence[] options = {"Choose from Gallery", "Upload Pdf", "Cancel"};
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Document");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery")) {
                    String[] mimeTypes =
                            {
                                    "image/jpeg", "image/jpg", "image/png"
                            };
                    Intent intent = new Intent(Intent.ACTION_PICK);
//                        intent.setType("image/*");
                    // intent.setType("image/jpeg");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                        if (mimeTypes.length > 0) {
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                        }
                    } else {
                        String mimeTypesStr = "";
                        for (String mimeType : mimeTypes) {
                            mimeTypesStr += mimeType + "|";
                        }
                        intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
                    }
                    startActivityForResult(intent, GALLERY_PICK);//2=> 12,22,32,42

                }
                   /* else if (options[item].equals("Upload Pdf")) {
                        browseDocuments(2);


                    } */
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }


        });

        builder.show();
        // }
    }


    String PHOTO_FILE_NAME = "";
    String SIGN_FILE_NAME = "";


    // private void sendPhotoToServer(final String data, final String stud_leave_id, final String name) {
    private void Update_Student_Personal_Details_API_updated() {
        System.out.println("ContactDetailsFragment.STUD_FATHER_MOBILE" + ContactDetailsFragment.STUD_FATHER_MOBILE);
        System.out.println("ContactDetailsFragment.STUD_EMAI" + ContactDetailsFragment.STUD_EMAIL);
        System.out.println("ContactDetailsFragment.STUD_MOBILE" + ContactDetailsFragment.STUD_MOBILE);
        System.out.println("ContactDetailsFragment.STUD_PINCOD" + ContactDetailsFragment.STUD_PINCODE);

        DialogUtils.showProgressDialog(getActivity(), "");
        String Url = URl.Update_Student_Personal_Details_API;
        queue = Volley.newRequestQueue(ctx);
        //String url = URLS.UPDATE_MANAGER_PROFILE_DETAIL;
        Log.d(TAG, "Update_Student_Personal_Details_API: URL: " + Url);

        StringRequest postRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DialogUtils.hideProgressDialog();
                        Log.d(TAG, "sendProfileDataToServer: Response: " + response + "");
                        // response
                        Log.d("Response", response);

                        if (response.length() > 5) {
                            Gson gson = new Gson();
//"Table": [
//        {
//          "Error_code": "1",
//          "Error_msg": "Success"
//        }
//      ]
//    }

                            if (response.length() > 5) {
                                //  Gson gson = new Gson();
                                DeletePojo deletePojo = gson.fromJson(response, DeletePojo.class);

                                if (deletePojo != null && deletePojo.getTable().size() > 0) {

                                    if (deletePojo.getTable().get(0).getError_code().contentEquals("1")) {

//                                        Intent i = new Intent(getActivity(), Main3Activity.class);
//                                        getActivity().finish();
//                                        ctx.startActivity(i);
  Intent i = new Intent(getActivity(), ChangePasswordActivity.class);
                                        getActivity().finish();
                                        ctx.startActivity(i);

                                        // DialogUtils.Show_Toast(E_Learning_PostList.this, "Post Delete Successfully");


                                    }
                                }
                            }


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //        progressDialog.dismiss();
                        DialogUtils.hideProgressDialog();
                        System.out.println("errrrrrrrrrrrrrrrrrrr" + error.getMessage());
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("im_id", dataStorage.read("intitute_id", 3) + "");
                params.put("stud_id", dataStorage.read("stud_id", 3) + "");


                params.put("stud_mobile", ContactDetailsFragment.STUD_MOBILE);

                params.put("stud_email", ContactDetailsFragment.STUD_EMAIL + "");
                params.put("stud_father_mobile", ContactDetailsFragment.STUD_FATHER_MOBILE);
                params.put("stud_father_occupation", ProfileFragment.Stud_fatherOccupation);
                params.put("stud_mother_occupation", ProfileFragment.Stud_motherOccupation);

                params.put("stud_address", ContactDetailsFragment.STUD_ADDRESS);
                params.put("stud_city", ContactDetailsFragment.STUD_CITY);//
                params.put("stud_dist", ContactDetailsFragment.STUD_DIST);//
                params.put("stud_state", ContactDetailsFragment.STUD_STATE);//
                params.put("stud_pincode", ContactDetailsFragment.STUD_PINCODE);
                params.put("stud_nation", ContactDetailsFragment.STUD_NATION);
                params.put("stud_birth_place", ProfileFragment.STUD_BIRTH_PLACE);
                params.put("stud_religion", ProfileFragment.STUD_RELIGION);//
                params.put("stud_bdate", ProfileFragment.STUD_BDATE);
                params.put("stud_blood", ProfileFragment.STUD_BLOOD);
                params.put("stud_category", ProfileFragment.STUD_CATEGORY);

                params.put("stud_gender", ProfileFragment.STUD_GENDER);

                params.put("stud_aadhar", ProfileFragment.STUD_AADHAR);
                params.put("stud_photo_filename", PHOTO_FILE_NAME);
                params.put("stud_photo_string", imgBytePhoto);
                params.put("stud_sign_filename", SIGN_FILE_NAME);
                params.put("stud_sign_string", imgByteSign);
                params.put("stud_modify_by", String.valueOf(dataStorage.read("stud_id", 3)));
                params.put("stud_modify_ip", "1");

                for (String s : params.keySet()) {
                    System.out.println("key ::::" + s);
                    System.out.println("value:::::" + params.get(s));
                    Log.d(TAG, "getParams: request parameters" + params.toString());
                }
                return params;
            }
        };
        queue.add(postRequest);

    }


    //imgByteSign,imgBytePhoto
    private void Update_Student_Personal_Details_API() {


        String URLs = URl.Update_Student_Personal_Details_API + "&im_id=" + String.valueOf(dataStorage.read("intitute_id", 3)) + "&stud_id=" + String.valueOf(dataStorage.read("stud_id", 3)) +
                "&stud_mobile=" + ContactDetailsFragment.STUD_MOBILE +
                "&stud_email=" + ContactDetailsFragment.STUD_EMAIL +
                "&stud_father_mobile=" + ContactDetailsFragment.STUD_FATHER_MOBILE +
                "&stud_father_occupation=" + ProfileFragment.Stud_fatherOccupation +
                "&stud_mother_occupation=" + ProfileFragment.Stud_motherOccupation +
                "&stud_address=" + ContactDetailsFragment.STUD_ADDRESS +
                "&stud_city=" + ContactDetailsFragment.STUD_CITY +
                "&stud_dist=" + ContactDetailsFragment.STUD_DIST +
                "&stud_state=" + ContactDetailsFragment.STUD_STATE +
                "&stud_pincode=" + ContactDetailsFragment.STUD_PINCODE +
                "&stud_nation=" + ContactDetailsFragment.STUD_NATION +
                "&stud_birth_place=" + ProfileFragment.STUD_BIRTH_PLACE +
                "&stud_religion=" + ProfileFragment.STUD_RELIGION +
                "&stud_bdate=" + ProfileFragment.STUD_BDATE +
                "&stud_blood=" + ProfileFragment.STUD_BLOOD +
                "&stud_category=" + ProfileFragment.STUD_CATEGORY +
                "&stud_gender=" + ProfileFragment.STUD_GENDER +
                "&stud_aadhar=" + ProfileFragment.STUD_AADHAR +
                "&stud_photo_filename=" + PHOTO_FILE_NAME +
                "&stud_photo_string=" + imgBytePhoto +
                "&stud_sign_filename=" + SIGN_FILE_NAME +
                "&stud_sign_string=" + imgByteSign +
                "&stud_modify_by=" + String.valueOf(dataStorage.read("stud_id", 3)) +
                "&stud_modify_ip=" + "1" + "";

        URLs = URLs.replace(" ", "%20");
        URLs = URLs.replace("null", "");
        Log.e("FINAL URL= ", URLs + "");
        StringRequest request = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response!!!!!!!!!!! " + response + "");
                Log.e("res", response);
                if (response.length() > 5) {
                    try {
                        Gson gson = new Gson();
                        DeletePojo deletePojo = gson.fromJson(response, DeletePojo.class);

                        if (deletePojo != null && deletePojo.getTable().size() > 0) {

                            if (deletePojo.getTable().get(0).getError_code().contentEquals("1")) {

                                Intent i = new Intent(getActivity(), Main3Activity.class);
                                getActivity().finish();
                                ctx.startActivity(i);

                                // DialogUtils.Show_Toast(E_Learning_PostList.this, "Post Delete Successfully");


                            } else {
                                DialogUtils.Show_Toast(getActivity(), "Please Try Again!!");
                            }
                        } else {
                            DialogUtils.Show_Toast(getActivity(), "Please Try Again!!");
                        }
                    } catch (Exception e) {

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                System.out.println("this is error ");
                error.printStackTrace();

            }
        });

        queue.add(request);


    }




/*
    URLs = URLs.replace(" ", "%20");
        //  System.out.println("Get_category_master_APIs    " + URLs + "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Log.e("GetI",response.toString());
                city_id_list = new ArrayList<>();
                city_list = new ArrayList<>();

                if (response.length() > 5) {

                    Gson gson = new Gson();

                    cityPojo = gson.fromJson(response, CityPojo.class);

                    city_list.add("Select City");
//

                    for (int i = 0; i < cityPojo.getTable().size(); i++) {

                        city_list.add(cityPojo.getTable().get(i).getCity_name());
                        System.out.println("categoryPojo.getTable().get(i).getCategory_id() + \"\" " + cityPojo.getTable().get(i).getCity_id() + "");
                        if (city_id.contentEquals(cityPojo.getTable().get(i).getCity_id() + "")) {
                            selectedcity = i + 1;
                            System.out.println(selectedcity);
                            //  spin_category.setSelection(i + 1);
                            System.out.println(i + 1);
                        }


                    }

                    adapter_city = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, city_list);
                    city_spin.setAdapter(adapter_city);
                    city_spin.setSelection(selectedcity);


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(stringRequest);*/


    public byte[] readByteFromFile(File file) throws IOException {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }

    //    private String[] RunTimePerMissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private String[] RunTimePerMissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    //    Bitmap bitmap = null;
    Bitmap bitmap1 = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // chnages add by harsh  for mi phone 18/2/2020
       // if (photSignFragment != null){
           // photSignFragment.onActivityResult(requestCode, resultCode, data);
            if (requestCode == GALLERY_PICK && resultCode == Activity.RESULT_OK && data != null) {
                Toast.makeText(getActivity().getApplicationContext(), "Photo Selected", Toast.LENGTH_LONG).show();
                imagesignatureuri = data.getData();
                imagephotouri = data.getData();




//            Bitmap bitmap = null;
//            Bitmap bitmap1 = null;
                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagesignatureuri);
//                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
//
//                byte[] byteArray = stream.toByteArray();
//                System.out.println("byarrayyyyyyyyyy:::::" + byteArray.toString());
//
//                bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagephotouri);

                    //  Drawable mDrawable = new BitmapDrawable(getResources(), bitmap);
                    if (sig) {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagesignatureuri);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);

                        byte[] byteArray = stream.toByteArray();
                        System.out.println("byarrayyyyyyyyyy:::::" + byteArray.toString());

//                    bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagephotouri);
                        // imgbtnsignature.setImageDrawable(mDrawable);
                        imgbtnsignature.setImageBitmap(bitmap);
                        sig = false;
                        photo = false;

                        String filePath = getRealPathFromURIPath(imagesignatureuri, getActivity());
                        File file = new File(filePath);
                        //     System.out.println("filepath multipart:::::::::::" + filePath);
                        System.out.println("OK:::::::::::::::");
                        String fileUrl = FileUtils.getPath(getActivity(), data.getData());
                        Log.d(TAG, "onActivityResult: " + data.getData().getPath() + " file url:" + fileUrl);
                        file = new File(fileUrl);
                        Log.d(TAG, "onActivityResult: " + "file is exist:" + file.exists() + " file absolute Path" + file.getAbsolutePath());
                        if (file.exists() && file.length() > 0) {
                            byte[] byteImage = readByteFromFile(file);

                            //  imgByteSign = Base64.encodeToString(byteSign, Base64.DEFAULT);
                            imgByteSign = Base64.encodeToString(byteImage, Base64.DEFAULT);
                            String ex = file.getName();
                            String e = ex.substring(ex.lastIndexOf("."), ex.length());

                            SIGN_FILE_NAME = ProfileFragment.ADMISSTION_NO + "" + e + "";
                            System.out.println("SIGN_FILE_NAME " + SIGN_FILE_NAME + "");
                            // System.out.println("imgByteSign" + imgByteSign);
                            System.out.println("imgByteSign" + imgByteSign);

                        } else {
                            Log.d("Save imgByteSign ", "image not exists or file size 0");
                        }


                    } else {
                        //photo
                        bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagephotouri);

                        if (bitmap1 != null) {
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap1);


//                        imgbtnselectphot.setImageDrawable(drawable);
                            imgbtnselectphot.setImageBitmap(bitmap1);


                            stream = new ByteArrayOutputStream();

                            bitmap1.compress(Bitmap.CompressFormat.PNG, 50, stream);

                            byte[] byteArray = stream.toByteArray();

                            System.out.println("byarrayyyyyyyyyy photo:::::" + byteArray.toString());

                            sig = false;
                            photo = false;
                            String filePath = getRealPathFromURIPath(imagephotouri, getActivity());
                            File file = new File(filePath);
                            System.out.println("filepath multipart:::::::::::" + filePath);

                            String fileUrl = FileUtils.getPath(getActivity(), data.getData());
                            Log.d(TAG, "onActivityResult: " + data.getData().getPath() + " file url:" + fileUrl);
                            file = new File(fileUrl);
                            Log.d(TAG, "onActivityResult: " + "file is exist:" + file.exists() + " file absolute Path" + file.getAbsolutePath());
                            if (file.exists() && file.length() > 0) {
                                byte[] byteImage = readByteFromFile(file);

                                //  imgByteSign = Base64.encodeToString(byteSign, Base64.DEFAULT);
                                imgBytePhoto = Base64.encodeToString(byteImage, Base64.DEFAULT);

                                // System.out.println("imgByteSign" + imgByteSign);
                                //  System.out.println("imgBytePhoto" + imgBytePhoto);


                                String ex = file.getName();
                                String e = ex.substring(ex.lastIndexOf("."), ex.length());

                                PHOTO_FILE_NAME = ProfileFragment.ADMISSTION_NO + "" + e + "";
                                //  System.out.println("PHOTO_FILE_NAME " + PHOTO_FILE_NAME + "");

                            } else {
                                Log.d("SaveExpensesFragment", "image not exists or file size 0");
                            }

                        }

                    }


                    String filePath2 = getRealPathFromURIPath(imagesignatureuri, getActivity());

                    File file2 = new File(filePath2);


                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("ERROR!!!!!!!!!!!!!!!!!!");
                }


                //  imgbtnsignature.setImageBitmap(bitmap);


            }

       // }

        // chnages add by harsh  for mi phone 18/2/2020




    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        // change here for image  19/2/2020
        //
        if (cursor != null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void initView(View view) {
        btn_save = (CustomButton) view.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    Bitmap bPhoto = null, bSign = null;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_save:


                System.out.println("ProfileFragment.IS_STEP_ONE_CLEAR " + ProfileFragment.IS_STEP_ONE_CLEAR + "");
                System.out.println("ProfileFragment.IS_STEP_TWO_CLEAR " + ProfileFragment.IS_STEP_TWO_CLEAR + "");
                //if (ProfileFragment.IS_STEP_ONE_CLEAR && ProfileFragment.IS_STEP_TWO_CLEAR&&imgBytePhoto!=null&&imgByteSign!=null) {
//                if (ProfileFragment.IS_STEP_ONE_CLEAR && ProfileFragment.IS_STEP_TWO_CLEAR&&imgBytePhoto!=null&&imgByteSign!=null) {
//
//                    Update_Student_Personal_Details_API();
//                Update_Student_Personal_Details_API_updated();
//                }
//                else{
//
//                }

                String birth = ProfileFragment.STUD_BIRTH_PLACE;
                System.out.println(birth);
                String STUD_RELIGION = ProfileFragment.STUD_RELIGION;
                System.out.println("STUD_RELIGION" + STUD_RELIGION);
                String STUD_NATION = ProfileFragment.STUD_NATION;
                System.out.println("STUD_NATION" + STUD_NATION+"");
                String STUD_BDATE = ProfileFragment.STUD_BDATE;
                System.out.println("STUD_BDATE" + STUD_BDATE);
                String STUD_BLOOD = ProfileFragment.STUD_BLOOD;
                System.out.println("STUD_BLOOD" + STUD_BLOOD);
                String STUD_CATEGORY = ProfileFragment.STUD_CATEGORY;
                System.out.println("STUD_CATEGORY" + STUD_CATEGORY);
                int STUD_GENDER = ProfileFragment.SELECTEDGENDER;
                System.out.println("STUD_GENDER" + STUD_GENDER);
                String STUD_AADHAR = ProfileFragment.STUD_AADHAR;
                System.out.println("STUD_AADHAR" + STUD_AADHAR);
                String STUD_EMAIL = ContactDetailsFragment.STUD_EMAIL;
                System.out.println("STUD_EMAIL" + STUD_EMAIL);
                String STUD_FATHER_MOBILE = ContactDetailsFragment.STUD_FATHER_MOBILE;
                System.out.println("STUD_FATHER_MOBILE" + STUD_FATHER_MOBILE);
                String STUD_ADDRESS = ContactDetailsFragment.STUD_ADDRESS.toString();
                System.out.println("STUD_ADDRESS" + STUD_ADDRESS);
                String STUD_CITY = ContactDetailsFragment.STUD_CITY.toString();
                System.out.println("STUD_CITY" + STUD_CITY);
                String STUD_DIST = ContactDetailsFragment.STUD_DIST.toString();
                System.out.println("STUD_DIST" + STUD_DIST);
                String STUD_STATE = ContactDetailsFragment.STUD_STATE.toString();
                System.out.println("STUD_STATE" + STUD_STATE);
                String STUD_PINCODE = ContactDetailsFragment.STUD_PINCODE.toString();
                System.out.println("STUD_PINCODE" + STUD_PINCODE);
                String STUD_NATION_contact = ContactDetailsFragment.STUD_NATION.toString();
                System.out.println("STUD_NATION_contact" + STUD_NATION_contact);
                String STUD_MOBILE = ContactDetailsFragment.STUD_MOBILE.toString();
                System.out.println("STUD_MOBILE" + STUD_MOBILE);


                if (ProfileFragment.IS_STEP_ONE_CLEAR) {
                    if (ProfileFragment.IS_STEP_TWO_CLEAR) {
                        if (imgBytePhoto != null) {
                            if (imgByteSign != null) {
                                System.out.println("all OK now call API");
                                // Update_Student_Personal_Details_API();
                                Update_Student_Personal_Details_API_updated();

                            } else {
                                DialogUtils.Show_Toast(getActivity(), "Please Upload Signature!");
                            }
                        } else {
                            DialogUtils.Show_Toast(getActivity(), "Please Upload Photo!");

                        }

                    } else {
                        NewProfileActivity.viewPager.setCurrentItem(1);
                    }
                } else {
                    NewProfileActivity.viewPager.setCurrentItem(0);
                }

                break;
        }
    }

    String imageDataString = "";

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getBase64EncodedImage() throws IOException {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    URL url = new URL(ProfileFragment.PROFILE_PHOTO);
//                    InputStream is = url.openStream();
//
//                    FileInputStream imageInFile = new FileInputStream(is.toString());
//                    byte imageData[] = new byte[2048];
//                    imageInFile.read(imageData);
//
//                    // Converting Image byte array into Base64 String
//                    //  String imageDataString = encodeImage(imageData);
//
//
//                    imageDataString = Base64.encodeToString(imageData, Base64.DEFAULT);
//                    System.out.println("imageDataString : " + imageDataString);
//
//                    System.out.println("Image Successfully Manipulated!");


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        return imageDataString;
    }


}
