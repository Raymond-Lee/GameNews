package com.example.gamenews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

public class EntryDialogFragment extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	    	final EditText input = new EditText(getActivity());
			input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
	    	
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        /*
	        builder.setTitle("Title")
	        	   .setMessage("Please enter a game title: ")
	        	   .setView(input)
	               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   ((SimplifiedSelectorActivity)getActivity()).promptText = input.getText().toString();
	       					//dialog.dismiss();
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   dialog.cancel();
	                   }
	               });
	               */
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	}