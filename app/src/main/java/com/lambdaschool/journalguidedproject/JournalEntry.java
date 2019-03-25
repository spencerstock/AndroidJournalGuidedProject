package com.lambdaschool.journalguidedproject;

import android.net.Uri;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JournalEntry implements Serializable {
    public static final String TAG = "JournalEntry";
    public static final int INVALID_ID = -1;

    private String date, entryText, image;
    private int dayRating, id;

    public JournalEntry(String date, String entryText, String image, int dayRating, int id) {
        this.date = date;
        this.entryText = entryText;
        this.image = image;
        this.dayRating = dayRating;
        this.id = id;
    }

    public JournalEntry(int id) {
        this.id = id;
        this.entryText = "";
        this.image = "";

        initializeDate();
    }

    public JournalEntry(String csvString) {
//        String.format("%d,%s,%d,%s,%s", id, date, dayRating, entryText, image);
        String[] values = csvString.split(",");
        if(values.length == 5) {
            this.id = Integer.parseInt(values[0]);
            this.date = values[1];
            this.dayRating = Integer.parseInt(values[2]);
            this.entryText = values[3].replace("~@", ",");
            this.image = values[4].equals("unused") ? "": values[4];
        }
    }

    String toCsvString() {
        return String.format("%d,%s,%d,%s,%s",
                             id,
                             date,
                             dayRating,
                             entryText.replace(",", "~@"),
                             image == "" ? "unused": image);
    }

    private void initializeDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date       date       = new Date();

        this.setDate(dateFormat.format(date));
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    public Uri getImage() {
        if(!image.equals("")) {
            return Uri.parse(image);
        } else {
            return null;
        }
    }

    public void setImage(Uri imageUri) {
        this.image = imageUri.toString();
    }

    public int getDayRating() {
        return dayRating;
    }

    public void setDayRating(int dayRating) {
        this.dayRating = dayRating;
    }

    public int getId() {
        return id;
    }
}
