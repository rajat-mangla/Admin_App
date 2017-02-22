package com.example.rajatiit.admin_app.intefaces.classroomInterface;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.Institute;
import com.example.rajatiit.admin_app.intefaces.batchInterface.AddEditBatchDialog;

public class ClassroomInterface extends AppCompatActivity {

    CustomCourseListAdapter customCourseListAdapter;

    Institute institute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toast.makeText(getBaseContext(),"In Classroom Interface",Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_data_interface);

        institute = new Institute();

        ListView listView = (ListView) findViewById(R.id.displayDataList);
        customCourseListAdapter = new CustomCourseListAdapter(getBaseContext(),R.layout.display_data_interface,institute.getClassrooms());
        listView.setAdapter(customCourseListAdapter);

        // For Floating action button add details
        final FloatingActionButton addDetail = (FloatingActionButton) findViewById(R.id.addDetail);
        addDetail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO ADD A ADD COURSE INTERFACE

                registerForContextMenu(addDetail);
                openContextMenu(addDetail);
                return true;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Department");
        for (int i=0;i<institute.getDepartments().size();i++){
            menu.add(0,v.getId(),0,institute.getDepartments().get(i));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(getBaseContext(),item.getTitle().toString(),Toast.LENGTH_SHORT).show();

        for (int i=0;i<institute.getDepartments().size();i++){
            if (item.getTitle().toString().equals(institute.getDepartments().get(i))){

                DialogFragment addDialog = new AddEditCourseDialog();
                Bundle bundle = new Bundle();
                bundle.putString("DepartmentName",item.getTitle().toString());
                addDialog.setArguments(bundle);

                addDialog.show(getFragmentManager(),"Add_Dialog");

                // TODO pass value of department to course display activity
                return true;
            }
        }
        return true;
    }
}
