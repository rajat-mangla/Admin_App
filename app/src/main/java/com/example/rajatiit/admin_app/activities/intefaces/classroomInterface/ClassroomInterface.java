package com.example.rajatiit.admin_app.activities.intefaces.classroomInterface;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rajatiit.admin_app.dataclasses.Database;
import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.insti.Classroom;
import com.example.rajatiit.admin_app.dataclasses.insti.Institute;
import com.example.rajatiit.admin_app.dataclasses.users.UserStorage;

import java.util.ArrayList;

public class ClassroomInterface extends AppCompatActivity implements AddEditCourseDialog.classroomDetailpasser {

    // Position in array to get the Specific Classroom
    private int classroomId;

    // Adapter for displaying the course list
    CustomCourseListAdapter customCourseListAdapter;

    Institute institute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.CLASSROOM_DATA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_display_data_interface);

        institute = new Institute();

        final ListView listView = (ListView) findViewById(R.id.displayDataList);
        listView.setDivider(null);
        listView.setDividerHeight(0);

        customCourseListAdapter = new CustomCourseListAdapter(getBaseContext(),
                R.layout.activity_display_data_interface, institute.getClassrooms());
        listView.setAdapter(customCourseListAdapter);

        // For ListView Long CLick Listener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // *** IMPORTANT
                classroomId = position;
                registerForContextMenu(listView);
                openContextMenu(listView);
                return true;
            }
        });

        // For Floating action button add details
        final FloatingActionButton addDetail = (FloatingActionButton) findViewById(R.id.addDetail);
        addDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classroomId = institute.getClassrooms().size();
                registerForContextMenu(addDetail);
                openContextMenu(addDetail);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.addDetail) {
            super.onCreateContextMenu(menu, v, menuInfo);
            menu.setHeaderTitle("Select Department");
            int len = Institute.getDepartments().size();
            for (int i = 0; i < len; i++) {
                menu.add(0, v.getId(), 0, Institute.getDepartments().get(i));
            }
        } else {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.viewItem:
                Toast.makeText(this, "View Details", Toast.LENGTH_SHORT).show();
                // TODO : WORK WITH VIEW DETAILS
                return true;
*/
            case R.id.editItem:
                Toast.makeText(this, "Edit Details", Toast.LENGTH_SHORT).show();
                //shows EDIT Dialog
                DialogFragment editDialog = new AddEditCourseDialog();
                Bundle bundle = new Bundle();

                // bundle.putSerializable(Integer.toString(R.string.CLASSROOM_DATA), institute.getClassrooms().get(classroomId));

                bundle.putInt(Integer.toString(R.string.CLASSROOM_DATA), classroomId);


                editDialog.setArguments(bundle);
                editDialog.show(getFragmentManager(), Integer.toString(R.string.EDIT_DIALOG));
                return true;

            case R.id.deleteItem:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm Delete")
                        .setMessage(R.string.delete_classroom)
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final ProgressDialog progressDialog = ProgressDialog.show(ClassroomInterface.this, null,
                                        Integer.toString(R.string.UPDATING));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // do the thing that takes a long time
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                deleteClassroom();
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).start();
                            }
                        });
                builder.show();
                return true;
        }
        for (int i = 0; i < Institute.getDepartments().size(); i++) {
            if (item.getTitle().toString().equals(Institute.getDepartments().get(i))) {

                DialogFragment addDialog = new AddEditCourseDialog();
                Bundle bundle = new Bundle();
                bundle.putString(Integer.toString(R.string.DEPARTMENT_NAME), item.getTitle().toString());
                addDialog.setArguments(bundle);

                addDialog.show(getFragmentManager(), Integer.toString(R.string.ADD_DIALOG));

                // TODO pass value of department to course display activity
                return true;
            }
        }
        return true;
    }

    private void deleteClassroom() {
        int teacherId = Institute.getClassroomDetail(classroomId).getTeacherId();
        int batchId = Institute.getClassroomDetail(classroomId).getBatchId();
        {
            ArrayList<Integer> classroomIds = UserStorage.getTeacherDetail(teacherId).getClassroomIds();
            for (int i = 0; i < classroomIds.size(); i++) {
                if (classroomId == classroomIds.get(i)) {
                    UserStorage.getTeacherDetail(teacherId).getClassroomIds().remove(i);
                }
            }
        }
        {
            ArrayList<Integer> classroomIds = UserStorage.getBatchDetail(batchId).getClassroomIds();
            for (int i = 0; i < classroomIds.size(); i++) {
                if (classroomId == classroomIds.get(i)) {
                    UserStorage.getBatchDetail(batchId).getClassroomIds().remove(i);
                }
            }
        }
        Institute.deleteClassroomDetail(classroomId);

        Database.updateUsers(new UserStorage());
        Database.deleteClassroomInfo();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                customCourseListAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void passAddDialogDetails(final Classroom classroom) {
        final ProgressDialog progressDialog = ProgressDialog.show(ClassroomInterface.this, null,
                getResources().getString(R.string.UPDATING));
        new Thread(new Runnable() {
            @Override
            public void run() {
                addClassroom(classroom);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Log.e("here","hdakdakda");
                }
                progressDialog.dismiss();
            }
        }).start();
    }

    private void addClassroom(Classroom classroom) {
        classroom.setClassroomId(Institute.totalNoOfClassrooms());
        UserStorage.getTeacherDetail(classroom.getTeacherId()).getClassroomIds().add(classroomId);
        UserStorage.getBatchDetail(classroom.getBatchId()).getClassroomIds().add(classroomId);
        classroom.generateCourseId();

        Institute.addClassroomDetail(classroom);

        Database.sendClassroomInfo(classroom);
        Database.sendTeacherInfo(UserStorage.getTeacherDetail(classroom.getTeacherId()));
        Database.sendBatchInfo(UserStorage.getBatchDetail(classroom.getBatchId()));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                customCourseListAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void passEditDialogDetails(final Classroom classroom) {
        final ProgressDialog progressDialog = ProgressDialog.show(ClassroomInterface.this, null,
                getResources().getString(R.string.UPDATING));
        new Thread(new Runnable() {
            @Override
            public void run() {
                editClassroom(classroom);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Log.e("here","hdakdakda");
                }
                progressDialog.dismiss();
            }
        }).start();
    }

    private void editClassroom(Classroom classroom) {

        UserStorage.getTeacherDetail(classroom.getTeacherId()).getClassroomIds().add(classroomId);
        UserStorage.getBatchDetail(classroom.getBatchId()).getClassroomIds().add(classroomId);
        classroom.generateCourseId();

        Database.sendClassroomInfo(classroom);
        Database.sendBatchDetails();
        Database.sendTeacherDetails();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                customCourseListAdapter.notifyDataSetChanged();
            }
        });
    }
}
