package org.primeit.governmentholidays.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.primeit.governmentholidays.adapter.RecyclerAdapter;
import org.primeit.governmentholidays.databinding.ActivityMainBinding;
import org.primeit.governmentholidays.model.HolidayModel;
import org.primeit.governmentholidays.viewModel.HolidayViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {


    ActivityMainBinding binding;
    RecyclerAdapter adapter;
    HolidayViewModel viewModel;

    private final ArrayList<HolidayModel> holidayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new HolidayViewModel(holidayList);
        viewModel.getHolidayList(getApplicationContext());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(getApplicationContext(), holidayList);
        binding.recyclerView.setAdapter(adapter);


    }


}