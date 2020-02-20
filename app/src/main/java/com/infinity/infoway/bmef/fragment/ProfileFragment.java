package com.infinity.infoway.bmef.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.bmef.CommonCls.CustomEditText;
import com.infinity.infoway.bmef.CommonCls.DialogUtils;
import com.infinity.infoway.bmef.CommonCls.URl;
import com.infinity.infoway.bmef.CommonCls.Validations;
import com.infinity.infoway.bmef.HrAppPojo.BloodGroupPojo;
import com.infinity.infoway.bmef.HrAppPojo.CategoryPojo;
import com.infinity.infoway.bmef.HrAppPojo.CountryPojo;
import com.infinity.infoway.bmef.HrAppPojo.ReligionPojo;
import com.infinity.infoway.bmef.HrAppPojo.StudenDetailsPojo;
import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.activity.EmployeeFillAttendanceActivity;
import com.infinity.infoway.bmef.activity.EmployeeFillAttendanceActivity_configuration;
import com.infinity.infoway.bmef.activity.Main3Activity;
import com.infinity.infoway.bmef.activity.NewProfileActivity;
import com.infinity.infoway.bmef.app.DataStorage;
import com.infinity.infoway.bmef.model.TopicPojo;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.crypto.AEADBadTagException;

public class ProfileFragment extends Fragment {
    CustomEditText edtName, edtmiddlename, edtfathersName, edtbirthdate, edtbirthplace, edtadharno, edtreligion, edtfathersoccupation,

    edtmothername, edtmotheroccupation;
    static boolean IS_STEP_ONE_CLEAR = false;
    static boolean IS_STEP_TWO_CLEAR = false;
    String booldGroup, catgegory, spinnernationality;
    RadioGroup rg;
    RadioButton rbMale, rbFemale;
    DataStorage storage;
    RequestQueue queue;
    // for storing spinner category ids
    String category_id = "0";

    String bloodgroup_id = "0";
    String country_id = "0";
    String religion_id = "0";
    SearchableSpinner countrtySpinner;

    // for setting value on spinner
    int selectedcategory = 0;
    Calendar myCalendar = Calendar.getInstance();
    Date date11;
    CountryPojo countryPojo;
    ReligionPojo religionPojo;


    // pojos//
    CategoryPojo categoryPojo;
    BloodGroupPojo bloodGroupPojo;
    StudenDetailsPojo studenDetailsPojo;

    Context context;
    Activity activity;
    String myFormat1 = "dd/MM/yyyy";
    Button btnnext;
    SearchableSpinner edtnationality, spin_category, spin_bloodgroup, spin_religion;
    ArrayAdapter<String> adapter_category, adapter_bloodgroup, adapter_nationality, adapter_religion;
    String SELECTED_CATEGORY = "", SELECTED_BLOOD_GORUP = "", SELECTED_GENDER = "", SELCTED_NATIONALITY = "", SELCTED_RELIGION = "";
    List<String> category_list = new ArrayList<>();
    List<String> category_id_list = new ArrayList<>();
    List<String> bloodgroup_list = new ArrayList<>();
    List<String> bloodgroup_id_list = new ArrayList<>();
    List<String> country_list = new ArrayList<>();
    List<String> religion_list = new ArrayList<>();
    List<String> coluntry_id_list = new ArrayList<>();
    List<String> religion_id_list = new ArrayList<>();
    Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.personal_details_form, container, false);
        edtName = view.findViewById(R.id.edtname);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        edtfathersoccupation = view.findViewById(R.id.edtfathersoccupation);
        edtmothername = view.findViewById(R.id.edtmothername);
        edtmotheroccupation = view.findViewById(R.id.edtmotheroccupation);
        spin_religion = view.findViewById(R.id.spin_religionpersonal);
        spin_religion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i > 0) {
                    SELCTED_RELIGION = religion_id_list.get(i);
                    // System.out.println("SELCTED_RELIGION" + SELCTED_RELIGION);

                } else {
                    SELCTED_RELIGION = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        countrtySpinner = view.findViewById(R.id.edtnationality);
        countrtySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    SELCTED_NATIONALITY = coluntry_id_list.get(i);
                    STUD_NATION = coluntry_id_list.get(i);

                } else {
                    SELCTED_NATIONALITY = "";
                    STUD_NATION = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edtmiddlename = view.findViewById(R.id.edtmiddlename);
        storage = new DataStorage("Login_Detail", getActivity());
        edtfathersName = view.findViewById(R.id.edtfathersName);
        context = getActivity();
        edtbirthdate = view.findViewById(R.id.edtbirthdate);
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//                updateLabel1();
                date11 = myCalendar.getTime();

                SimpleDateFormat sdf = new SimpleDateFormat(myFormat1, Locale.US);
                edtbirthdate.setText(sdf.format(date11));

            }

        };

        edtbirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new DatePickerDialog(getActivity(), date1, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                DatePickerDialog dialog = new DatePickerDialog(getActivity(), date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
                // return dialog;

            }
        });


        edtbirthplace = view.findViewById(R.id.edtbirthplace);
        spin_bloodgroup = view.findViewById(R.id.spin_bloodgroup);
        spin_bloodgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("pos", String.valueOf(i));
                if (i > 0) {
                    SELECTED_BLOOD_GORUP = bloodgroup_id_list.get(i);
                    STUD_BLOOD = SELECTED_BLOOD_GORUP;

                } else {
                    SELECTED_BLOOD_GORUP = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spin_category = view.findViewById(R.id.spin_category);
        spin_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_CATEGORY = category_id_list.get(i);
                    System.out.println(SELECTED_CATEGORY);


                } else {
                    SELECTED_CATEGORY = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //  SELECTED_CATEGORY = "00";
            }
        });
