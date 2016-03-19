package com.example.yi.tablayoutdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Fragment doctorRecoder;
    private Fragment attention;
    private Fragment followUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] titles = new String[]{"一二三五", "二三", "五六"};
        tabLayout = (TabLayout) findViewById(R.id.my_doctor_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.my_doctor_view_pager);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    if (doctorRecoder == null) {
                        doctorRecoder = new MainBaseFragment();
                    }
                    return doctorRecoder;
                } else if (position == 1) {
                    if (attention == null) {
                        attention = new MainBaseFragment();
                    }
                    return attention;
                } else if (position == 2) {
                    if (followUpFragment == null) {
                        followUpFragment = new MainBaseFragment();
                    }
                    return followUpFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
