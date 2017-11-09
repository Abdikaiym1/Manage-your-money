package com.example.asusx555l.projecttoolbar.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asusx555l.projecttoolbar.ItemTouchHelperClass;
import com.example.asusx555l.projecttoolbar.R;
import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.ui.custom.CurrencyTextView;

import java.util.Collections;
import java.util.List;


public class ExpensesRecyclerViewAdapter extends RecyclerView.Adapter<ExpensesRecyclerViewAdapter.ViewHolder>
    implements ItemTouchHelperClass.ItemTouchHelperAdapter {

    private ItemTouchListener itemTouchListener;

    public interface ItemTouchListener {
        void onItem(Expense mJustDeletedToDoItem, int mIndexOfDeletedToDoItem, List<Expense> listItems);
    }

    private List<Expense> listItems;
    private Context context;
    private Expense mJustDeletedToDoItem;
    private int mIndexOfDeletedToDoItem;

    public ExpensesRecyclerViewAdapter(List<Expense> listItems, ItemTouchListener itemTouchListener) {
        this.listItems = listItems;
        this.itemTouchListener = itemTouchListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Expense exp = listItems.get(position);
        holder.money.setText(String.valueOf(exp.getMoney()));
        holder.money.setCurrencyText(exp.getCurrency().name());
        holder.date.setText(exp.getDate());

        holder.date.setTextColor(context.getResources().getColor(exp.isSpend() ? android.R.color.holo_red_dark : android.R.color.holo_green_dark));

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition) {
        if(fromPosition<toPosition){
            for(int i=fromPosition; i<toPosition; i++){
                Collections.swap(listItems, i, i+1);
            }
        }
        else{
            for(int i=fromPosition; i > toPosition; i--){
                Collections.swap(listItems, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemRemoved(int position) {
        mJustDeletedToDoItem =  listItems.remove(position);
        mIndexOfDeletedToDoItem = position;
        notifyItemRemoved(position);
        itemTouchListener.onItem(mJustDeletedToDoItem, mIndexOfDeletedToDoItem, listItems);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public CurrencyTextView money;
        public TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            money = itemView.findViewById(R.id.money);
            date =  (TextView) itemView.findViewById(R.id.date);
        }
    }
}
