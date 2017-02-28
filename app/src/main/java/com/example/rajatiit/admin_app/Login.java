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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {
    public static final String ADMIN_LOGIN_CHECK = "ADMIN_LOGGED_IN";
    public static final String TEACHER_LOGIN_CHECK = "TEACHER_LOGGED_IN";
    public static final String BATCH_LOGIN_CHECK = "BATCH_LOGGED_IN";
    public static final String NAME = "NAME";
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

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        intentLoginAdmin = new Intent(this, AdminMainActivity.class);
        intentLoginTeacher = new Intent(this, TeacherMainActivity.class);
        intentLoginBatch = new Intent(this, BatchMainActivity.class);

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
                            usernameS = username.getText().toString().trim().toLowerCase();
                            passwordS = password.getText().toString();

                            for (int i = 0; i < usernameS.length(); i++) {
                                if (usernameS.charAt(i) == ' ') {
                                    Toast.makeText(getBaseContext(), "Username cannot contain any space!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            for (int i = 0; i < passwordS.length(); i++) {
                                if (passwordS.charAt(i) == ' ') {
                                    Toast.makeText(getBaseContext(), "Password cannot contain any space!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

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

    private void loginUser(String username, String password) {
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
                if (userType == 0 && s.startsWith("admin")) {
                    Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(ADMIN_LOGIN_CHECK, true);
                    editor.putString(NAME, s.substring(0,1).toUpperCase() + s.substring(1));
                    editor.commit();
                    startActivity(intentLoginAdmin);
                    finish();
                }
                else if (userType == 1 && s.startsWith("teacher")) {
                    Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(TEACHER_LOGIN_CHECK, true);
                    editor.putString(NAME, s.substring(0,1).toUpperCase() + s.substring(1));
                    editor.commit();
                    startActivity(intentLoginTeacher);
                    finish();
                }
                else if (userType == 2 && s.startsWith("batch")) {
                    Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(BATCH_LOGIN_CHECK, true);
                    editor.putString(NAME, s.substring(0,1).toUpperCase() + s.substring(1));
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
                else if(username.equals("teacher") && password.equals("teacher")){
                    return "teacher";
                }
                else if(username.equals("batch") && password.equals("batch")){
                    return "batch";
                } else {
                    return "none";
                }
            }
        }

        LoginUser lu = new LoginUser();
        lu.execute(username, password);
    }
}
