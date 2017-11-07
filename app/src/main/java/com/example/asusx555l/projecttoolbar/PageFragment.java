package com.example.asusx555l.projecttoolbar;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.beans.Expense2;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment implements ExpensesRecyclerViewAdapter.ItemTouchListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Expense> expenseList;
    public ItemTouchHelper itemTouchHelper;
    public CoordinatorLayout mCoordLayout;

    public PageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_one, container, false);
        mCoordLayout = (CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SecondActivity.CODE) {
            if (resultCode == RESULT_OK) {
                expenseList.add((Expense) data.getSerializableExtra("exp"));
                mAdapter.notifyDataSetChanged();
            }
        }
    }




    @Override
    public void onItem(final Expense mJustDeletedToDoItem, final int mIndexOfDeletedToDoItem, final List<Expense> listItems) {
        Snackbar.make(mCoordLayout, "Deleted", Snackbar.LENGTH_SHORT)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listItems.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);
                        mAdapter.notifyItemInserted(mIndexOfDeletedToDoItem);
                    }
                }).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.first_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i = new Intent(getActivity().getApplication(), SecondActivity.class);
                startActivityForResult(i, SecondActivity.CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
