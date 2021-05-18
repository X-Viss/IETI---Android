package com.paocu.xviss;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.paocu.xviss.activities.ui.login.LoginActivity;
import com.paocu.xviss.model.GeneritToUserRolWeatherOrCategory;
import com.paocu.xviss.model.Travel;
import com.paocu.xviss.model.util.Country;
import com.paocu.xviss.network.RetrofitNetwork;
import com.paocu.xviss.network.requests.CreateTravelServicce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class CreateTravelActivity extends AppCompatActivity {

    private String idOfCurrentNewTripPage = "";
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );
    RetrofitNetwork retrofitNetwork;
    private Spinner spinner;
    private CheckBox mascota;
    private CheckBox mochilero;
    private CheckBox pareja;
    private CheckBox turista;
    private CheckBox trabajo;
    private String[] countries;
    private ArrayAdapter<String> countriesListAdapter;
    private CreateTravelServicce createTravelServicce;
    private List<GeneritToUserRolWeatherOrCategory> generitToUserRolWeatherOrCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_travel);

        retrofitNetwork =  new RetrofitNetwork(LoginActivity.getToken());
        createTravelServicce = (CreateTravelServicce) retrofitNetwork.getRetrofitService(CreateTravelServicce.class);
        spinner = (Spinner) findViewById(R.id.countryList);
        countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};
        countriesListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        spinner.setAdapter(countriesListAdapter);
    }

    public void rolSelectionClic(View view){
        mascota = (CheckBox) findViewById(R.id.mascota);
        mochilero = (CheckBox) findViewById(R.id.mochilero);
        pareja = (CheckBox) findViewById(R.id.pareja);
        turista = (CheckBox) findViewById(R.id.turista);
        trabajo = (CheckBox) findViewById(R.id.trabajo);

        view.setEnabled( false );
        executorService.execute( new Runnable() {
            @Override
            public void run() {
                try{
                    List<GeneritToUserRolWeatherOrCategory> rolList = new ArrayList<>();
                    List<GeneritToUserRolWeatherOrCategory> generitToUserRolWeatherOrCategoryList = fillListRol( (ArrayList<GeneritToUserRolWeatherOrCategory>) rolList);
                    Call<String> call = createTravelServicce.postSelectTravelerRol(generitToUserRolWeatherOrCategoryList, idOfCurrentNewTripPage);
                    System.out.println("Qué paso ome perro ome el call");
                    System.out.println(call);
                    Response<String> response = call.execute();
                    System.out.println("Qué paso ome perro ome");
                    System.out.println(response);
                    if ( response.isSuccessful() ) {
                        idOfCurrentNewTripPage = response.body().toString();
                        System.out.println("Ome entre a ver que esta pasando y ver si pudo hacer el post");
                        System.out.println(idOfCurrentNewTripPage);
                        String message = "Guardado con éxito!";
                        System.out.println(message);
                    }else{
                        String message = "No se pudo crear el rol, intenta de nuevo!";
                        System.out.println(message);
                    }
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void countrySelectionClic(View view){

        view.setEnabled( false );
        executorService.execute( new Runnable() {
            @Override
            public void run() {
                try{
                    String country = spinner.getSelectedItem().toString();
                    Call<Void> call = createTravelServicce.putSelectDestiny(new Country(country), idOfCurrentNewTripPage);
                    Response<Void> response = call.execute();
                    if ( response.isSuccessful() ) {
                        System.out.println("Ome entre a ver que esta pasando y ver si pudo hacer el put de pais");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println(country);
                        String message = "Guardado con éxito!";
                        System.out.println(message);
                    }else{
                        String message = "No se pudo guardar el país, intenta de nuevo!";
                        System.out.println(message);
                    }
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<GeneritToUserRolWeatherOrCategory> fillListRol(List<GeneritToUserRolWeatherOrCategory> rolList){
        GeneritToUserRolWeatherOrCategory rolMascota = new GeneritToUserRolWeatherOrCategory(mascota.isChecked(), "Viaje con mascotas","https://i.ibb.co/6JRjSwT/mascotas.jpg");
        GeneritToUserRolWeatherOrCategory rolMochilero = new GeneritToUserRolWeatherOrCategory(mochilero.isChecked(),"Viaje como Mochilero", "https://i.ibb.co/dKFjmSv/mochilero.jpg");
        GeneritToUserRolWeatherOrCategory rolPareja = new GeneritToUserRolWeatherOrCategory(pareja.isChecked(), "Viaje en pareja", "https://i.ibb.co/3mJhzz1/parejas.jpg");
        GeneritToUserRolWeatherOrCategory rolTurista = new GeneritToUserRolWeatherOrCategory(turista.isChecked(), "Viaje como turista", "https://i.ibb.co/vdJ8ZQc/turistas.jpg");
        GeneritToUserRolWeatherOrCategory rolTrabajo = new GeneritToUserRolWeatherOrCategory(trabajo.isChecked(), "Viaje de trabajo", "https://i.ibb.co/88ckWqL/viaje-De-Negocios.jpg");
        rolList.add(rolMascota);
        rolList.add(rolMochilero);
        rolList.add(rolPareja);
        rolList.add(rolTurista);
        rolList.add(rolTrabajo);
        return rolList;
    }

}
