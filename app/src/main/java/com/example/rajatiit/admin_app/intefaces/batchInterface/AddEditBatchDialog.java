package com.example.rajatiit.admin_app.intefaces.batchInterface;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import com.example.rajatiit.admin_app.R;

/**
 * Created by rajat on 22/2/17.
 */

public class AddEditBatchDialog extends DialogFragment {

    // Flag to check if its a editDialog
    private boolean isEditView;

    // view to display
    View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_teacher_add_edit,null);
        return addDialog(builder);
    }

    // method to create ADD_DIALOG BUILDER
    private Dialog addDialog(AlertDialog.Builder builder){
        return builder.create();
    }
}