//        spin_category.setSelection(0);
        edtadharno = view.findViewById(R.id.edtadharno);
        edtnationality = view.findViewById(R.id.edtnationality);
        // edtreligion = view.findViewById(R.id.edtreligion);
        rg = (RadioGroup) view.findViewById(R.id.rg);
        rbMale = (RadioButton) view.findViewById(R.id.rb_male);
        rbFemale = (RadioButton) view.findViewById(R.id.rb_female);
        btnnext = view.findViewById(R.id.btnnext);
        rbMale.setTypeface(Validations.setTypeface(getActivity()));
        rbFemale.setTypeface(Validations.setTypeface(getActivity()));
//        getBooldGroup();
        //  getCategoryApi();
//        Get_Country_Master_API();
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty()) {
                    getStrings();

                    NewProfileActivity.viewPager.setCurrentItem(1);


                }
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (i == R.id.rb_male) {
                            STUD_GENDER = 1 + "";
                        }
                        if (i == R.id.rb_female) {
                            STUD_GENDER = 2 + "";
                        }
                    }
                });
            }
        });

        // Get_Country_Master_API();

        get_student_profile_detail_atmiya_api();


        NewProfileActivity.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {


                    if (isEmpty()) {

                        IS_STEP_ONE_CLEAR = true;
                    } else {
                        System.out.println("gone");
                        IS_STEP_ONE_CLEAR = false;

//                        DialogUtils.showDialog(getActivity(), getString(R.string.app_name), "Please Fill Required Fields", new DialogUtils.DailogCallBackOkButtonClick() {
//                            @Override
//                            public void onDialogOkButtonClicked() {
//                                NewProfileActivity.viewPager.setCurrentItem(0);
//                            }
//                        });
                    }

                }


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        return view;

    }

    public static String STUD_BIRTH_PLACE = "", STUD_RELIGION = "",Stud_fatherOccupation = "",Stud_motherOccupation = "",
            STUD_NATION = "", STUD_BDATE = "", STUD_BLOOD = "", STUD_CATEGORY = "", STUD_GENDER = "1", STUD_AADHAR = "";
    public static int SELECTEDGENDER = 0;


    private void getStrings() {

        STUD_BIRTH_PLACE = edtbirthplace.getText().toString().trim();

        System.out.println(STUD_BIRTH_PLACE);
        STUD_RELIGION = SELCTED_RELIGION;
        Stud_fatherOccupation = edtfathersoccupation.getText().toString().trim();
        Stud_motherOccupation = edtmotheroccupation.getText().toString().trim();
        System.out.println("get String done religion" + STUD_RELIGION);
        STUD_BDATE = edtbirthdate.getText().toString().trim();

        STUD_BLOOD = SELECTED_BLOOD_GORUP;
        STUD_CATEGORY = SELECTED_CATEGORY;

        STUD_NATION = SELCTED_NATIONALITY;
        STUD_AADHAR = edtadharno.getText().toString().trim();


    }

    public static String ADMISSTION_NO = "";


    private void validateDate(String currentDate, String selectedDate) {

    }

    public static String PROFILE_PHOTO = "";
    public static String PROFILE_SIGN = "";
    public static String FILE_PHOTO = "";
    public static String FILE_SIGN = "";

    private void get_student_profile_detail_atmiya_api() {

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String URLs = URl.get_student_profile_detail_atmiya + "&stud_id=" + String.valueOf(storage.read("stud_id", 3)) +
                "&year_id=" +
                String.valueOf(storage.read("swd_year_id", 3)) +

                "&school_id=" +

                String.valueOf(storage.read("ac_id", 3)) + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("get_student_profile_detail_atmiya_api    " + URLs + "");


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("thisstudentresponse", response.toString());

                if (response.length() > 5) {

                    Gson gson = new Gson();

                    studenDetailsPojo = gson.fromJson(response, StudenDetailsPojo.class);
                    //final List<String> studn = new ArrayList<>();
                    /*{"stud_id":10,"name":"CHOVATIYA JANKI NARESHBHAI","ac_full_name":"Bhagwan Mahavir College of Computer Application (BMU)","dm_full_name":"BMU M.Sc.IT Integrated","course_fullname":"M.Sc.IT (Integrated)","sm_name":"BMU MSc IT(Int.) Sem 2","stud_enrollment_no":"19020406008","stud_admission_no":"19020406008","stud_admission_year":null,"gen_name":"FEMALE","blood_group":"O+","category_name":"OPEN","admission_type":"REGULAR","Fee_Type":"","Student_Quata":"","Stud_Shift":"","Stud_Current_Address":null,"city_name":"Surat","state_name":"GUJARAT","country_name":"INDIA","stud_land_line_no":null,"religion_name":"HINDU","dvm_name":"A","bch_name":"1","swd_roll_no":8,"stud_internet_username":"","stud_internet_password":"","stud_surname":"CHOVATIYA","stud_name":"JANKI","stud_father_name":"NARESHBHAI","Stud_mobile_no":"9712114408","Stud_email":"JANUCHOVATIYA1@GMAIL.COM","stud_fathers_name":"NARESHBHAI","Stud_father_Occupation":"","Stud_father_mobile_no":"9725967488","stud_mothers_name":"MEENABEN","Stud_Mother_Occupation":"","Stud_mother_Mobile_no":"","Stud_Address":"null","Stud_city":2,"stud_district":3,"Stud_state":1,"Stud_pin_no":"395006","Stud_Country":15182,"stud_birth_place":"SURAT","Stud_religion":1,"stud_bdate":"21/11/2001","stud_blood_group":7,"stud_category":1,"Stud_gender":2,"stud_adhar_no":"12345678917777777","Stud_photo":"http://bmef.icrp.in/cms/Images/stud_photo/19020406008.jpg","stud_signature":"http://bmef.icrp.in/cms/Images/Stud_signature/19020406008.jpg","profile_completed":1}*/
//
                    edtName.setText(studenDetailsPojo.getStud_surname());
                    edtmiddlename.setText(studenDetailsPojo.getStud_name());
                    edtfathersName.setText(studenDetailsPojo.getStud_father_name());
                    edtfathersoccupation.setText(studenDetailsPojo.getStud_father_Occupation() + "");
                    edtmotheroccupation.setText(studenDetailsPojo.getStud_Mother_Occupation());
                    edtbirthplace.setText(studenDetailsPojo.getStud_birth_place());
                    edtbirthdate.setText(studenDetailsPojo.getStud_bdate());
                    edtadharno.setText(studenDetailsPojo.getStud_adhar_no());
                    ADMISSTION_NO = studenDetailsPojo.getStud_admission_no() + "";
                    PROFILE_PHOTO = studenDetailsPojo.getStud_photo() + "";
                    PROFILE_SIGN = studenDetailsPojo.getStud_signature()+"";
                    FILE_SIGN = PROFILE_SIGN+"";
                    FILE_PHOTO = PROFILE_PHOTO+"";

                    //  edtreligion.setText(studenDetailsPojo.getReligion_name() + "");
                    if (studenDetailsPojo.getStud_gender() == 1) {
                        rbFemale.setChecked(true);
                        SELECTED_GENDER = "FEMALE";
                        STUD_GENDER = 2 + "";

                    } else {
                        rbMale.setChecked(true);
                        STUD_GENDER = 1 + "";

                        SELECTED_GENDER = "MALE";
                    }

                    edtmothername.setText(studenDetailsPojo.getStud_mothers_name());
                    edtfathersoccupation.setText(studenDetailsPojo.getStud_father_Occupation());
                    edtmotheroccupation.setText(studenDetailsPojo.getStud_Mother_Occupation());
                    getStrings();

                    category_id = studenDetailsPojo.getStud_category() + "";
                    //bloodgroup_id = studenDetailsPojo.getBlood_group() + "";
                    bloodgroup_id = studenDetailsPojo.getStud_blood_group() + "";


                    country_id = studenDetailsPojo.getStud_Country() + "";
                    religion_id = studenDetailsPojo.getStud_religion() + "";

                   // district_id = studenDetailsPojo.getStud_district() + "";
                    System.out.println("cat Id.... " + category_id + "");
                    System.out.println("bloodgroup_id Id.... " + bloodgroup_id + "");
                    System.out.println("country_id Id.... " + country_id + "");
                    getBooldGroup();
                    getCategoryApi();
                    Get_Religion_Master_API();
                    Get_Country_Master_API();

                    //adapter_bloodgroup = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, bloodgroup_list);
                    // spin_bloodgroup.setAdapter(adapter_bloodgroup);


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);


    }

    int country_selection = 0;

    private void Get_Country_Master_API() {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String URLs = URl.Get_Country_Master_API + "institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_Country_Master_API    " + URLs + "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                country_list = new ArrayList<>();
                coluntry_id_list = new ArrayList<>();

                if (response.length() > 0) {
                    Log.e("getcountry", response.toString());

                    Gson gson = new Gson();

                    countryPojo = gson.fromJson(response, CountryPojo.class);

                    country_list.add("Select Country");
                    coluntry_id_list.add("00");
//

                    System.out.println("country_id "+country_id+"");

                    for (int i = 0; i < countryPojo.getTable().size(); i++) {

                        country_list.add(countryPojo.getTable().get(i).getCountry_name());
                        coluntry_id_list.add(countryPojo.getTable().get(i).getCountry_id() + "");


                        // Bl_selection=i+1  ;
                        //   System.out.println(i+1);
                        if (country_id.contentEquals(countryPojo.getTable().get(i).getCountry_id() + "")) {
                            country_selection = i + 1;
                            //  spin_category.setSelection(i + 1);

                        }


                    }

                    adapter_nationality = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, country_list);
                    countrtySpinner.setAdapter(adapter_nationality);
                    countrtySpinner.setSelection(country_selection);


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(stringRequest);


    }

    int religion_selection = 0;

    private void Get_Religion_Master_API() {

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String URLs = URl.Get_Religion_Master_API + "institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_Religion_Master_API    " + URLs + "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("religion", response.toString());


                religion_list = new ArrayList<>();


                if (response.length() > 0) {
                    Log.e("Get_Religion_Master_API", response.toString());

                    Gson gson = new Gson();

                    religionPojo = gson.fromJson(response, ReligionPojo.class);

                    religion_list.add("Select Religion");
                    religion_id_list.add("00");
//

                    for (int i = 0; i < religionPojo.getTable().size(); i++) {

                        religion_list.add(religionPojo.getTable().get(i).getReligion_name());

                        religion_id_list.add(religionPojo.getTable().get(i).getReligion_id() + "");


                        // Bl_selection=i+1  ;
                        //   System.out.println(i+1);
                        if (religion_id.contentEquals(religionPojo.getTable().get(i).getReligion_id() + "")) {
                            religion_selection = i + 1;
                            //  spin_category.setSelection(i + 1);
                            System.out.println("blood id" + i + 1);
                        }


                    }

                    adapter_religion = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, religion_list);
                    spin_religion.setAdapter(adapter_religion);
                    spin_religion.setSelection(religion_selection);


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(stringRequest);


    }

    int Bl_selection = 0;

    private void getBooldGroup() {

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String URLs = URl.Get_blood_group_master_API + "institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_blood_group_master_APIs    " + URLs + "");


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                bloodgroup_list = new ArrayList<>();
                bloodgroup_id_list = new ArrayList<>();
                if (response.length() > 5) {


                    Gson gson = new Gson();

                    bloodGroupPojo = gson.fromJson(response, BloodGroupPojo.class);

                    bloodgroup_list.add("Select Blood group");
                    bloodgroup_id_list.add("00");
//

                    for (int i = 0; i < bloodGroupPojo.getTable().size(); i++) {

                        bloodgroup_list.add(bloodGroupPojo.getTable().get(i).getBlood_group());
                        bloodgroup_id_list.add(bloodGroupPojo.getTable().get(i).getBlood_id() + "");

                        //  if (bloodgroup_id.contentEquals(bloodGroupPojo.getTable().get(i).getBlood_id() + "")){
                        // Bl_selection=i+1  ;
                        //   System.out.println(i+1);
                        if (bloodgroup_id.contentEquals(bloodGroupPojo.getTable().get(i).getBlood_id() + "")) {
                            Bl_selection = i + 1;
                            //  spin_category.setSelection(i + 1);
                            System.out.println("blood id" + i + 1);
                        }

                        // }


                    }

                    adapter_bloodgroup = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, bloodgroup_list);
                    spin_bloodgroup.setAdapter(adapter_bloodgroup);
                    spin_bloodgroup.setSelection(Bl_selection);


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(stringRequest);


    }


    private void getCategoryApi() {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());


        String URLs = URl.Get_Category_Master_API + "institute_id=" + String.valueOf(storage.read("intitute_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        System.out.println("Get_category_master_APIs    " + URLs + "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Log.e("GetI",response.toString());
                // spin_category

                category_id_list = new ArrayList<>();
                category_list = new ArrayList<>();
                if (response.length() > 5) {

                    Gson gson = new Gson();

                    categoryPojo = gson.fromJson(response, CategoryPojo.class);

                    category_list.add("Select category");
                    category_id_list.add("0");

//
                    System.out.println("ada " + category_id + "");
                    for (int i = 0; i < categoryPojo.getTable().size(); i++) {

                        category_list.add(categoryPojo.getTable().get(i).getCategory_name());
                        category_id_list.add(categoryPojo.getTable().get(i).getCategory_id() + "");

                        // System.out.println("categoryPojo.getTable().get(i).getCategory_id() + \"\" " + categoryPojo.getTable().get(i).getCategory_id() + "");
                        if (category_id.contentEquals(categoryPojo.getTable().get(i).getCategory_id() + "")) {
                            selectedcategory = i + 1;
                            //  spin_category.setSelection(i + 1);
                            System.out.println(i + 1);
                        }


                    }

                    adapter_category = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, category_list);
                    spin_category.setAdapter(adapter_category);
                    spin_category.setSelection(selectedcategory);


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(stringRequest);


    }

    public boolean isEmpty() {


        System.out.println("SELECTED_BLOOD_GORUP  " + SELECTED_BLOOD_GORUP + "");
        if (edtName.getText().toString().trim().contentEquals("") || edtName.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Student Name");
            return false;
        } else if (edtmiddlename.getText().toString().trim().contentEquals("") || edtmiddlename.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Middle Name");
            return false;
        } else if (edtfathersName.getText().toString().contentEquals("") || edtfathersName.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Father's Name");

            return false;
        }else if (edtfathersoccupation.getText().toString().contentEquals("") || edtfathersoccupation.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Father's Occupation");

            return false;
        }
        else if (edtmotheroccupation.getText().toString().contentEquals("") || edtmotheroccupation.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Mothers's Occupation");

            return false;
        }
        else if (edtbirthdate.getText().toString().contentEquals("") || edtbirthdate.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Date of Birth");

            return false;
        } else if (edtbirthplace.getText().toString().contentEquals("") || edtbirthplace.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Birth place");

            return false;
        } else if (SELECTED_GENDER == null || SELECTED_GENDER.contentEquals("")) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Select Gender ");


            return false;
        } else if (SELECTED_BLOOD_GORUP == null || SELECTED_BLOOD_GORUP.contentEquals("")) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Select blood group");


            return false;
        } else if (SELECTED_CATEGORY == null || SELECTED_CATEGORY.contentEquals("")) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Select category type");


            return false;
        } else if (edtadharno.getText().toString().contentEquals("") || edtadharno.getText().toString().trim().length() > 13) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Valid Aadhaar Card No");

            return false;
        } else if (SELCTED_NATIONALITY == null || SELCTED_NATIONALITY.contentEquals("")) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Select Country ");


            return false;
        } else if (SELCTED_RELIGION == null || SELCTED_RELIGION.contentEquals("")) {
            System.out.println("SELCTED_RELIGION @@@@@@@@@@@@@@@@ " + SELCTED_RELIGION);


            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Select Religion");


            return false;

        }


        return true;
    }





   /* private void AddLeave()
    {

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String is_emergency = "";

        if (cbemegency.isChecked())
        {
            is_emergency = "1";
        } else {
            is_emergency = "0";
        }
        String load_adjust_ID = "";


        if (rg.getCheckedRadioButtonId() == -1)
        {
            load_adjust_ID = "0";
        }
        if (rbyes.isChecked() == true)
        {
            load_adjust_ID = "1";
        }
        if (rbno.isChecked())
        {
            load_adjust_ID = "0";
        }
        if (rbnotapplicable.isChecked())
        {
            load_adjust_ID = "2";
        }


        String leave_IDD = "";
        if (is_update)
        {
            leave_IDD = ID;
        }
        else
        {
            leave_IDD = "0";
        }

        System.out.println("from date in API :::: " + edtfromdate.getText().toString().trim());
        String date1 = edtfromdate.getText().toString().trim();
        String date2 = edttodate.getText().toString().trim();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mmaa");
        DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null,dateOut = null;
        String IN_date_time="",Out_date_time = "";
        try
        {
            //Converting the input String to Date
            date = df.parse(date1);
            //Changing the format of date and storing it in String
            IN_date_time = outputformat.format(date);
            //Displaying the date
            System.out.println("IN_date_time :::::::"+IN_date_time);
        }
        catch(ParseException pe)
        {
            pe.printStackTrace();
        }
        try
        {
            //Converting the input String to Date
            dateOut = df.parse(date2);
            //Changing the format of date and storing it in String
            Out_date_time = outputformat.format(dateOut);
            //Displaying the date
            System.out.println("Out_date_time :::::::"+Out_date_time);
        }
        catch(ParseException pe)
        {
            pe.printStackTrace();
        }

        DialogUtils.showProgressDialog(AddLeaveAcivity.this, "");

        String url1 = URLS.Employee_leave_application_insert + "&leave_id=" + leave_IDD + "&emp_id=" + mySharedPrefereces.getEmpID() + "&leave_type=" + leave_ID + "&from_date=" + IN_date_time+ "&to_date=" + Out_date_time + "&remark=" + edtremark.getText().toString().trim() + "&reason=" + Reason_ID + "&load_adjusted=" + load_adjust_ID + "&address_while_on_leave=" + edtadd.getText().toString().trim() + "&contact_no=" + edtconno.getText().toString().trim() + "&leave_days=" + edtday.getText().toString().trim() + "&emergency_leave=" + is_emergency + "&user_id=" + mySharedPrefereces.getUserID() + "&ip_address=" + "1" + "&leave_balance=" + edtleavebalance.getText().toString().trim() + "";

        url1 = url1.replace(" ", "%20");

        System.out.println("Employee_leave_application_insert URL " + url1 + "");
        StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response1)
            {
                DialogUtils.hideProgressDialog();

                System.out.println("response of Employee_leave_application_insert !!!!!!!!!!! " + response1);
                response1 = response1 + "";


//                 System.out.println("sucess response Get_miss_punch_detail !!!!!!!!!!!!!!!!!!!" + response + "");

                if (response1.length() > 5)
                {

                    response1 = "{\"Data\":" + response1 + "}";
                    Gson gson = new Gson();


//                    EmpLeaveBalancePojo empLeaveBalancePojo = gson.fromJson(response, EmpLeaveBalancePojo.class);
                    AddMissPunchPojo addMissPunchPojo = gson.fromJson(response1, AddMissPunchPojo.class);


                    if (addMissPunchPojo != null)
                    {
                        if (addMissPunchPojo.getData().size() > 0)
                        {

                            DialogUtils.Show_Toast(AddLeaveAcivity.this, addMissPunchPojo.getData().get(0).getMsg());

                            if (addMissPunchPojo.getData().get(0).getMsg().compareToIgnoreCase("Leave Appliation Inserted") == 0 || addMissPunchPojo.getData().get(0).getMsg().compareToIgnoreCase("Leave Appliation Updated") == 0)
                            {
                                Intent intent = new Intent(AddLeaveAcivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {

                            }

//                            edtleavebalance.setText(empLeaveBalancePojo.getData().get(0).getBalance() + "");


                        }
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtils.Show_Toast(AddLeaveAcivity.this, "Please Try Again Later");
                DialogUtils.hideProgressDialog();
                System.out.println("errorrrrrrrrrr " + error);
                System.out.println("errorrrrrrrrrr in api" + error.networkResponse);
            }
        });
        request1.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue1.add(request1);
    }
*/


}





