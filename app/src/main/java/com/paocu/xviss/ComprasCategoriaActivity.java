package com.paocu.xviss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.paocu.xviss.activities.ui.login.LoginActivity;
import com.paocu.xviss.adapter.InternAdapter;
import com.paocu.xviss.model.util.Question;
import com.paocu.xviss.model.util.Store;
import com.paocu.xviss.network.RetrofitNetwork;
import com.paocu.xviss.network.requests.BaseTravelService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;


public class ComprasCategoriaActivity extends AppCompatActivity {

    private List<Store> stores;
    private ArrayList<Store> storesLoad;
    private RetrofitNetwork retrofitNetwork;
    private RecyclerView reciclerView;
    private LinearLayoutManager managerLL;
    private InternAdapter mInternAdapter;
    private String categorySelected = "";
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );
    private BaseTravelService travelService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras_categoria);

        storesLoad = new ArrayList<Store>();
        storesLoad.add(new Store());
        reciclerView = (RecyclerView) findViewById(R.id.stores);
        reciclerView.setHasFixedSize(true);
        managerLL = new LinearLayoutManager(this);
        reciclerView.setLayoutManager(managerLL);

        mInternAdapter = new InternAdapter();
        reciclerView.setAdapter(mInternAdapter);

        categorySelected = "ACCESORIOS";

        retrofitNetwork =  new RetrofitNetwork(LoginActivity.getToken());
        travelService = (BaseTravelService) retrofitNetwork.getRetrofitService(BaseTravelService.class);

        executorService.execute( new Runnable() {
            @Override
            public void run() {
                Call<List<Store>> call = travelService.getStores(categorySelected);
                try {
                    stores = call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mInternAdapter.setDataSet((ArrayList<Store>) stores);
                    }
                });
            }
        });
    }
}