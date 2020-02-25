package com.infinity.infoway.bmef.fragment;

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
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.infinity.infoway.bmef.CommonCls.DialogUtils;
import com.infinity.infoway.bmef.CommonCls.URl;
import com.infinity.infoway.bmef.CommonCls.Validations;
import com.infinity.infoway.bmef.HrAppPojo.BloodGroupPojo;
import com.infinity.infoway.bmef.HrAppPojo.CityPojo;
import com.infinity.infoway.bmef.HrAppPojo.CountryPojo;
import com.infinity.infoway.bmef.HrAppPojo.DistrictPojo;
import com.infinity.infoway.bmef.HrAppPojo.StatePojo;
import com.infinity.infoway.bmef.HrAppPojo.StudenDetailsPojo;
import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.activity.NewProfileActivity;
import com.infinity.infoway.bmef.app.DataStorage;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactDetailsFragment extends Fragment {
    RequestQueue queue;
    DataStorage dataStorage;
    Bundle bundle;
    ArrayAdapter<String> adapter_nationality;
    DataStorage storage;
    Button btnnext;
    EditText txtmobilenouser, txtfathermobileno, txtemail, txtlandmark, txtstate, txtpincode, txtcountry, txtparentaddress;
    SearchableSpinner spin_state, spin_district, city_spin, spin_countrycontact;
    ArrayAdapter<String> adapter_state, adapter_district, adapter_city;
    StatePojo statePojo;
    StudenDetailsPojo studenDetailsPojo;
    DistrictPojo districtPojo;
    CityPojo cityPojo;
    String district_id = "0";
    String state_id = "0";
    CountryPojo countryPojo;
    String country_id = "0";
    String city_id = "0";
    Date date11;
    int selectedcategory = 0;
    int selectedstate = 0;
    int selectedcity = 0;
    List<String> country_list = new ArrayList<>();
    List<String> coluntry_id_list = new ArrayList<>();
    List<String> city_id_list = new ArrayList<>();
    List<String> state_id_list = new ArrayList<>();
    ;
    List<String> state_list = new ArrayList<>();
    ;
    List<String> district_id_list = new ArrayList<>();
    List<String> city_list = new ArrayList<>();
    List<String> dis_list = new ArrayList<>();
    String SELECTED_CITY = "", SELECTED_STATE = "", SELECTED_DISTRICT = "", SELECTED_COUNTRY = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_details_form, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        btnnext = view.findViewById(R.id.btnnext);
        dataStorage = new DataStorage("Login_Detail", getActivity());
        txtmobilenouser = view.findViewById(R.id.txtmobilenouser);
        txtfathermobileno = view.findViewById(R.id.txtfathermobileno);
        txtparentaddress = view.findViewById(R.id.txtpresentaddress);
        spin_countrycontact = view.findViewById(R.id.spin_countrycontact);
        spin_countrycontact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_COUNTRY = coluntry_id_list.get(i);

                    STUD_NATION = SELECTED_COUNTRY;
                  //  STUD_NATION = SELECTED_COUNTRY;
                } else {
                    SELECTED_COUNTRY = "";
                    STUD_NATION = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                SELECTED_STATE = "00";
            }
        });



        txtemail = view.findViewById(R.id.txtemail);
        city_spin = view.findViewById(R.id.spin_city);
        city_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_CITY = city_id_list.get(i);

                    Log.e("SELECTED_CITY", SELECTED_CITY);


                } else {
                    SELECTED_CITY = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txtlandmark = view.findViewById(R.id.txtlandmark);
        txtstate = view.findViewById(R.id.txtstate);
        txtpincode = view.findViewById(R.id.txtpincode);
        txtcountry = view.findViewById(R.id.txtcountry);
        spin_state = view.findViewById(R.id.spin_state);

        spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_STATE = state_id_list.get(i);

                } else {
                    SELECTED_STATE = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin_district = view.findViewById(R.id.spin_district);
        spin_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_DISTRICT = district_id_list.get(i);

                } else {
                    SELECTED_DISTRICT = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                SELECTED_DISTRICT = "";
            }
        });


        Get_District_Master_API();
        Get_State_Master_API();
        getCityApi();


        get_student_profile_detail_atmiya_api();


        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEmpty()) {

                    getStrings();
                    System.out.println("111111111111111111111111");
                    NewProfileActivity.viewPager.setCurrentItem(2);

                }


                ;


            }
        });
        NewProfileActivity.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 2) {

                    if(ProfileFragment.IS_STEP_ONE_CLEAR) {


                        if (isEmpty()) {

                            ProfileFragment.IS_STEP_TWO_CLEAR = true;
                        }
                        else{
                            ProfileFragment.IS_STEP_TWO_CLEAR = false;

//                            DialogUtils.showDialog(getActivity(), getString(R.string.app_name), "Please Fill Required Fields", new DialogUtils.DailogCallBackOkButtonClick() {
//                                @Override
//                                public void onDialogOkButtonClicked() {
//                                    NewProfileActivity.viewPager.setCurrentItem(0);
//                                }
//                            });
                        }


                    }else {
                        System.out.println("gone");
                        ProfileFragment.IS_STEP_TWO_CLEAR = false;

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

    public static String STUD_EMAIL = "", STUD_FATHER_MOBILE = "", STUD_ADDRESS = "", STUD_CITY = "", STUD_DIST = "", STUD_STATE="", STUD_PINCODE = "", STUD_NATION="", STUD_MOBILE = "";


    private void getStrings() {
        STUD_EMAIL = txtemail.getText().toString().trim();
        STUD_FATHER_MOBILE = txtfathermobileno.getText().toString().trim();
        STUD_ADDRESS = txtparentaddress.getText().toString().trim();


        STUD_CITY = SELECTED_CITY;
        System.out.println("city done getStrings" + STUD_CITY);
        STUD_DIST = SELECTED_DISTRICT;
        System.out.println("dis done getStrings" + STUD_DIST);
        STUD_STATE = SELECTED_STATE;
        System.out.println("state done getStrings" + STUD_STATE);


        STUD_PINCODE = txtpincode.getText().toString().trim();
        STUD_NATION = SELECTED_COUNTRY;
        STUD_MOBILE = txtmobilenouser.getText().toString().trim();

    }

    //Get_District_Master_API

    //
    private void get_student_profile_detail_atmiya_api() {

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String URLs = URl.get_student_profile_detail_atmiya + "&stud_id=" + String.valueOf(dataStorage.read("stud_id", 3)) +
                "&year_id=" +
                String.valueOf(dataStorage.read("swd_year_id", 3)) +

                "&school_id=" +

                String.valueOf(dataStorage.read("ac_id", 3)) + "";

        URLs = URLs.replace(" ", "%20");
        System.out.println("get_student_profile_detail_atmiya_api    " + URLs + "");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("this", response.toString());

                if (response.length() > 5) {

                    Gson gson = new Gson();

                    studenDetailsPojo = gson.fromJson(response, StudenDetailsPojo.class);
                    //final List<String> studn = new ArrayList<>();

//
                    txtmobilenouser.setText(studenDetailsPojo.getStud_mobile_no());
                    txtfathermobileno.setText(studenDetailsPojo.getStud_father_mobile_no());

                    txtparentaddress.setText(studenDetailsPojo.getStud_Address() + "");
                    txtemail.setText(studenDetailsPojo.getStud_email());
                    // edtbirthplace.setText(studenDetailsPojo.ge);
                    txtpincode.setText(studenDetailsPojo.getStud_pin_no());


                    district_id = studenDetailsPojo.getStud_district() + "";
                    state_id = studenDetailsPojo.getStud_state() + "";
                    city_id = studenDetailsPojo.getStud_city() + "";
                    country_id = studenDetailsPojo.getStud_Country() + "";
                    System.out.println("cat Id.... " + district_id + "");
                    System.out.println("bloodgroup_id Id.... " + state_id + "");
                    System.out.println("bloodgroup_id Id.... " + city_id + "");
                    System.out.println("country_id Id.... " + country_id + "");
                    //   getBooldGroup();
                    //  getCategoryApi();
                    Get_District_Master_API();
                    Get_State_Master_API();
                    getCityApi();
                    Get_Country_Master_API();
                    getStrings();


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

int selectedDistrict  = 0;
    private void Get_District_Master_API() {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        dis_list = new ArrayList<>();
        district_id_list = new ArrayList<>();
        String URLs = URl.Get_District_Master_API + "institute_id=" + String.valueOf(dataStorage.read("intitute_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        // System.out.println("Get_District_Master_API    " + URLs + "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Log.e("Get_District_Master_API",response.toString());
                if (response.length() > 5) {

                    Gson gson = new Gson();

                    districtPojo = gson.fromJson(response, DistrictPojo.class);

                    dis_list.add("Select District");
//

                    for (int i = 0; i < districtPojo.getTable().size(); i++) {

                        dis_list.add(districtPojo.getTable().get(i).getDM_name());
                        district_id_list.add(districtPojo.getTable().get(i).getDM_id() + "");
                       // System.out.println("categoryPojo.getTable().get(i).getCategory_id() + \"\" " + districtPojo.getTable().get(i).getDM_id() + "");
                        if (district_id.contentEquals(districtPojo.getTable().get(i).getDM_id() + "")) {
                            selectedDistrict = i + 1;
                            //  spin_category.setSelection(i + 1);
                            System.out.println(i + 1);
                        }


                    }

                    adapter_district = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, dis_list);
                    spin_district.setAdapter(adapter_district);
                    spin_district.setSelection(selectedDistrict);


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

        String URLs = URl.Get_Country_Master_API + "institute_id=" + String.valueOf(dataStorage.read("intitute_id", 3)) + "";
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

                    for (int i = 0; i < countryPojo.getTable().size(); i++) {

                        country_list.add(countryPojo.getTable().get(i).getCountry_name());
                        coluntry_id_list.add(countryPojo.getTable().get(i).getCountry_id() + "");


                        //if (country_id.contentEquals(countryPojo.getTable().get(i).getCountry_id() + "")) {
                            // Bl_selection=i+1  ;
                            //   System.out.println(i+1);
                            if (country_id.contentEquals(countryPojo.getTable().get(i).getCountry_id() + "")) {
                                country_selection = i + 1;

                                //STUD_NATION = SELECTED_COUNTRY=countryPojo.getTable().get(i).getCountry_id() + "";

                                //  spin_category.setSelection(i + 1);

                            }

                        //}


                    }

                    adapter_nationality = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, country_list);
                    spin_countrycontact.setAdapter(adapter_nationality);
                //    spin_countrycontact.setSelection(country_selection);


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(stringRequest);


    }


    private void Get_State_Master_API() {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String URLs = URl.Get_State_Master_API + "institute_id=" + String.valueOf(dataStorage.read("intitute_id", 3)) + "";
        URLs = URLs.replace(" ", "%20");
        state_list = new ArrayList<>();
        state_id_list = new ArrayList<>();
        // System.out.println("Get_State_Master_API    " + URLs + "");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Log.e("Get_State_Master_API",response.toString());
                if (response.length() > 5) {

                    Gson gson = new Gson();

                    statePojo = gson.fromJson(response, StatePojo.class);

                    state_list.add("Select State");
                    state_id_list.add("00");
//

                    for (int i = 0; i < statePojo.getTable().size(); i++) {

                        state_list.add(statePojo.getTable().get(i).getState_name());
                        state_id_list.add(statePojo.getTable().get(i).getState_id() + "");
                       // System.out.println("categoryPojo.getTable().get(i).getCategory_id() + \"\" " + statePojo.getTable().get(i).getState_id() + "");
                        if (state_id.contentEquals(statePojo.getTable().get(i).getState_id() + "")) {
                            selectedstate = i + 1;
                            //  spin_category.setSelection(i + 1);
                            System.out.println(i + 1);
                        }


                    }

                    adapter_state = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, state_list);
                    spin_state.setAdapter(adapter_state);
                    spin_state.setSelection(selectedstate);


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        queue.add(stringRequest);


    }


    private void getCityApi() {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String URLs = URl.Get_City_Master_API + "institute_id=" + String.valueOf(dataStorage.read("intitute_id", 3)) + "";
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
                    city_id_list.add("00");
//

                    for (int i = 0; i < cityPojo.getTable().size(); i++) {

                        city_list.add(cityPojo.getTable().get(i).getCity_name());
                        city_id_list.add(cityPojo.getTable().get(i).getCity_id() + "");
                     //   System.out.println("categoryPojo.getTable().get(i).getCategory_id() + \"\" " + cityPojo.getTable().get(i).getCity_id() + "");
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


        queue.add(stringRequest);


    }


    public boolean isEmpty() {

        if (txtmobilenouser.getText().toString().trim().contentEquals("") || txtmobilenouser.getText().toString().trim().length() < 10|| txtmobilenouser.getText().toString().trim().length() > 10 ) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Valid  Mobile number");
            return false;
        } else if (txtfathermobileno.getText().toString().trim().contentEquals("") || txtfathermobileno.getText().toString().trim().length() < 10|| txtfathermobileno.getText().toString().trim().length() > 10) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Valid Father`s Mobile No ");
            return false;
        }  else if (txtparentaddress.getText().toString().contentEquals("") || txtparentaddress.getText().toString().trim().length() < 10) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Valid Address");

            return false;
        }
        else if (txtemail.getText().toString().contentEquals("") || txtemail.getText().toString().trim().length() < 0|| !Validations.isValidEmail(txtemail.getText().toString().trim())) {
        //else if (txtemail.getText().toString().contentEquals("") || txtemail.getText().toString().trim().length() < 0) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Valid Email Address");

            return false;



        }
        else if (SELECTED_STATE == null || SELECTED_STATE.contentEquals("")) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Select State");

            return false;
        }
        else if (SELECTED_DISTRICT == null || SELECTED_DISTRICT.contentEquals("")) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "select District");

            return false;
        }

        else if (SELECTED_CITY == null || SELECTED_CITY.contentEquals("")) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Select City");

            return false;
        }
        else if (txtpincode.getText().toString().contentEquals("") || txtpincode.getText().toString().trim().length() < 3) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Enter Valid Pincode");

            return false;
        }
        else if (SELECTED_COUNTRY == null || SELECTED_COUNTRY.contentEquals("")) {
            DialogUtils.Show_Toast(getActivity().getApplicationContext(), "Select Country");

            return false;
        }




        return true;
    }
}
