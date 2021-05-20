package com.paocu.xviss;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.paocu.xviss.activities.ui.login.LoginActivity;
import com.paocu.xviss.model.GeneritToUserRolWeatherOrCategory;
import com.paocu.xviss.model.util.Question;
import com.paocu.xviss.network.RetrofitNetwork;
import com.paocu.xviss.network.requests.BaseTravelService;
import com.paocu.xviss.network.requests.TravelService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class QuestListActivity extends AppCompatActivity {

    private ArrayList<String> quests;
    private Spinner spinner;
    private LinearLayout linearLayout;
    private ArrayAdapter<String> questListAdapter;
    private List<Question> questions;
    private RetrofitNetwork retrofitNetwork;
    private TextView textView;
    private TextView answer;
    private BaseTravelService travelService;
    private String valor;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_list);

        retrofitNetwork =  new RetrofitNetwork(LoginActivity.getToken());
        travelService = (BaseTravelService) retrofitNetwork.getRetrofitService(BaseTravelService.class);

        quests = new ArrayList<String>();
        spinner = (Spinner) findViewById(R.id.questionlists);
        answer = (TextView) findViewById(R.id.answer);
        linearLayout= (LinearLayout) findViewById(R.id.mainlayout);
        textView = new TextView(getApplicationContext());

        quests.add("Selecciona");
        executorService.execute( new Runnable() {
            @Override
            public void run() {
                Call<List<Question>> call = travelService.getQuestions();
                try {
                    questions = call.execute().body();
                    
                    for (Question qt : questions) {
                        quests.add(qt.getQuest());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("ACA");
            }
        });
        questListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quests);
        spinner.setAdapter(questListAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String valor = (String) spinner.getAdapter().getItem(position);
                if(position!=0){
                    answer.setText(questions.get(position-1).getAnswer());
                }else{
                    answer.setText("");
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                System.out.println("vacio");
            }
        });
        System.out.println(valor);
    }

    private void generateTitle(String title){
        int color = Color.parseColor("#53c0c6");

        textView.setText(title);
        textView.setTextColor(color);
        textView.setTextSize(30);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                linearLayout.addView(textView);
            }
        });
    }
}