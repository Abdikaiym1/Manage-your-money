package com.example.asusx555l.projecttoolbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asusx555l.projecttoolbar.beans.Expense;
import com.example.asusx555l.projecttoolbar.beans.Expense2;

import java.util.Date;
import java.util.List;


public class ExpensesRecyclerViewAdapter extends RecyclerView.Adapter<ExpensesRecyclerViewAdapter.ViewHolder> {

    private List<Expense> listItems;
    //private List<Expense2> listItems;
    private Context context;

    public ExpensesRecyclerViewAdapter(List<Expense> listItems) {
        this.listItems = listItems;
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
