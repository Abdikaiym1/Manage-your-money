package com.example.asusx555l.projecttoolbar.ui;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.ui.fragmets.ExpensePage;

@SuppressLint("ValidFragment")
public class FullScreenDialogOfFilter extends DialogFragment {
    private RadioGroup radioGroup;
    private View view;
    private FullScreenDialogListener fullScreenDialogListener;

    public interface FullScreenDialogListener {
        void sortSignal (String nameOfParameter);
    }

    @SuppressLint("ValidFragment")
    public FullScreenDialogOfFilter(FullScreenDialogListener fullScreenDialogListener) {
        this.fullScreenDialogListener = fullScreenDialogListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.full_screen_dialog_layout, container, false);

        radioGroup = view.findViewById(R.id.radio_group_full_screen);

        Toolbar toolbarFullScreen = view.findViewById(R.id.toolbar);
        toolbarFullScreen.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarFullScreen);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
            actionBar.setTitle("Сортировка");
        }

        setHasOptionsMenu(true);
        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_full_screen, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.filter_elements).setVisible(false);
        menu.findItem(R.id.action_add).setVisible(false);
        menu.findItem(R.id.action_setting).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ok :
                listenerRadioButton();
                dismiss();
                return true;
            case android.R.id.home:
                dismiss();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void listenerRadioButton () {
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = view.findViewById(checkedRadioButtonId);
        fullScreenDialogListener.sortSignal(radioButton.getText().toString());
    }
}
