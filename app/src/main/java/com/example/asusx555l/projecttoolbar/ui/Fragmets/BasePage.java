package com.example.asusx555l.projecttoolbar.ui.fragmets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asusx555l.projecttoolbar.ui.ExpensesRecyclerViewAdapter;
import com.example.asusx555l.projecttoolbar.ItemTouchHelperClass;
import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.activities.SecondActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class BasePage extends Fragment implements ExpensesRecyclerViewAdapter.ItemTouchListener {

    private RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected List<Expense> expenseList;
    private ItemTouchHelper itemTouchHelper;
    private View view;
    private Activity context;
    private int curPosition;
    

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
        Snackbar.make(view, "Deleted", Snackbar.LENGTH_SHORT)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listItems.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);
                        mAdapter.notifyItemInserted(mIndexOfDeletedToDoItem);
                    }
                }).show();
    }

    @Override
    public void onItemClick(int position, View view, List<Expense> listItems) {
        curPosition = position;
        Intent intent = new Intent(getActivity(), SecondActivity.class);
        intent.putExtra(Expense.KEY, listItems.get(position));
        intent.putExtra(Expense.POSITION, position);
        getActivity().startActivityForResult(intent, SecondActivity.BaseCODE);
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SecondActivity.BaseCODE) {
            if (resultCode == Activity.RESULT_OK) {
                Expense expense = (Expense) data.getSerializableExtra(Expense.KEY);
                if (!expenseList.get(curPosition).equals(expense)) {
                    Log.v("LIST",expenseList.get(curPosition).getTag());
                    Log.v("EXPENSE", expense.getTag());
                    expenseList.set(curPosition, expense);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }*/

    public void addNewExpense(Expense expense) {
        expenseList.add(0, expense);
        mAdapter.notifyDataSetChanged();
    }

    public void addNewExpense(Expense expense, int position) {
        expenseList.remove(position);
        expenseList.add(position, expense);
        mAdapter.notifyDataSetChanged();
    }

    public void getExpense(Expense expense) {
    }

    public void removeMoneyExpense(Expense expense) {

    }

    public void removeExpense(int position) {
        Log.v("DSD", String.valueOf(position));
        Log.v("DST", String.valueOf(expenseList.size()));
        expenseList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

}
