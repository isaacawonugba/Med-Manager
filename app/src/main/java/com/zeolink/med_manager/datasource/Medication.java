package com.zeolink.med_manager.datasource;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by isaac on 4/16/2018.
 */

public class Medication implements Parcelable{

    private String id;
    private String name;
    private String description;
    private String frequency;
    private List<String> reminders;
    private String start_date;
    private String end_date;

    public Medication(){}

    public Medication(String id, String name, String description,
                      String frequency, List<String> reminders,
                      String start_date, String end_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.reminders = reminders;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    protected Medication(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        frequency = in.readString();
        reminders = in.createStringArrayList();
        start_date = in.readString();
        end_date = in.readString();
    }

    public static final Creator<Medication> CREATOR = new Creator<Medication>() {
        @Override
        public Medication createFromParcel(Parcel in) {
            return new Medication(in);
        }

        @Override
        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFrequency() {
        return frequency;
    }

    public List<String> getReminders() {
        return reminders;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeString(id);
        destination.writeString(name);
        destination.writeString(description);
        destination.writeString(frequency);
        destination.writeStringList(reminders);
        destination.writeString(start_date);
        destination.writeString(end_date);
    }
}
