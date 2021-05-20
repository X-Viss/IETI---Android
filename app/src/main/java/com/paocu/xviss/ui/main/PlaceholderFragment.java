package com.paocu.xviss.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paocu.xviss.R;
import com.paocu.xviss.activities.ui.login.LoginActivity;
import com.paocu.xviss.adapter.ElementAdapter;
import com.paocu.xviss.adapter.InternAdapter;
import com.paocu.xviss.model.GeneritToUserRolWeatherOrCategory;
import com.paocu.xviss.model.Travel;
import com.paocu.xviss.model.TravelOnLoad;
import com.paocu.xviss.model.util.Store;
import com.paocu.xviss.network.RetrofitNetwork;
import com.paocu.xviss.network.requests.BaseTravelService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private int actual;
    private ArrayList<GeneritToUserRolWeatherOrCategory> elementsList;
    private String travelId;
    private String categorySelected;
    private TravelOnLoad travel;

    private RetrofitNetwork retrofitNetwork;
    private RecyclerView reciclerView;
    private LinearLayoutManager managerLL;
    private InternAdapter mInternAdapter;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );
    private BaseTravelService travelService;

    private PageViewModel pageViewModel;

    public PlaceholderFragment(){
        elementsList= new ArrayList<>();
    }

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        travel = new TravelOnLoad();
        elementsList= new ArrayList<>();
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            actual =index;
        }
        travelId = "ce482bbc-b558-4107-95db-0bf4e1bd14f1";
        retrofitNetwork =  new RetrofitNetwork(LoginActivity.getToken());
        travelService = (BaseTravelService) retrofitNetwork.getRetrofitService(BaseTravelService.class);
        executorService.execute( new Runnable() {
            @Override
            public void run() {
                Call<TravelOnLoad> call = travelService.getTravel(travelId);
                try {
                    travel = call.execute().body();
                    switch (actual){
                        case 1 : elementsList.addAll(travel.getAccessoriesList());categorySelected = "ACCESORIOS";break;
                        case 2 : elementsList.addAll(travel.getClothesList());categorySelected = "ROPA";break;
                        case 3 : elementsList.addAll(travel.getCleanlinessList());categorySelected = "ASEO";break;
                        case 4 : elementsList.addAll(travel.getMedicineList());categorySelected = "MEDICINE";break;
                        case 5 : elementsList.addAll(travel.getOnHandList());categorySelected = "A LA MANO";break;
                        case 6 : elementsList.addAll(travel.getSeveralList()); categorySelected = "VARIOS";break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ElementAdapter adapter = new ElementAdapter(elementsList);
                        reciclerView.setAdapter(adapter);
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_elementos_list, container, false);
        reciclerView = root.findViewById(R.id.itemsViaje);
        reciclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        executorService.execute( new Runnable() {
            @Override
            public void run() {
                Call<TravelOnLoad> call = travelService.getTravel(travelId);
                try {
                    travel = call.execute().body();
                    switch (actual){
                        case 1 : elementsList.addAll(travel.getAccessoriesList());categorySelected = "ACCESORIOS";break;
                        case 2 : elementsList.addAll(travel.getClothesList());categorySelected = "ROPA";break;
                        case 3 : elementsList.addAll(travel.getCleanlinessList());categorySelected = "ASEO";break;
                        case 4 : elementsList.addAll(travel.getMedicineList());categorySelected = "MEDICINE";break;
                        case 5 : elementsList.addAll(travel.getOnHandList());categorySelected = "A LA MANO";break;
                        case 6 : elementsList.addAll(travel.getSeveralList()); categorySelected = "VARIOS";break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ElementAdapter adapter = new ElementAdapter(elementsList);
                        reciclerView.setAdapter(adapter);
                    }
                });
            }
        });
        return root;
    }

    private void llenarLista(){
        switch (actual){
            case 1 : elementsList.addAll(travel.getAccessoriesList());categorySelected = "ACCESORIOS";break;
            case 2 : elementsList.addAll(travel.getClothesList());categorySelected = "ROPA";break;
            case 3 : elementsList.addAll(travel.getCleanlinessList());categorySelected = "ASEO";break;
            case 4 : elementsList.addAll(travel.getMedicineList());categorySelected = "MEDICINE";break;
            case 5 : elementsList.addAll(travel.getOnHandList());categorySelected = "A LA MANO";break;
            case 6 : elementsList.addAll(travel.getSeveralList()); categorySelected = "VARIOS";break;
        }
    }

}