package com.example.asusx555l.projecttoolbar.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.asusx555l.projecttoolbar.R;


@SuppressLint("ValidFragment")
public class NotifyDialogFragment extends AppCompatDialogFragment {
    private EditText editTextRateUSD;
    private EditText editTextRateEURO;
    private CheckBox checkBoxDefault;

    @SuppressLint("ValidFragment")
    public NotifyDialogFragment(RateDialogListener rateDialogListener) {
        this.rateDialogListener = rateDialogListener;
    }

    private RateDialogListener rateDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rate_dialog, null);

        builder.setView(view).setTitle("Нет подключение к сети")
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (checkBoxDefault.isChecked()) {
                    rateDialogListener.isDefaultRate(true);
                } else {
                    String RateUSD = editTextRateUSD.getText().toString();
                    String RateEUR = editTextRateEURO.getText().toString();
                    rateDialogListener.applyRate(RateUSD, RateEUR);
                }
            }
        });

        editTextRateEURO = view.findViewById(R.id.rate_euro);
        editTextRateUSD = view.findViewById(R.id.rate_usd);
        checkBoxDefault = view.findViewById(R.id.checkbox_default);

        return builder.create();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

        try {
            rateDialogListener = (RateDialogListener) childFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(childFragment.toString() + " must implement NoticeDialogListener");
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            rateDialogListener = (RateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NoticeDialogListener");
        }
    }*/

    public interface RateDialogListener {
        void applyRate(String USD, String EUR);
        void isDefaultRate(boolean check);
    }
}
