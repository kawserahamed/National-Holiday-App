package org.primeit.governmentholidays.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.primeit.governmentholidays.R;
import org.primeit.governmentholidays.databinding.ActivityDetailsBinding;
import org.primeit.governmentholidays.model.HolidayModel;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        HolidayModel holidayModel = (HolidayModel) getIntent().getSerializableExtra("holiday");


        binding.title.setText(holidayModel.getHoliday());
        binding.date.setText(holidayModel.getDate());
        binding.holidayType.setText(holidayModel.getType());
        binding.description.setText(holidayModel.getComment());

        Glide.with(getApplicationContext()).load(holidayModel.getUrl())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(binding.imageView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}