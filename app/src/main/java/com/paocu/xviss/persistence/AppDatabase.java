package com.paocu.xviss.persistence;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.paocu.xviss.model.Travel;
import com.paocu.xviss.persistence.repository.TravelDAO;

@Database(entities = {Travel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TravelDAO travelDAO();
}

