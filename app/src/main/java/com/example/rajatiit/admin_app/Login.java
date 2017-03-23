package com.example.rajatiit.admin_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rajatiit.admin_app.dataclasses.Database;
import com.example.rajatiit.admin_app.dataclasses.users.BatchDetail;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Login extends AppCompatActivity {
    public static final String ADMIN_LOGIN_CHECK = "ADMIN_LOGGED_IN";
    public static final String TEACHER_LOGIN_CHECK = "TEACHER_LOGGED_IN";
    public static final String BATCH_LOGIN_CHECK = "BATCH_LOGGED_IN";
    public static final String NAME = "NAME";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String TAG = "rajatiit.admin_app";
    String usernameS;
    String passwordS;
    private EditText username;
    private EditText password;
    private Intent intentLoginAdmin;
    private Intent intentLoginTeacher;
    private Intent intentLoginBatch;
    private int userType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }
        catch (NullPointerException e){
            Log.e("In Login Activity","No Action Bar Found");
        }

        getAllUsersData();

        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        intentLoginAdmin = new Intent(this, Intermediate.class);
        intentLoginTeacher = new Intent(this, Intermediate.class);
        intentLoginBatch = new Intent(this, Intermediate.class);

        username.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username.setCursorVisible(true);
                    }
                }
        );

        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNetworkAvailable()) {
                            usernameS = username.getText().toString();
                            passwordS = password.getText().toString();


                            loginUser(usernameS, passwordS);
                        } else {
                            Toast.makeText(getBaseContext(), "No internet!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void selectUser(View view){
        switch(view.getId()){
            case R.id.admin:
                userType = 0;
                break;
            case R.id.teacher:
                userType = 1;
                break;
            case R.id.batch:
                userType = 2;
                break;
            default:
                userType = -1;
        }
    }

    private void loginUser(final String username, final String password) {
        class LoginUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (userType == 0 && !s.startsWith("none")) {
                    Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(ADMIN_LOGIN_CHECK, true);
                    editor.putString(NAME, s.substring(0,1).toUpperCase() + s.substring(1));
                    editor.putString(USERNAME, username);
                    editor.putString(PASSWORD, password);
                    editor.commit();
                    startActivity(intentLoginAdmin);
                    finish();
                }
                else if (userType == 1 && !s.startsWith("none")) {
                    Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(TEACHER_LOGIN_CHECK, true);
                    editor.putString(NAME, s.substring(0,1).toUpperCase() + s.substring(1));
                    editor.putString(USERNAME, username);
                    editor.putString(PASSWORD, password);
                    editor.commit();
                    startActivity(intentLoginTeacher);
                    finish();
                }
                else if (userType == 2 && !s.startsWith("none")) {
                    Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(BATCH_LOGIN_CHECK, true);
                    editor.putString(NAME, s.substring(0,1).toUpperCase() + s.substring(1));
                    editor.putString(USERNAME, username);
                    editor.putString(PASSWORD, password);
                    editor.commit();
                    startActivity(intentLoginBatch);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Username, Password and Type combination not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String username = params[0];
                String password = params[1];
                if(username.equals("admin") && password.equals("admin")){
                    return "admin";
                }
                else if(checkTeacher()){
                    return usernameS;
                }
                else if(checkBatch()){
                    return usernameS;
                } else {
                    return "none";
                }
            }
        }

        LoginUser lu = new LoginUser();
        lu.execute(username, password);
    }

    private boolean checkBatch(){
        ArrayList<BatchDetail> batchDetails = new UserStorage().getBatchDetails();
        int len = batchDetails.size();
        for (int i=0;i<len;i++){
            if (usernameS.equals(batchDetails.get(i).getUserName()) && passwordS.equals(batchDetails.get(i).getPassword())){
                return true;
            }
        }
        return false;
     }

    private boolean checkTeacher(){
        ArrayList<TeacherDetail> teacherDetails = new UserStorage().getTeacherDetails();
        int len = teacherDetails.size();
        for (int i=0;i<len;i++){
            if (usernameS.equals(teacherDetails.get(i).getFirstName()) && passwordS.equals(teacherDetails.get(i).getPassword())){
                return true;
            }
        }
        return false;
    }

    private void getAllUsersData(){
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
}
