package com.infinity.infoway.bmef.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.infinity.infoway.bmef.R;
import com.infinity.infoway.bmef.fragment.ContactDetailsFragment;
import com.infinity.infoway.bmef.fragment.DaysFragment;
import com.infinity.infoway.bmef.fragment.EducationFrament;
import com.infinity.infoway.bmef.fragment.PhotSignFragment;
import com.infinity.infoway.bmef.fragment.ProfileFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewProfileActivity extends AppCompatActivity  {


    private TabLayout tabLayout;
    public static ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);
        tabLayout = findViewById(R.id.tabsprofile);
        viewPager = findViewById(R.id.viewpagerprofile);

        viewPagerAdapter  = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);







    }




//    public  class ViewPagerAdapter extends FragmentPagerAdapter {
    public  class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0)
            {
                fragment = new ProfileFragment();
            }
            else if (position == 1)
            {
                fragment = new ContactDetailsFragment();
            }
            else if (position == 2)
            {
                fragment = new PhotSignFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0)
            {
                title = "personal details";
            }
            else if (position == 1)
            {
                title = "contact details";
            }
            else if (position == 2)
            {
                title = "Photo/Sign";
            }

            return title;
        }



    }






    /*private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        // removied string title
        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
           // mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);

        }

    }*/
}
