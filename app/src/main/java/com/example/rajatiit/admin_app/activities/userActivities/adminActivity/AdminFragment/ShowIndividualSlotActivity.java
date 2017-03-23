package com.example.rajatiit.admin_app.activities.userActivities.adminActivity.AdminFragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.insti.Classroom;
import com.example.rajatiit.admin_app.timetablehandler.TimeTable;

import java.util.ArrayList;

public class ShowIndividualSlotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_individual_slot);

        int slotIndex = getIntent().getIntExtra("SLOT_INDEX",-1);

        ArrayList<Classroom> classrooms = new TimeTable().getTotalSlots().get(slotIndex).getClassrooms();
        ListView listView = (ListView) findViewById(R.id.activity_show_individual_slot_list);

        IndividualSlotListAdapter individualSlotListAdapter = new IndividualSlotListAdapter(getBaseContext()
                            ,R.layout.activity_show_individual_slot,classrooms);

        listView.setAdapter(individualSlotListAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
