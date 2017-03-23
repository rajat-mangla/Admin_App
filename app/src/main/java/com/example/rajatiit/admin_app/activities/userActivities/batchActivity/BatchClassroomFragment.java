package com.example.rajatiit.admin_app.activities.userActivities.batchActivity;

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
import com.example.rajatiit.admin_app.dataclasses.users.BatchDetail;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.example.rajatiit.admin_app.timetablehandler.SlotDetails;
import com.example.rajatiit.admin_app.timetablehandler.TimeTable;

import java.util.ArrayList;


public class BatchClassroomFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_display_data_interface, container, false);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addDetail);
        floatingActionButton.setVisibility(View.GONE);

        ListView listview = (ListView) view.findViewById(R.id.displayDataList);

        CustomBatchClassroomAdapter customBatchClassroomAdapter = new CustomBatchClassroomAdapter(getContext(),
                R.layout.activity_display_data_interface, generateClassroomList());

        listview.setAdapter(customBatchClassroomAdapter);

        return view;
    }

    private ArrayList<Classroom> generateClassroomList(){
        ArrayList<Classroom> classrooms = new ArrayList<>();

        BatchDetail batchDetail = new BatchDetail();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String userName = sp.getString(Login.USERNAME,"Daku");
        String password = sp.getString(Login.PASSWORD,"DAku");

        ArrayList<BatchDetail> batchDetails = new UserStorage().getBatchDetails();
        int totalBatches = UserStorage.noOfBatches();

        for (int i=0;i<totalBatches;i++) {
            if (userName.equals(batchDetails.get(i).getUserName()) && password.equals(batchDetails.get(i).getPassword())) {
                batchDetail = batchDetails.get(i);
                break;
            }
        }
        int batchId = batchDetail.getBatchId();

        ArrayList<SlotDetails> slotDetails = new TimeTable().getTotalSlots();
        int totalSlots = slotDetails.size();

        for (int i=0;i<totalSlots;i++){
            int len1 = slotDetails.get(i).totalClassrooms();
            for (int j=0;j<len1;j++){
                if (batchId == slotDetails.get(i).getClassroomDetail(j).getBatchId()) {
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
