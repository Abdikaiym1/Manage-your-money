package com.example.asusx555l.projecttoolbar.ui.fragmets;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asusx555l.projecttoolbar.ui.FullScreenDialogOfFilter;
import com.example.asusx555l.projecttoolbar.ui.ExpensesRecyclerViewAdapter;
import com.example.asusx555l.projecttoolbar.ItemTouchHelperClass;
import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.activities.ExpanseAddActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ExpensePage extends BasePage implements ExpensesRecyclerViewAdapter.ItemTouchListener, FullScreenDialogOfFilter.FullScreenDialogListener {

    private RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemTouchHelper itemTouchHelper;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_page, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        expenseList = new ArrayList<>();
        mAdapter = new ExpensesRecyclerViewAdapter(expenseList, this);

        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(mAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_elements:
                showFullScreenDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFullScreenDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        FullScreenDialogOfFilter fullScreenDialogOfFilter = new FullScreenDialogOfFilter(this);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, fullScreenDialogOfFilter).commit();
    }

    @Override
    public void onItem(final Expense mJustDeletedToDoItem, final int mIndexOfDeletedToDoItem, final List<Expense> listItems) {
        expenses.remove(mIndexOfDeletedToDoItem);
        Snackbar.make(view, "Deleted", Snackbar.LENGTH_SHORT)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        expenses.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);
                        mAdapter.notifyDataSetChanged();
                    }
                }).show();
    }

    @Override
    public void onItemClick(int position, View view, List<Expense> listItems) {
        Intent intent = new Intent(getActivity(), ExpanseAddActivity.class);
        Expense expense = listItems.get(position);
        intent.putExtra(Expense.KEY, expense);
        intent.putExtra(Expense.MAIN_POSITION, expenses.indexOf(expense));
        intent.putExtra(Expense.POSITION, position);
        getActivity().startActivityForResult(intent, ExpanseAddActivity.BaseCODE);
    }

    @Override
    public void onItemRemoved(Expense item, int position) {
        if (expenseList.contains(item))
            expenseList.remove(item);
    }

    @Override
    public void onItemAdded(Expense item, int position) {
        if (satisfied(item)) {
            expenseList.add(0, item);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemChanged(Expense item, Expense oldItem, int position) {
        if (expenseList.contains(oldItem)) {
            if (satisfied(item)) {
                expenseList.set(expenseList.indexOf(oldItem), item);
            } else {
                expenseList.remove(oldItem);
            }
        } else {
            if (satisfied(item)) {
                expenseList.add(0, item);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    protected abstract boolean satisfied(Expense expense);

    @Override
    public void sortSignal(String nameOfParameter) {
        if (Objects.equals(nameOfParameter, "Сначала минимальные")) {
            Toast.makeText(getActivity(), nameOfParameter, Toast.LENGTH_SHORT).show();
        } else if (Objects.equals(nameOfParameter, "Сначала максимальные")) {
            Toast.makeText(getActivity(), nameOfParameter, Toast.LENGTH_SHORT).show();
        } else if (Objects.equals(nameOfParameter, "Новые по дате сверху")) {
            Toast.makeText(getActivity(), nameOfParameter, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), nameOfParameter, Toast.LENGTH_SHORT).show();
        }

    }
}
