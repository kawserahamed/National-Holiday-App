package org.primeit.governmentholidays.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.primeit.governmentholidays.R;
import org.primeit.governmentholidays.model.HolidayModel;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    Context context;
    ArrayList<HolidayModel> holidayList = new ArrayList<>();

    public RecyclerAdapter(Context context, ArrayList<HolidayModel> holidayList) {
        this.context = context;
        this.holidayList = holidayList;
    }

    public RecyclerAdapter() {
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        HolidayModel holiday = holidayList.get(position);
        holder.title.setText(holiday.getHoliday());
        holder.date.setText(holiday.getDate());


    }

    @Override
    public int getItemCount() {
        return holidayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView date;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textTitle);
            date = itemView.findViewById(R.id.textDate);
        }
    }
}
