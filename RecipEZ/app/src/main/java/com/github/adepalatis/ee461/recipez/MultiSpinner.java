package com.github.adepalatis.ee461.recipez;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by Tony on 4/25/2016.
 */
public class MultiSpinner extends Spinner implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private String[] items;
    private boolean[] selected;
    private MultiSpinnerListener listener;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    public String[] getItems() {
        return items;
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked) {
            if (which != 0) {
                uncheckAny((AlertDialog) dialog);
            }
            if (which == 0) {
                uncheckAllElements((AlertDialog) dialog);
            }
            selected[which] = true;
        }
        else
            selected[which] = false;
            if(allUncheckedExceptAny((AlertDialog) dialog) == true)
                checkAny((AlertDialog) dialog);

    }

    private boolean allUncheckedExceptAny(AlertDialog dialog) {
        for(int i = 1; i < items.length; i++)
        {
            if(selected[i] == true)
                return false;
        }
        return true;
    }

    private void checkAny(AlertDialog dialog) {
        selected[0] = true;
        ((AlertDialog) dialog).getListView().setItemChecked(0, true);
    }

    private void uncheckAny(AlertDialog dialog) {
        selected[0] = false;
        ((AlertDialog) dialog).getListView().setItemChecked(0, false);
    }

    private void uncheckAllElements(AlertDialog dialog) {
        for(int i = 1; i < items.length; i++)
        {
            selected[i] = false;
            ((AlertDialog) dialog).getListView().setItemChecked(i, false);
        }
    }
    @Override
    public void onCancel(DialogInterface dialog) {
        // refresh text on spinner
        StringBuffer spinnerBuffer = new StringBuffer();
        boolean someUnselected = false;
        boolean oneOtherThanAnySelected = false;
        boolean noneSelected = true;
        for (int i = items.length - 1; i >= 0; i--) { //traverse backwards
            if (selected[i] == true) {
                if(i!=0) {
                    oneOtherThanAnySelected = true;
                    noneSelected = false;
                }
                else if(oneOtherThanAnySelected == true && i == 0) {
                    selected[i] = false;
                    continue; //dont append any
                }
                spinnerBuffer.append(items[i]);
                spinnerBuffer.append(", ");
            } else {
                someUnselected = true;
            }
        }
        if(noneSelected == true) {
            if(selected[0] != true) { //if user had something selected before but not anymore. this avoids printing "Any" twice
                selected[0] = true;
                spinnerBuffer.append(items[0]);
                spinnerBuffer.append(", ");
            }
        }
        String spinnerText;
        if (someUnselected) {
            spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2); //remove space and comma at end
        } else {
            spinnerText = "Hello";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner,
                new String[] { spinnerText });
        setAdapter(adapter);
        listener.onItemsSelected(this, selected);

    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle("Select Items");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        //if(items[1].equals("Main course")) { //used for dish type. Creates a dialog with only one option to select
        //    builder.setItems(items,this);
        //}
        //else { //creata a multiple choice dialog
            builder.setMultiChoiceItems(items, selected, this);
        //}

        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    public void setItems(String[] items, MultiSpinnerListener listener) {
        this.items = items;
        this.listener = listener;

        selected = new boolean[items.length];

        // 1st selected by default
        selected[0] = true;

        // the rest are unselected by default
        for (int i = 1; i < selected.length; i++)
            selected[i] = false;

        // all text on the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, items);
        setAdapter(adapter);
    }

    public interface MultiSpinnerListener {
        public void onItemsSelected(View v, boolean[] selected);
    }

}
