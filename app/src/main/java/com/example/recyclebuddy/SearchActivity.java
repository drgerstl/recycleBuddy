package com.example.recyclebuddy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import java.util.Arrays;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    /*** Class Variables ***/

    private Button btnHome;
    private MultiAutoCompleteTextView txtSearch;
    private RadioGroup radioGroup;
    private RadioButton radUPC;
    private RadioButton radProductType;

    private final static int AT_HOME = 1;
    private final static int DROP_OFF = 2;
    private final static int NO_RECYCLE = 0;

    static ArrayList<Recyclable> items;
    static ArrayList<RecycleCenter> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*** Load database ***/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        /*** Get IDs ***/

        btnHome = findViewById(R.id.btnHomeSearch);
        txtSearch = findViewById(R.id.txtSearch);
        radioGroup = findViewById(R.id.radioGroup);
        radUPC = findViewById(R.id.radUPC);
        radProductType = findViewById(R.id.radProductType);

        /*** Add Listeners ***/

        btnHome.setOnClickListener(this);
        txtSearch.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);

        /*** Lock orientation to portrait ***/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        /*** Set title ***/

        getSupportActionBar().setTitle("Recycle Buddy - Product Search");

        /*** Parse JSON ***/
/**
 setupLocationArray();
 setupItemsArray();

 System.out.println("**********************************************************");
 System.out.println("**********************************************************");
 System.out.println("**********************************************************");
 for(int i = 0; i < items.size(); i++){
 items.get(i).print();
 }
 System.out.println("**********************************************************");
 System.out.println("**********************************************************");
 System.out.println("**********************************************************");
 **/
 }

 /*** Listener methods ***/
        @Override
        public void onClick (View view){

            switch (view.getId()) {
                case R.id.btnHomeSearch:
                    clickHome();
                    break;

                case R.id.txtSearch:
                    clearText();
                    break;
            }
        }

        @Override
        public void onCheckedChanged (RadioGroup group,int checkedId){
            if (checkedId == radUPC.getId()) {
                setText("Enter UPC");
            } else if (checkedId == radProductType.getId()) {
                setText("Enter product");
            }
        }

        private void clickHome () {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
        }

        private void clearText () {
            txtSearch.setText("");
        }

        private void setText (String text){
            txtSearch.setText(text);
        }

        private void setupLocationArray () {
            try {
                // get JSONObject from JSON file
                JSONObject obj = new JSONObject(locationsLoadJSONFromAsset());

                // fetch JSONArray named users
                JSONArray jLocations = obj.getJSONArray("locations");

                // implement for loop for getting users list data
                for (int i = 0; i < jLocations.length(); i++) {
                    locations.add(parseLocations((JSONObject) jLocations.get(i)));
                    locations.get(i).print();

                }
            } catch (JSONException e) {

            }
        }

        private String locationsLoadJSONFromAsset () {
            String json = null;
            try {
                InputStream is = getAssets().open("locations.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        private String itemsLoadJSONFromAsset () {
            String json = null;
            try {
                InputStream is = getAssets().open("items.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        private static RecycleCenter parseLocations (JSONObject center){
            try {
                String locationID = (String) center.get("locationID");

                String address = (String) center.get("address");
                String city = (String) center.get("city");
                String phone = (String) center.get("phone");
                return new RecycleCenter(locationID, address, city, phone, null);
                //ArrayList<Recyclable> typesAccepted = Arrays.asList((String[]) center.get("typesAccepted"));
            } catch (JSONException e) {

            }
            return null;
        }

        private void setupItemsArray () {
            try {
                // get JSONObject from JSON file
                JSONObject obj = new JSONObject(itemsLoadJSONFromAsset());

                // fetch JSONArray named users
                JSONArray jItems = obj.getJSONArray("items");
                // implement for loop for getting users list data
                for (int i = 0; i < jItems.length(); i++) {
                    items.add(parseItems((JSONObject) jItems.get(i)));
                    items.get(i).print();
                }
            } catch (JSONException e) {

            }

        }

        private static Recyclable parseItems (JSONObject center){
            try {
                String itemID = (String) center.get("itemID");

                int isRecyclable = (int) center.get("isRecyclable");
                String[] UPC = (String[]) center.get("Products");
             ///   return new Recyclable(itemID, UPC, isRecyclable);
            } catch (JSONException e) {

            }
            return null;
        }

        private static void cramTime () {
            items.add(new Recyclable("Red Bull Can", "61126999100", 1));
        }
    }