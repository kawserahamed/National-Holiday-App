package org.primeit.governmentholidays.viewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.primeit.governmentholidays.model.HolidayModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HolidayViewModel extends ViewModel {
    public MutableLiveData<List<HolidayModel>> holidayLiveList = new MutableLiveData<>();
    public MutableLiveData<List<HolidayModel>> holidayFilterLiveList = new MutableLiveData<>();
    private final ArrayList<HolidayModel> holidayList = new ArrayList<>();
    private final ArrayList<HolidayModel> holidayFilterList = new ArrayList<>();

    public void getHolidayList(Context context) {

        // JSON Parsing
        String myJSONStr = loadJSONFromAssets(context);

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

            holidayLiveList.postValue(holidayList);

            Log.d("ddddd", "getHolidayList: " + holidayList.size());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getHolidayFilterList(String compareDate, boolean isDate) {
        Log.d("ViewModel", "getHolidayFilterList: "+compareDate);
        Log.d("ViewModel", "getHolidayFilterList: "+isDate);
        holidayFilterList.clear();

        for (int i = 0; i < holidayList.size(); i++) {

            HolidayModel holiday = holidayList.get(i);

            Log.d("ViewModel", "compareDate: " + compareDate + " :holiday date: " + holiday.getDate());

            if (isDate) {
                Log.d("ViewModel", "Hit: ");
                if (holiday.getDate().equals(compareDate)) {
                    Log.d("ViewModel", "isDate:true ");
                    holidayFilterList.add(holiday);
                }
            } else {
                Log.d("ViewModel", "Hit Else: ");
                if (holiday.getDate().split("-")[1].equals(compareDate)) {
                    Log.d("ViewModel", "isDate: false");
                    holidayFilterList.add(holiday);
                }
            }
        }

        holidayFilterLiveList.postValue(holidayFilterList);
    }

    private String loadJSONFromAssets(Context context) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open("holiday.json");
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
