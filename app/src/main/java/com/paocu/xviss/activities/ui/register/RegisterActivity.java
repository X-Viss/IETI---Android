package com.paocu.xviss.activities.ui.register;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paocu.xviss.R;
import com.paocu.xviss.activities.BaseActivity;
import com.paocu.xviss.activities.ui.login.LoginActivity;
import com.paocu.xviss.activities.ui.register.model.*;
import com.paocu.xviss.network.*;
import com.paocu.xviss.activities.ui.login.model.Token;
import com.paocu.xviss.activities.ui.login.service.AuthService;
import com.paocu.xviss.activities.ui.register.service.RegisterService;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextPassword2;
    private Spinner editSpinner;
    private CalendarView editBirthday;
    private EditText editPhone;
    private Date birthday;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        editSpinner = (Spinner) findViewById(R.id.spinner);
        String[] countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};
        ArrayAdapter<String>  countriesListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        editSpinner.setAdapter(countriesListAdapter);

        editBirthday = (CalendarView) findViewById(R.id.calendarView);
        editBirthday.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)  {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                birthday = cal.getTime();
                System.out.println("------------------------------------------------");
                System.out.println(birthday);
            }
        });
    }

    public void onClickSubmit(View view) {
        editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword2);
        editTextPassword2 = (EditText) findViewById(R.id.editTextTextPassword3);
        editPhone = (EditText) findViewById(R.id.editTextPhone);
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String password2 = editTextPassword2.getText().toString();
        String spinner = editSpinner.getSelectedItem().toString();
        Integer phone = Integer.parseInt(editPhone.getText().toString());

        if (email.isEmpty()) {
            editTextEmail.setError("no hay dato de email");
        } else if (password.isEmpty()) {
            editTextPassword.setError("no hay dato de password");
        } else if (password2.isEmpty()) {
            editTextPassword2.setError("no hay dato de password");
        } else if ((editPhone.getText().toString()).isEmpty()){
            editPhone.setError("no hay dato de telefono");
        } else if (!password.equals(password2)){
            editTextPassword2.setError("las contraseñas no son iguales");
        }else{
            RegisterService registerService= instance();
            AuthenticationRequest authenticationRequest = new AuthenticationRequest();
            authenticationRequest.setUserName(email);
            authenticationRequest.setPassword(password);
            authenticationRequest.setBirth(birthday);
            authenticationRequest.setCountry(spinner);
            authenticationRequest.setPhone(phone);
            onSubmitClickedVerify(registerService,authenticationRequest);
        }

    }


    public void onSubmitClickedVerify(RegisterService registerService, AuthenticationRequest authenticationRequest){
        Context aca = this;
        executorService.execute( new Runnable(){
            @Override
            public void run(){
                try{
                    Response<AutenticationResponse> prueba = registerService.subcribeClient(authenticationRequest).execute();
                    Response<AutenticationResponse> response = registerService.subcribeClient(authenticationRequest).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Gson gson = new GsonBuilder().create();
                                AutenticationResponse mError = null;
                                try {
                                     mError= gson.fromJson(response.errorBody().string(),AutenticationResponse.class);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                android.app.AlertDialog.Builder dialogo = new AlertDialog.Builder(aca);
                                dialogo.setTitle("Error");
                                dialogo.setMessage(mError.toString());
                                dialogo.show();
                            }

                        }
                    });
                }catch ( IOException e ){
                    e.printStackTrace();
                }
            }
        } );
    }

    public RegisterService instance(){
        RetrofitNetwork retrofitNetwork = new RetrofitNetwork();
        return (RegisterService) retrofitNetwork.getRetrofitService(RegisterService.class);
    }


}
