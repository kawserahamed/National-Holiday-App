package org.primeit.governmentholidays.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.primeit.governmentholidays.R;
import org.primeit.governmentholidays.adapter.MonthAdapter;
import org.primeit.governmentholidays.adapter.RecyclerAdapter;
import org.primeit.governmentholidays.databinding.ActivityMainBinding;
import org.primeit.governmentholidays.interfaces.OnMonthClickListener;
import org.primeit.governmentholidays.model.HolidayModel;
import org.primeit.governmentholidays.utils.date_picker.OnDateSelectedListener;
import org.primeit.governmentholidays.viewModel.HolidayViewModel;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMonthClickListener {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private RecyclerAdapter adapter;
    private MonthAdapter monthAdapter;
    HolidayViewModel holidayViewModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
    private final SimpleDateFormat dateFormatNumber = new SimpleDateFormat("dd-MM", Locale.getDefault());
    private final SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM", Locale.getDefault());
    Calendar today = Calendar.getInstance();
    private final ArrayList<HolidayModel> holidayList = new ArrayList<>();
    private final ArrayList<String> monthList = new ArrayList<>();
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        holidayViewModel = new ViewModelProvider(this).get(HolidayViewModel.class);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(getApplicationContext(), holidayList);
        binding.recyclerView.setAdapter(adapter);

        String[] months = getResources().getStringArray(R.array.month_list);
        monthList.addAll(Arrays.asList(months));
        monthAdapter = new MonthAdapter(getApplicationContext(), monthList, this);
        binding.rvMonthList.setAdapter(monthAdapter);


        binding.tvDate.setText(dateFormat.format(today.getTime()));
        selectedDate = dateFormatNumber.format(today.getTime());

        binding.datePickerTimeline.setInitialDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
        binding.datePickerTimeline.setActiveDate(today);

        holidayViewModel.getHolidayList(this);

        holidayViewModel.getHolidayFilterList(dateFormatMonth.format(System.currentTimeMillis()), false);

        /*holidayViewModel.holidayLiveList.observe(this, holidayModels -> {
            Log.d(TAG, "onCreate: " + holidayModels.size());
            Log.d(TAG, "onCreate: " + dateFormatMonth.format(today.get(Calendar.MONTH)));
            holidayList.addAll(holidayModels);

            adapter.notifyDataSetChanged();

        });*/

        binding.datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                Date date = new Date(year, month, day);
                selectedDate = dateFormatNumber.format(date);
                holidayViewModel.getHolidayFilterList(dateFormatNumber.format(date), true);

            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {

            }
        });

        holidayViewModel.holidayFilterLiveList.observe(this, holidayModels -> {
            holidayList.clear();
            holidayList.addAll(holidayModels);
            adapter = new RecyclerAdapter(getApplicationContext(), holidayList);
            binding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            Log.d(TAG, "onChanged: " + holidayModels.size());
            if (holidayModels.size() != 0) {
                binding.layoutLinear2.setVisibility(View.GONE);
            } else {
                binding.layoutLinear2.setVisibility(View.VISIBLE);
            }
        });

        binding.switchMonthView.setOnToggledListener((toggleableView, isOn) -> {
            if (isOn) {
                binding.datePickerTimeline.setVisibility(View.GONE);
                binding.rvMonthList.setVisibility(View.VISIBLE);
                holidayViewModel.getHolidayFilterList(dateFormatMonth.format(System.currentTimeMillis()), false);
            } else {
                binding.datePickerTimeline.setVisibility(View.VISIBLE);
                binding.rvMonthList.setVisibility(View.GONE);
                holidayViewModel.getHolidayFilterList(selectedDate, true);
            }
        });
    }

    @Override
    public void onMonthClick(int position, String month) {
        String pos = (position > 9) ? "" + (position + 1) : "0" + (position + 1);
        Log.d(TAG, "onMonthClick: " + month);
        Log.d(TAG, "onMonthClick: " + pos);
        holidayViewModel.getHolidayFilterList(pos, false);

    }
}