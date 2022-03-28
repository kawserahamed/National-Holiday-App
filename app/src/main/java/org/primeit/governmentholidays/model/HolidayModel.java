package org.primeit.governmentholidays.model;

public class HolidayModel {

    private String date;
    private String day;
    private String holiday;
    private String type;
    private String comment;
    private String url;


    public HolidayModel(String date, String day, String holiday, String type, String comment, String url) {
        this.date = date;
        this.day = day;
        this.holiday = holiday;
        this.type = type;
        this.comment = comment;
        this.url = url;
    }

    public HolidayModel() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
