package com.paocu.xviss.activities.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.paocu.xviss.R;
import com.paocu.xviss.activities.ui.login.model.*;
import com.paocu.xviss.network.*;
import com.paocu.xviss.activities.*;
import com.paocu.xviss.activities.ui.register.*;
import com.paocu.xviss.activities.ui.login.service.AuthService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextPassword;
    private EditText editTextEmail;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );
    private static String Token;
    private static String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public void onClickLogin(View view) {
        editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        String login = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (login.isEmpty()) {
            editTextEmail.setError("no hay dato de email");
        } else if (password.isEmpty()) {
            editTextPassword.setError("no hay dato de password");
        } else {
            AuthService authService= instantion();
            onLoginClickedVerify(authService,login,password);
        }
    }

    public void onLoginClickedVerify(AuthService authService, String email, String password){
        executorService.execute( new Runnable(){
            @Override
            public void run(){
                try{
                    Email= email;
                    Response<Token> response =
                            authService.login(new LoginModel( email, password)).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {
                                Token token = response.body();

                                SharedPreferences sharedPref = getSharedPreferences(getString( R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("TOKEN_KEY", token.getAccessToken());
                                setToken(token.getAccessToken());
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
                                startActivity(intent);
                            } else {
                                editTextEmail.setError("Correo y/o contraseña incorrecta");
                                editTextPassword.setError("Correo y/o contraseña incorrecta");
                            }
                        }
                    });
                }catch ( IOException e ){
                    e.printStackTrace();
                }
            }
        } );
    }

    public AuthService instantion(){
        RetrofitNetwork retrofitNetwork = new RetrofitNetwork();
        return (AuthService) retrofitNetwork.getRetrofitService(AuthService.class);
    }

    public static String getToken(){
        return Token;
    }
    private static void setToken(String token){
        Token = token;
    }
    public static String getEmail(){
        return Email;
    }

    public void onClickLoginARegistro(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}