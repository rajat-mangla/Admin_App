package com.example.rajatiit.admin_app.dataclasses;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by RajatIIT on 09-02-2017.
 */

public class TeacherDetail {
    private String firstName;
    private String lastName;
    private String password;
    private String lite;

    public TeacherDetail(){

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLite(String lite) {
        this.lite = lite;
    }

    public String getPassword() {

        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLite() {
        return lite;
    }
}
