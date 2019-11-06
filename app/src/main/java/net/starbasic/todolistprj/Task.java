package net.starbasic.todolistprj;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Task implements Parcelable, Serializable {
    private int category;
    private String content;
    private int priority;
    private int hours;
    private int minutes;
    private int day;
    private int month;
    private int year;

    protected Task(Parcel in) {
        category = in.readInt();
        content = in.readString();
        priority = in.readInt();
        hours = in.readInt();
        minutes = in.readInt();
        day = in.readInt();
        month = in.readInt();
        year = in.readInt();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String text) {
        this.content = text;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Task(int category, String content, int priority, int hours, int minutes, int day, int month, int year) {
        this.category = category;
        this.content = content;
        this.priority = priority;
        this.hours = hours;
        this.minutes = minutes;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Task(int category, String content,  int hours, int minutes, int day, int month, int year) {
        this.category = category;
        this.content = content;
        this.priority = 10;
        this.hours = hours;
        this.minutes = minutes;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Task(int category, String content, int priority, int day, int month, int year) {
        this.category = category;
        this.content = content;
        this.priority = priority;
        this.hours = 0;
        this.minutes = 0;
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public Task(int category, String content,  int day, int month, int year) {
        this.category = category;
        this.content = content;
        this.priority = 10;
        this.hours = 0;
        this.minutes = 0;
        this.day = day;
        this.month = month;
        this.year = year;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(category);
        dest.writeString(content);
        dest.writeInt(priority);
        dest.writeInt(hours);
        dest.writeInt(minutes);
        dest.writeInt(day);
        dest.writeInt(month);
        dest.writeInt(year);
    }
}
