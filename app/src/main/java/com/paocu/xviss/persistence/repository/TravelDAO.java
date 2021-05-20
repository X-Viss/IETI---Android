package com.paocu.xviss.persistence.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.paocu.xviss.model.Travel;

import java.util.List;

@Dao
public interface TravelDAO {

    @Query("SELECT * FROM travel")
    List<Travel> getAll();

    @Query("SELECT * FROM travel WHERE travelId = :id")
    Travel loadByTravelId(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Travel> tasks);

    @Delete
    void delete(Travel task);

}
