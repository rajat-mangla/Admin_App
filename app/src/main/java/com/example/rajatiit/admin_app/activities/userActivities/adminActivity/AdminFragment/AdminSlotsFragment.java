package com.example.rajatiit.admin_app.activities.userActivities.adminActivity.AdminFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.timetablehandler.TimeTable;

/**
 * Created by rajat on 20-01-2017.
 *
 */

/*
Using Catalog Data As The Data To Represent the Food Items ....
 */
public class AdminSlotsFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.activity_display_data_interface, container, false);
        ListView listView = (ListView) view.findViewById(R.id.displayDataList);
        listView.setDivider(null);
        listView.setDividerHeight(0);

        CustomSlotsListViewAdapter customSlotsListViewAdapter = new CustomSlotsListViewAdapter(getContext(),
                R.layout.activity_display_data_interface,new TimeTable().getTotalSlots());
        listView.setAdapter(customSlotsListViewAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addDetail);
        floatingActionButton.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getActivity(),ShowIndividualSlotActivity.class);
                intent.putExtra("SLOT_INDEX",position);
                getActivity().startActivity(intent);


            }
        });
        return view;
    }
}
