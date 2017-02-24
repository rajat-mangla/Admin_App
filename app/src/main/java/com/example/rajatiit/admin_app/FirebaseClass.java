package com.example.rajatiit.admin_app;

import com.example.rajatiit.admin_app.dataclasses.Institute;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by RajatIIT on 10-02-2017.
 */

public class FirebaseClass {
    public static final String URL= "https://timetable-generation-system.firebaseio.com/";

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static FirebaseDatabase getDatabase() {
        return database;
    }

    public static void updateUsers(UserStorage userStorage){
        DatabaseReference reference = database.getReference(UserStorage.USER_STORAGE_REF);
        reference.setValue(userStorage);
    }
    public static void updateInstitute(Institute institute){
        DatabaseReference reference = database.getReference(Institute.INSTITUTE_REF);
        reference.setValue(institute);
    }
}
