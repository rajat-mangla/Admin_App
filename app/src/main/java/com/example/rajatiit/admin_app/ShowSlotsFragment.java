package com.example.rajatiit.admin_app;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;
import com.example.rajatiit.admin_app.intefaces.teacherInterface.CustomTeacherListAdapter;

/**
 * Created by rajat on 20-01-2017.
 */


/*
Using Catalog Data As The Data To Represent the Food Items ....
 */
public class ShowSlotsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_display_data_interface, container, false);
        ListView listview = (ListView) view.findViewById(R.id.displayDataList);

        CustomSlotsListViewAdapter customSlotsListViewAdapter = new CustomSlotsListViewAdapter(getContext(),
                R.layout.activity_display_data_interface,new TimeTable().getTotalSlots());
        listview.setAdapter(customSlotsListViewAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addDetail);
        floatingActionButton.setVisibility(View.GONE);

        return view;
    }

}
