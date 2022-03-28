package org.primeit.governmentholidays.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.primeit.governmentholidays.adapter.RecyclerAdapter;
import org.primeit.governmentholidays.databinding.ActivityMainBinding;
import org.primeit.governmentholidays.model.HolidayModel;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    RecyclerAdapter adapter;

    private final ArrayList<HolidayModel> holidayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getHolidayList();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(getApplicationContext(), holidayList);
        binding.recyclerView.setAdapter(adapter);


    }

    private void getHolidayList() {

        // JSON Parsing
        String myJSONStr = loadJSONFromAssets();

        try {
            JSONObject rootJsonObject = new JSONObject(myJSONStr);
            JSONArray holidayJsonArray = rootJsonObject.getJSONArray("holiday");
            for (int i = 0; i < holidayJsonArray.length(); i++) {

                //Create a temp Holiday object
                HolidayModel holidayModel = new HolidayModel();

                JSONObject jsonObject = holidayJsonArray.getJSONObject(i);
                //Get Holiday details
                holidayModel.setDate(jsonObject.getString("date"));
                holidayModel.setDay(jsonObject.getString("day"));
                holidayModel.setHoliday(jsonObject.getString("holiday"));
                holidayModel.setType(jsonObject.getString("type"));
                holidayModel.setComment(jsonObject.getString("comment"));
                holidayModel.setUrl(jsonObject.getString("url"));

                //add to List;
                holidayList.add(holidayModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String loadJSONFromAssets() {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("holiday.json");
            int size = inputStream.available();


            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}