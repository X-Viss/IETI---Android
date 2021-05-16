package com.paocu.xviss.services;

import android.content.Context;

import androidx.room.Room;

import com.paocu.xviss.model.Travel;
import com.paocu.xviss.persistence.AppDatabase;
import com.paocu.xviss.persistence.repository.TravelDAO;

import java.util.ArrayList;
import java.util.List;


/**
 * @author alejovasquero
 * This is just an experimental class. All observers and live features are not included...YET ;)
 */
public class TravelLiveService {

    private TravelDAO travelDAO;

    public TravelLiveService(Context context){
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "database-name").build();
        travelDAO = db.travelDAO();
    }

    public void saveAll(List<Travel> tasks){
        travelDAO.insertAll(tasks);
    }

    public List<Travel> getAll() {
        final List<Travel> travels = new ArrayList<Travel>();
        travels.addAll(travelDAO.getAll());
        System.out.println("RESULT " + travels);
        return travels;
    }

}
