package com.example.rajatiit.admin_app.activities.userActivities.teacheractivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.rajatiit.admin_app.Login;
import com.example.rajatiit.admin_app.R;
import com.firebase.client.Firebase;

import java.util.Arrays;
import java.util.List;

public class TeacherMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final List<String> WeekDays = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY","THURSDAY","FRIDAY");

    private SharedPreferences sp;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teacher);
        Firebase.setAndroidContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_app_bar_content_toolbar);
        setSupportActionBar(toolbar);
        setViewPager();
        setTabLayout();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.teacherDrawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.teacherNav);
        navigationView.setNavigationItemSelectedListener(this);

        View view = getLayoutInflater().inflate(R.layout.main_nav_header, navigationView);
        TextView user = (TextView) view.findViewById(R.id.main_nav_header_UserName);

        sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        user.setText(sp.getString(Login.NAME, "Noobie"));
    }

    private void setViewPager(){
        viewPager = (ViewPager)findViewById(R.id.main_app_bar_content_viewpager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }
    private void setTabLayout(){
        TabLayout tabLayout = (TabLayout)findViewById(R.id.main_app_bar_content_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(Login.TEACHER_LOGIN_CHECK, false);
                editor.commit();
                Intent intentLogout = new Intent(this, Login.class);
                startActivity(intentLogout);
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.teacherDrawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.teacherDrawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new TeacherClassroomFragment();
        }

        @Override
        public int getCount() {
            return WeekDays.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return WeekDays.get(0);
                case 1:
                    return WeekDays.get(1);
                case 2:
                    return WeekDays.get(2);
                case 3:
                    return WeekDays.get(3);
                case 4:
                    return WeekDays.get(4);
                case 5:
                    return WeekDays.get(5);
                default:
                    return WeekDays.get(0);
            }
        }
    }
}
