package com.example.rajatiit.admin_app.activities.userActivities.teacheractivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rajatiit.admin_app.Login;
import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.insti.Classroom;
import com.example.rajatiit.admin_app.dataclasses.users.TeacherDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.example.rajatiit.admin_app.timetablehandler.SlotDetails;
import com.example.rajatiit.admin_app.timetablehandler.TimeTable;

import java.util.ArrayList;


public class TeacherClassroomFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_display_data_interface, container, false);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addDetail);
        floatingActionButton.setVisibility(View.GONE);

        ListView listview = (ListView) view.findViewById(R.id.displayDataList);

        CustomTeacherClassroomAdapter customTeacherClassroomAdapter = new CustomTeacherClassroomAdapter(getContext(),
                R.layout.activity_display_data_interface, generateClassroomList());

        listview.setAdapter(customTeacherClassroomAdapter);

        return view;
    }

    private ArrayList<Classroom> generateClassroomList(){
        ArrayList<Classroom> classrooms = new ArrayList<>();

        TeacherDetail teacherDetail = new TeacherDetail();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String userName = sp.getString(Login.USERNAME,"Daku");
        String password = sp.getString(Login.PASSWORD,"DAku");

        int totalTeachers = UserStorage.noOfTeachers();

        for (int i=0;i<totalTeachers;i++){

            String teacherFirstName = UserStorage.getTeacherDetail(i).getFirstName();
            String teacherPassword = UserStorage.getTeacherDetail(i).getPassword();

            if (userName.equals(teacherFirstName) && password.equals(teacherPassword)){
                teacherDetail = UserStorage.getTeacherDetail(i);
                break;
            }
        }

        int teacherId = teacherDetail.getTeacherId();
        ArrayList<SlotDetails> slotDetails = new TimeTable().getTotalSlots();
        int totalSlots = slotDetails.size();

        // Iterating through SLOTS
        for (int i=0;i<totalSlots;i++){
            int slotClassrooms = slotDetails.get(i).totalClassrooms();

            // For Each Slot Iterating through Total Classrooms in that Slot
            for (int j=0;j<slotClassrooms;j++){

                int tempTeacherId = slotDetails.get(i).getClassroomDetail(j).getTeacherId();

                if (teacherId == tempTeacherId) {
                    classrooms.add(slotDetails.get(i).getClassroomDetail(j));
                    break;
                }
            }

            if (classrooms.size() != i+1){
                classrooms.add(new Classroom());
            }
        }
        return classrooms;
    }
}
