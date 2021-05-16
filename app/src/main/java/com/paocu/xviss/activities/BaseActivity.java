package com.paocu.xviss.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
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

import com.paocu.xviss.MainActivity;
import com.paocu.xviss.R;
import com.paocu.xviss.TravelListActivity;
import com.paocu.xviss.activities.adapter.TravelItemAdapter;
import com.paocu.xviss.model.Travel;
import com.paocu.xviss.network.RetrofitNetwork;
import com.paocu.xviss.network.requests.TravelService;
import com.paocu.xviss.services.TravelLiveService;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

/**
 * this activity shows all user travels
 */
public class BaseActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private RecyclerView recyclerView;

    private TravelLiveService travelLiveService;
    private TravelService travelService;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        //ADD TOKEN LOGIN
        RetrofitNetwork retrofitNetwork = new RetrofitNetwork("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYXZpZC52YXNxdWV6QG1haWwuZXNjdWVsYWluZy5lZHUuY28iLCJleHAiOjE2MjEyMTU4NzIsImlhdCI6MTYyMTE3OTg3Mn0.GTxZBUZSJrTOit67efq1hJpLhVbCoyu_y6HsapiyPqw");
        travelService = (TravelService) retrofitNetwork.getRetrofitService(TravelService.class);
    }


    private void loadTravels() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Travel> widgetContent = new ArrayList<Travel>();
                try {
                    Response<List<Travel>> response =
                            travelService.getTravels("david.vasquez@mail.escuelaing.edu.co").execute();

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
                        TravelItemAdapter travelItemAdapter = new TravelItemAdapter(widgetContent, getApplicationContext());
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
}