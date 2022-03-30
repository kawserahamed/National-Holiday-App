package org.primeit.governmentholidays.viewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.primeit.governmentholidays.model.HolidayModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HolidayViewModel extends ViewModel {

    ArrayList<HolidayModel> holidayList;

    public HolidayViewModel( ArrayList<HolidayModel> holidayList) {
        this.holidayList = holidayList;
    }

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
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
