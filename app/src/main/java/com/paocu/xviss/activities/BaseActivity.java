package com.paocu.xviss.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paocu.xviss.CreateTravelActivity;
import com.paocu.xviss.MainActivity;
import com.paocu.xviss.R;
import com.paocu.xviss.TravelListActivity;
import com.paocu.xviss.activities.adapter.ListItemsListener;
import com.paocu.xviss.activities.adapter.TravelItemAdapter;
import com.paocu.xviss.activities.ui.login.LoginActivity;
import com.paocu.xviss.model.Travel;
import com.paocu.xviss.network.RetrofitNetwork;
import com.paocu.xviss.activities.ui.login.*;
import com.paocu.xviss.network.requests.TravelService;
import com.paocu.xviss.services.TravelLiveService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

/**
 * this activity shows all user travels
 */
public class BaseActivity extends AppCompatActivity implements ListItemsListener {

    private AppBarConfiguration mAppBarConfiguration;

    private RecyclerView recyclerView;
    private TravelItemAdapter travelItemAdapter;

    private TravelLiveService travelLiveService;
    private TravelService travelService;
    private List<Travel> allTravels;
    private ListItemsListener listener = this;

    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("CREATING");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO REDIRECT TO CREATE TRAVEL VIEW
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.nav_home){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else if(id == R.id.nav_add){
                Intent intent = new Intent(this, CreateTravelActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_log_out){
                onClickLogout();
            }
            System.out.println(item.getItemId());
            return true;
        }
        );

        recyclerView = findViewById(R.id.travelistrecyclerView);
        setUpServices();
        loadTravels();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void openTravelList(MenuItem item){
        Intent intent = new Intent(this, TravelListActivity.class);
        startActivity(intent);
    }

    public void setUpServices(){
        travelLiveService = new TravelLiveService(getApplicationContext());
        //TODO ADD TOKEN LOGIN
        RetrofitNetwork retrofitNetwork = new RetrofitNetwork(LoginActivity.getToken());
        travelService = (TravelService) retrofitNetwork.getRetrofitService(TravelService.class);
    }


    private void loadTravels() {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Travel> widgetContent = new ArrayList<Travel>();
                try {
                    Response<List<Travel>> response =
                            travelService.getTravels(LoginActivity.getEmail()).execute();

                    if(response.isSuccessful()){
                        widgetContent.addAll(response.body());
                        travelLiveService.saveAll(widgetContent);
                    } else {
                        widgetContent.addAll(travelLiveService.getAll());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    widgetContent.addAll(travelLiveService.getAll());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allTravels = widgetContent;
                        travelItemAdapter = new TravelItemAdapter(allTravels, getApplicationContext());
                        travelItemAdapter.setListItemsListener(listener);
                        recyclerView.setAdapter(travelItemAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                        RecyclerView.ItemDecoration itemDecoration = new
                                DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                        recyclerView.addItemDecoration(itemDecoration);
                    }
                });
            }
        });
    }

    @Override
    public void onClickDelete(int position) {
        deleleConfirmationHandler(position);
    }


    private void deleleConfirmationHandler(int position){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Confirmación");
        dialogo.setMessage("¿ Estás seguro de que quieres borrar tu viaje ?");
        dialogo.setCancelable(true);
        dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                deleteTravel(position);
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {

            }
        });
        dialogo.show();
    }

    public void deleteTravel(int position){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Travel travel = allTravels.get(position);
                try {
                    View view = findViewById(R.id.drawer_layout);
                    Snackbar.make(view,  "Borrando", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Response<Void> response =
                            travelService.deleteTravel(travel.getTravelId()).execute();
                    if(response.isSuccessful()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                allTravels.remove(position);
                                travelItemAdapter.notifyDataSetChanged();
                                travelItemAdapter.notifyItemRemoved(position);
                                travelItemAdapter.notifyItemRangeChanged(position, allTravels.size());
                                View view = findViewById(R.id.drawer_layout);
                                Snackbar.make(view,  "Viaje borrado", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onClickLogout(){
        SharedPreferences sharedPref = getSharedPreferences(getString( R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("TOKEN_KEY");
        editor.apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}