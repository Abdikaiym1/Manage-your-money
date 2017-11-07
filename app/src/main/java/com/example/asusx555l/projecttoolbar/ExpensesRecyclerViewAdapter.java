package com.example.asusx555l.projecttoolbar;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asusx555l.projecttoolbar.beans.Expense;

import java.util.Collections;
import java.util.List;

import static android.support.design.R.styleable.CoordinatorLayout;


public class ExpensesRecyclerViewAdapter extends RecyclerView.Adapter<ExpensesRecyclerViewAdapter.ViewHolder>
    implements ItemTouchHelperClass.ItemTouchHelperAdapter {

    private ItemTouchListener itemTouchListener;

    public interface ItemTouchListener {
        void onItem(Expense mJustDeletedToDoItem, int mIndexOfDeletedToDoItem, List<Expense> listItems);
    }

    private List<Expense> listItems;
    //private List<Expense2> listItems;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_one_page, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Expense exp = listItems.get(position);
        holder.money.setText(String.valueOf(exp.getMoney()));
        holder.currency.setText(exp.getCurrency().name());
        holder.date.setText(exp.getDate());

        /*Expense2 exp2 = listItems.get(position);
        holder.money.setText(String.valueOf(exp2.getMoney()));
        holder.currency.setText("3");
        holder.date.setText(exp2.getDate());*/
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
        public TextView money;
        public TextView currency;
        public TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            money =  (TextView) itemView.findViewById(R.id.money);
            currency =  (TextView) itemView.findViewById(R.id.currency);
            date =  (TextView) itemView.findViewById(R.id.date);
        }
    }
}
