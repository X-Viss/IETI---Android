package com.paocu.xviss;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.paocu.xviss.activities.ui.login.LoginActivity;
import com.paocu.xviss.model.GeneritToUserRolWeatherOrCategory;
import com.paocu.xviss.model.Travel;
import com.paocu.xviss.model.util.Country;
import com.paocu.xviss.model.util.ListCategories;
import com.paocu.xviss.network.RetrofitNetwork;
import com.paocu.xviss.network.requests.CreateTravelServicce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class CreateTravelActivity extends AppCompatActivity {

    private String[] countries;
    private Spinner spinner;
    private Button fecha;
    private int dia, mes, año, itemId=0, buttonId=0;
    private EditText titulo, mostrarFecha;
    public LinearLayout linearLayout;
    public ListCategories listCategories;
    private RetrofitNetwork retrofitNetwork;
    private String idOfCurrentNewTripPage = "";
    private ArrayAdapter<String> countriesListAdapter;
    private CreateTravelServicce createTravelServicce;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );
    private List<GeneritToUserRolWeatherOrCategory> accesories, onHand, cleanliness, medicine, clothes;
    private CheckBox mascota, mochilero, pareja, turista, trabajo, invierno, verano, otoño, primavera, generalItemsCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_travel);

        accesories = new ArrayList<>();
        onHand = new ArrayList<>();
        cleanliness = new ArrayList<>();
        medicine = new ArrayList<>();
        clothes = new ArrayList<>();
        linearLayout = (LinearLayout) findViewById(R.id.contenedorItems);
        fecha = (Button) findViewById(R.id.guardar_fecha);
        titulo = (EditText) findViewById(R.id.guardar_titulo);
        mostrarFecha = (EditText) findViewById(R.id.mostrar_fecha);
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
                    Response<String> response = call.execute();
                    if ( response.isSuccessful() ) {
                        idOfCurrentNewTripPage = response.body().toString();
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

    public void dateAndTitleSelect(View view){

        view.setEnabled( false );
        executorService.execute( new Runnable() {
            @Override
            public void run() {
                try{
                    String tituloReal = titulo.getText().toString();
                    String date =  mostrarFecha.getText().toString()+"T18:49:00.000+00:00";
                    Call<Void> call = createTravelServicce.putTitleAndHour(tituloReal, date, idOfCurrentNewTripPage);
                    Response<Void> response = call.execute();
                    if ( response.isSuccessful() ) {
                        String message = "Guardado con éxito!";
                        System.out.println(message);
                    }else{
                        String message = "No se pudo guardar el título ni hora, intenta de nuevo!";
                        System.out.println(message);
                    }
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void weatherSelect(View view){
        invierno = (CheckBox) findViewById(R.id.invierno);
        verano = (CheckBox) findViewById(R.id.verano);
        primavera = (CheckBox) findViewById(R.id.primavera);
        otoño = (CheckBox) findViewById(R.id.otono);

        view.setEnabled( false );
        executorService.execute( new Runnable() {
            @Override
            public void run() {
                try{
                    List<GeneritToUserRolWeatherOrCategory> weatherList = new ArrayList<>();
                    List<GeneritToUserRolWeatherOrCategory> generitToUserRolWeatherOrCategoryList = fillListWeather( (ArrayList<GeneritToUserRolWeatherOrCategory>) weatherList);
                    Call<ListCategories> call = createTravelServicce.putWeatherByUserRolSelected(generitToUserRolWeatherOrCategoryList, idOfCurrentNewTripPage);
                    Response<ListCategories> response = call.execute();
                    if ( response.isSuccessful() ) {
                        listCategories = response.body();
                        String message = "Guardado con éxito!";
                        System.out.println(message);
                    }else{
                        String message = "No se pudo guardar el clima, intenta de nuevo!";
                        System.out.println(message);
                    }
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showItems(View view){
        genaerateItems(view);
    }

    public void buttonFecha(View view){
        final Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        año = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mostrarFecha.setText(year+"-"+(month+1)+"-"+dayOfMonth);
            }
        }, año, mes, dia);
        datePickerDialog.show();
    }

    public List<GeneritToUserRolWeatherOrCategory> fillListWeather(List<GeneritToUserRolWeatherOrCategory> weatherList){
        GeneritToUserRolWeatherOrCategory climaInvierno = new GeneritToUserRolWeatherOrCategory(invierno.isChecked(), "Invierno","https://i.ibb.co/ssC1Wz1/KONICA-MINOLTA-DIGITAL-CAMERA.jpg");
        GeneritToUserRolWeatherOrCategory climaVerano = new GeneritToUserRolWeatherOrCategory(verano.isChecked(),"Verano", "https://i.ibb.co/8BHqHSC/verano.jpg");
        GeneritToUserRolWeatherOrCategory climaPrimavera = new GeneritToUserRolWeatherOrCategory(primavera.isChecked(), "Primavera", "https://i.ibb.co/rQ6K4Pp/primavera.jpg");
        GeneritToUserRolWeatherOrCategory climaOtoño = new GeneritToUserRolWeatherOrCategory(otoño.isChecked(), "Otoño", "https://i.ibb.co/XsY2PSt/oto-o.jpg");
        weatherList.add(climaInvierno);
        weatherList.add(climaOtoño);
        weatherList.add(climaPrimavera);
        weatherList.add(climaVerano);
        return weatherList;
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

    public void genaerateItems(View view){
        generateAccesoriesChecks(listCategories.getAccesoriesList(), view);
        generateClenanestChecks(listCategories.getCleannessList(), view);
        generateClothesChecks(listCategories.getClothesList(), view);
        generateMedicineChecks(listCategories.getHealthList(), view);
        generateOnHandChecks(listCategories.getOnHandList(), view);
    }
    private void generateClothesChecks(List<String> clothesList, View view) {
        generateTitle("Ropa");
        for(int i=0; i<clothesList.size(); i++){
            String data = clothesList.get(i);
            System.out.println(data);
            accesories.add(new GeneritToUserRolWeatherOrCategory(false, data, "https://i.ibb.co/XLqTpvs/fondo.jp"));
            generateNewChecbox(data, view);
        }
        generateButton("GUARDAR ROPA");
    }

    private void generateMedicineChecks(List<String> medicineList, View view) {
        generateTitle("Medicina");
        for(int i=0; i<medicineList.size(); i++){
            String data = medicineList.get(i);
            System.out.println(data);
            accesories.add(new GeneritToUserRolWeatherOrCategory(false, data, "https://i.ibb.co/XLqTpvs/fondo.jp"));
            generateNewChecbox(data, view);
        }
        generateButton("GUARDAR MEDICINA");
    }

    private void generateClenanestChecks(List<String> cleannessList, View view ) {
        generateTitle("Aseo");
        for(int i=0; i<cleannessList.size(); i++){
            String data = cleannessList.get(i);
            System.out.println(data);
            accesories.add(new GeneritToUserRolWeatherOrCategory(false, data, "https://i.ibb.co/XLqTpvs/fondo.jp"));
            generateNewChecbox(data, view);
        }
        generateButton("GUARDAR ASEO");
    }

    private void generateOnHandChecks(List<String> onHandList, View view ) {
        generateTitle("A La Mano");
        for(int i=0; i<onHandList.size(); i++){
            String data = onHandList.get(i);
            System.out.println(data);
            accesories.add(new GeneritToUserRolWeatherOrCategory(false, data, "https://i.ibb.co/XLqTpvs/fondo.jp"));
            generateNewChecbox(data, view);
        }
        generateButton("GUARDAR A LA MANO");
    }

    private void generateAccesoriesChecks(List<String> accesoriesList, View view ) {
        generateTitle("Accesorios");
        for(int i=0; i<accesoriesList.size(); i++){
            String data = accesoriesList.get(i);
            System.out.println(data);
            accesories.add(new GeneritToUserRolWeatherOrCategory(false, data, "https://i.ibb.co/XLqTpvs/fondo.jp"));
            generateNewChecbox(data, view);
        }
        generateButton("GUARDAR ACCESORIOS");
    }

    private void generateButton(String message){
        int color = Color.parseColor("#91C2EA");
        Button button = new Button(getApplicationContext());
        button.setId(buttonId++);
        button.setBackgroundColor(color);
        button.setText(message);
        button.setOnClickListener(getOnClickButton(button));
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                linearLayout.addView(button);
            }
        });
    }

    private void generateTitle(String title){
        int color = Color.parseColor("#53c0c6");
        TextView textView = new TextView(getApplicationContext());
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

    private void generateNewChecbox(String data, View view) {
        generalItemsCheckbox = new CheckBox(getApplicationContext());
        generalItemsCheckbox.setId(itemId++);
        generalItemsCheckbox.setText(data);
        generalItemsCheckbox.setOnClickListener(getOnClickChecBox(generalItemsCheckbox));
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                linearLayout.addView(generalItemsCheckbox);
            }
        });
    }

    private View.OnClickListener getOnClickChecBox(final Button button) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("on click was the id: "+ button.getId() + "with the opcion: "+ button.getText().toString());
            }
        };
    }


    private View.OnClickListener getOnClickButton(final Button button) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("on click was the id: "+ button.getId() + "with the opcion: "+ button.getText().toString());
            }
        };
    }
}
