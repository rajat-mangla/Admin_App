package com.example.rajatiit.admin_app.activities.userActivities.adminActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rajatiit.admin_app.activities.userActivities.adminActivity.AdminFragment.AdminSlotsFragment;
import com.example.rajatiit.admin_app.dataclasses.Database;
import com.example.rajatiit.admin_app.Login;
import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.timetablehandler.TimeTable;
import com.example.rajatiit.admin_app.activities.intefaces.batchInterface.BatchInterface;
import com.example.rajatiit.admin_app.activities.intefaces.classroomInterface.ClassroomInterface;
import com.example.rajatiit.admin_app.activities.intefaces.roomInterface.RoomInterface;
import com.example.rajatiit.admin_app.activities.intefaces.teacherInterface.TeacherInterface;
import com.firebase.client.Firebase;

import java.util.Arrays;
import java.util.List;

public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final List<String> WeekDays = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY","THURSDAY","FRIDAY");
    private ViewPager viewPager;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Firebase.setAndroidContext(this);


       /* *//*
            Getting Data From DataBase
         *//*
        progressDialog = ProgressDialog.show(this,
                getResources().getString(R.string.GETTING_DATA),"Please Wait");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAllUsersData();
                getInstituteData();
                getTimeTable();
            }
        }).start();
*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_app_bar_content_toolbar);
        setSupportActionBar(toolbar);
        setViewPager();
        setTabLayout();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_admin_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = getLayoutInflater().inflate(R.layout.main_nav_header, navigationView);
        TextView user = (TextView) view.findViewById(R.id.main_nav_header_UserName);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
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
        Intent intent;
        switch (item.getItemId()){
            case R.id.activity_main_drawer_courses :
                intent = new Intent(this, ClassroomInterface.class);
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_teachers:
                intent = new Intent(this, TeacherInterface.class);
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_batches :
                intent = new Intent(this,BatchInterface.class);
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_rooms :
                intent = new Intent(this,RoomInterface.class);
                startActivity(intent);
                break;
            case R.id.activity_main_drawer_about :
                Toast.makeText(this,"IN PROGRESS",Toast.LENGTH_SHORT).show();
                break;

            case R.id.generate_time_table :
                final ProgressDialog progressDialog = ProgressDialog.show(this, null, "Generating");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TimeTable timeTable = new TimeTable();
                        timeTable.generateTimeSlots();

                        Database.sendTimeTable(timeTable);

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            Log.e("here","error in sleep");
                        }
                        progressDialog.dismiss();
                    }
                }).start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setViewPager();
                        setTabLayout();
                    }
                });
                break;
            case R.id.logout:
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(Login.ADMIN_LOGIN_CHECK, false);
                editor.commit();
                Intent intentLogout = new Intent(this, Login.class);
                startActivity(intentLogout);
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_admin_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_admin_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*private void getAllUsersData(){
        DatabaseReference reference = Database.database.getReference(UserStorage.USER_STORAGE_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserStorage userStorage = dataSnapshot.getValue(UserStorage.class);
                Toast.makeText(getBaseContext(),"Getting Users Data",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Error in connecting ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInstituteData(){
        DatabaseReference reference = Database.database.getReference(Institute.INSTITUTE_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Institute institute = dataSnapshot.getValue(Institute.class);
                Toast.makeText(getBaseContext(),"Getting Institute Data",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(),"Error in connecting ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTimeTable(){
        DatabaseReference reference = Database.database.getReference(TimeTable.TIME_TABLE_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TimeTable timeTable = dataSnapshot.getValue(TimeTable.class);
                Toast.makeText(getBaseContext(),"Getting Time Table",Toast.LENGTH_SHORT).show();

                *//*try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Log.e("here","hdakdakda");
                }*//*
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/


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
            /*switch (position){
                case 1:
                    return new AdminSlotsFragment();
                case 2:
                    return new AdminTuesdayFragment();
                case 3:
                    return new AdminWednesdayFragment();
                case 4:
                    return new AdminThursdayFragment();
                case 5:
                    return new AdminFridayFragment();
                default:
                    return new AdminSlotsFragment();
            }*/
            return new AdminSlotsFragment();
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
