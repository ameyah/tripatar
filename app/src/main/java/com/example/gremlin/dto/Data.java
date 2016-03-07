package com.example.gremlin.dto;

import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Vishal on 05-Mar-16.
 */
public class Data implements Serializable{
    private String title;
    private String description;
    private Date date;
    private String duration ;
    private String price;
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public File getCoverFile() {
        return coverFile;
    }

    public void setCoverFile(File coverFile) {
        this.coverFile = coverFile;
    }

    private File coverFile;


    public void setDate(Date date) {
        this.date = date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public String getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
