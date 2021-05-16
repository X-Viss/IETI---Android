package com.paocu.xviss.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.paocu.xviss.model.util.DateConverter;

import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Travel {

    private String id;
    private String user;

    private String destiny;


    @PrimaryKey
    @NonNull
    private String travelId;

    private String title;
    private String description;
    private Date dueDate;

    public Travel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("{ title: %s , date: %s, description: %s}", title, dueDate == null ? "NULL": dueDate.toString(), description);
    }
}
