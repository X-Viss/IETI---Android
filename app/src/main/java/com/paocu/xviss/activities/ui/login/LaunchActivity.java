package com.paocu.xviss.activities.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.paocu.xviss.activities.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paocu.xviss.R;

public class LaunchActivity
        extends AppCompatActivity
{

    public static final String TOKEN_KEY = "TOKEN_KEY";

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        SharedPreferences sharedPref =
                getSharedPreferences( getString( R.string.preference_file_key), Context.MODE_PRIVATE );

        if(sharedPref.contains(TOKEN_KEY)){
            Intent intent = new Intent(this, BaseActivity.class);
            startActivity(intent);
            this.finish();
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}