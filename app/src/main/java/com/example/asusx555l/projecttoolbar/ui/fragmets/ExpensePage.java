package com.example.asusx555l.projecttoolbar.ui.fragmets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asusx555l.projecttoolbar.ui.ExpensesRecyclerViewAdapter;
import com.example.asusx555l.projecttoolbar.ItemTouchHelperClass;
import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.activities.ExpanseAddActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpensePage extends BasePage implements ExpensesRecyclerViewAdapter.ItemTouchListener {

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
}
