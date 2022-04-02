package org.primeit.governmentholidays.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.primeit.governmentholidays.R;
import org.primeit.governmentholidays.interfaces.OnMonthClickListener;

import java.util.ArrayList;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.RecyclerViewHolder> {

    Context context;
    ArrayList<String> monthList = new ArrayList<>();
    OnMonthClickListener onMonthClickListener;
    private View selectedView;
    private int selectedPosition;

    public MonthAdapter(Context context, ArrayList<String> holidayList, OnMonthClickListener onMonthClickListener) {
        this.context = context;
        this.monthList = holidayList;
        this.onMonthClickListener = onMonthClickListener;
    }

    public MonthAdapter() {
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_month_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv_month.setTextColor(Color.BLACK);
        holder.tv_month.setText(monthList.get(position));

        if (selectedPosition == position) {
            holder.rootView.setBackgroundResource(R.drawable.background_shape);
            holder.tv_month.setTextColor(Color.WHITE);
        } else {
            holder.rootView.setBackground(null);
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            holder.rootView.setBackgroundResource(R.drawable.background_shape);
            holder.tv_month.setTextColor(Color.WHITE);
            notifyDataSetChanged();
            onMonthClickListener.onMonthClick(selectedPosition, monthList.get(selectedPosition));
        });
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_month;
        LinearLayout rootView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_month = itemView.findViewById(R.id.tv_month);
            rootView = itemView.findViewById(R.id.rootView);
        }

    }
}
