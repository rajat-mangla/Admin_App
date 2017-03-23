package com.example.rajatiit.admin_app.activities.intefaces.roomInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rajatiit.admin_app.R;
import com.example.rajatiit.admin_app.dataclasses.insti.Institute;
import com.example.rajatiit.admin_app.dataclasses.insti.RoomDetail;
import com.example.rajatiit.admin_app.activities.intefaces.SpinnerHandler;


/**
 * Created by rajat on 25/2/17.
 */

public class AddEditRoomDialog extends DialogFragment implements AdapterView.OnItemSelectedListener{

    // Flag to check if its a editDialog
    private boolean isEditView;

    private RoomDetail roomDetail;
    private int roomCapacity;

    // view to display
    private View view;

    // Interface For Passing Values to Batch Interface ...
    public interface RoomDetailsPasser {
        void passAddDialogDetail(RoomDetail roomDetail);

        void passEditDialogDetail(RoomDetail roomDetail);
    }
    private RoomDetailsPasser roomDetailsPasser;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_edit_room, null);

        if (getFragmentManager().findFragmentByTag(Integer.toString(R.string.ADD_DIALOG))!=null){
            isEditView=false;
            return addDialogBuilder(builder);
        }
        else {
            isEditView=true;
            return editDialogBuilder(builder);
        }
    }

    // method to create ADD_DIALOG BUILDER
    private Dialog addDialogBuilder(AlertDialog.Builder builder) {
        builder.setView(view)
                .setTitle("Add Room")
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // override in show method
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing to do in this
                    }
                });

        // setting the spinners for the add dialog
        setSpinners();
        return builder.create();
    }

    // Builder For Edit Dialog
    private Dialog editDialogBuilder(AlertDialog.Builder builder) {
        builder.setView(view)
                .setTitle("Edit Room")
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing here will overide in onshow() dialog method
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
        // setting the spinners for the Edit dialog
        setSpinners();
        roomDetail = (RoomDetail) getArguments().getSerializable(Integer.toString(R.string.ROOM_DATA));
        showDetails();

        return builder.create();
    }

    // Method to show existing details in for a teacher In  @Edit Dialog
    private void showDetails(){
        EditText roomNo = (EditText) view.findViewById(R.id.add_edit_room_roomNo);
        Spinner roomCapacity = (Spinner)view.findViewById(R.id.add_edit_room_capacity);
        CheckBox isProjector = (CheckBox)view.findViewById(R.id.add_edit_room_isprojector);

        roomNo.setText(String.valueOf(roomDetail.getRoomNo()));

        switch (roomDetail.getCapacity()) {
            case 40:
                roomCapacity.setSelection(0);
                break;
            case 80:
                roomCapacity.setSelection(1);
                break;

            case 120:
                roomCapacity.setSelection(1);
                break;
        }
        if (roomDetail.getProjector()){isProjector.setChecked(true);}
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handleErrors()) {
                    roomDetailsPasser = (RoomDetailsPasser) getActivity();
                    if (isEditView){
                        if (getDetails()){
                            dismiss();
                            roomDetailsPasser.passEditDialogDetail(roomDetail);
                        }
                        else {
                            Toast.makeText(getActivity(),"Room No already exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        // storing the details entered by User for add dialog
                        roomDetail = new RoomDetail();
                        if (getDetails()){
                            dismiss();
                            roomDetailsPasser.passAddDialogDetail(roomDetail);
                        }
                        else {
                            Toast.makeText(getActivity(),"Room No already exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    // method to handle all the errors ...
    private boolean handleErrors() {
        EditText roomNo = (EditText) view.findViewById(R.id.add_edit_room_roomNo);
        if (roomNo.getText().toString().equals("")){
            Toast.makeText(getActivity(),"Enter full details",Toast.LENGTH_SHORT).show();
            return false;
        }
        // TODO : HANDLE OTHER ERRORS
        return true;
    }


    // GETTING all the details from the dialog front end ...
    private boolean getDetails() {
        EditText roomNo = (EditText) view.findViewById(R.id.add_edit_room_roomNo);
        CheckBox isProjector = (CheckBox)view.findViewById(R.id.add_edit_room_isprojector);
        int iroomNo = Integer.parseInt(roomNo.getEditableText().toString());

        // means its for add dialog
        // we have to check whether room already exists
        if (roomDetail.getRoomNo() == -1){
            int len = Institute.totalNoOfRooms();
            for (int i=0;i<len;i++){
                if (iroomNo == Institute.getRoomDetail(i).getRoomNo()){
                    return false;
                }
            }
        }
        // its a edit dialog
        else {
            if (roomDetail.getRoomNo() != iroomNo){
                int len = Institute.totalNoOfRooms();
                for (int i=0;i<len;i++){
                    if (iroomNo == Institute.getRoomDetail(i).getRoomNo()){
                        return false;
                    }
                }
            }
        }
        roomDetail.setRoomNo(iroomNo);
        roomDetail.setCapacity(roomCapacity);
        roomDetail.setProjector(isProjector.isChecked());
        return true;
    }


    // Method for setting up the spinners ....
    public void setSpinners() {
        Spinner capacitySpinner = (Spinner) view.findViewById(R.id.add_edit_room_capacity);
        capacitySpinner.setOnItemSelectedListener(this);
        SpinnerHandler.setRoomCapacitySpinner(getActivity(), capacitySpinner);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        roomCapacity = (Integer) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


