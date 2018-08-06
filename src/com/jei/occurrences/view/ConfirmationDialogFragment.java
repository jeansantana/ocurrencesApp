package com.jei.occurrences.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class ConfirmationDialogFragment extends DialogFragment {
	private boolean save;
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        builder.setMessage("Salvar?")
               .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       save = true;
                   }
               })
               .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       save = false;
                   }
               });
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
    
    public boolean getSave(){
    	return save;
    }
}
