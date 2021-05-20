package com.paocu.xviss.activities.ui.register;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paocu.xviss.activities.ui.login.LoginActivity;
import com.paocu.xviss.activities.ui.login.model.*;
import com.paocu.xviss.activities.ui.register.model.UserModel;
import com.paocu.xviss.activities.ui.register.service.RegisterService;
import com.paocu.xviss.network.*;
import com.paocu.xviss.activities.*;
import com.paocu.xviss.activities.ui.register.*;
import com.paocu.xviss.activities.ui.login.service.AuthService;
import com.paocu.xviss.R;
import com.paocu.xviss.activities.ui.register.model.AutenticationResponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class EditUser extends AppCompatActivity {

    private TextView editTextEmail;
    private EditText password;
    private EditText phone;
    private CalendarView birth;
    private Spinner country;
    private Spinner editSpinner;
    private String[] countries;
    private Date birthday;
    private UserModel user;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user);

        RegisterService registerService = instance();
        onChangeClickedVerify(registerService);

        editSpinner = (Spinner) findViewById(R.id.spinneredit);
        countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};
        ArrayAdapter<String> countriesListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        editSpinner.setAdapter(countriesListAdapter);

        editTextEmail = (TextView) findViewById(R.id.editEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword2edit);
        phone = (EditText) findViewById(R.id.editTextPhoneedit);
        birth = (CalendarView) findViewById(R.id.calendarViewedit);

        birth = (CalendarView) findViewById(R.id.calendarView);
        birth.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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

    public void onClickChange(View view){
        user.setBirth(birthday);
    }

    public void onChangeClickedVerify(RegisterService registerService){
        Context aca = this;
        executorService.execute( new Runnable(){
            @Override
            public void run(){
                try{
                    Response<UserModel> response = registerService.getUserData().execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {
                                user = response.body();
                                editTextEmail.setHint(user.getEmail());
                                password.setHint(user.getPassword());
                                for (int i=0; i< countries.length; i++){
                                    if (countries[i].equals(user.getCountry())) {
                                        editSpinner.setSelection(i);
                                        break;
                                    }
                                }
                                birth.setDate(user.getBirth().getTime());
                                phone.setHint(user.getPhone().toString());
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

    public void cambioVariables(UserModel user){
        System.out.println(user.toString());
        //EditText editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress2edit);
        //editTextEmail.setText(user.getEmail());

    }

    public RegisterService instance(){
        RetrofitNetwork retrofitNetwork = new RetrofitNetwork(LoginActivity.getToken());
        return (RegisterService) retrofitNetwork.getRetrofitService(RegisterService.class);
    }
}
