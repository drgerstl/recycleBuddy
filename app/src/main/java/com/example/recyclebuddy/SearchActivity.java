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

    }

    /*** Listener methods ***/
    @Override
    public void onClick(View view) {

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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == radUPC.getId()) {
            setText("Enter UPC");
        } else if (checkedId == radProductType.getId()) {
            setText("Enter product");
        }
    }

    private void clickHome() {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void clearText() {
        txtSearch.setText("");
    }

    private void setText(String text) {
        txtSearch.setText(text);
    }

    private void setupLocationArray() {
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

    private String locationsLoadJSONFromAsset() {
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

    private String itemsLoadJSONFromAsset() {
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

    private static RecycleCenter parseLocations(JSONObject center) {
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

    private void setupItemsArray() {
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

    private static Recyclable parseItems(JSONObject center) {
        try {
            String itemID = (String) center.get("itemID");

            int isRecyclable = (int) center.get("isRecyclable");
            String[] UPC = (String[]) center.get("Products");
            ///   return new Recyclable(itemID, UPC, isRecyclable);
        } catch (JSONException e) {

        }
        return null;
    }

    private static void cramTime() {

        locations.add(new RecycleCenter("Madison Wast", "1501 W Badger Rd", "Madison", "608-266-4681",
                new String[] {"oil","electronics","yard waste","scrap metal"}));
        locations.add(new RecycleCenter("Madison Eest", "4602 Sycamore Ave", "Madison", "608-246-4532",
                new String[] {"electronics","yard waste","scrap metal"}));
        locations.add(new RecycleCenter("Madison Yard Waste", "402 South Point Rd", "Madison", null,
                new String[] {"yard waste"}));
        locations.add(new RecycleCenter("Reynolds Urethane Recycling", "2701 Progress Rd", "Madison", "608-906-4244",
                new String[] {"poly materials"}));
        locations.add(new RecycleCenter("Henry Vilas Zoo", "606 Randall Ave", "Madison", "608-266-4732",
                new String[] {"cell phones"}));
        locations.add(new RecycleCenter("Sun Prarie", "1798 South Bird St", "Sun Prarie", "608-837-3050",
                new String[] {"Yard Waste","Oil", "Scrap Metal"}));
        locations.add(new RecycleCenter("Middleton Recycling Center", "7426 Hubbard Ave", "Middleton", "608-821-8350",
                new String[] {"electronics","printer ink","scrap metal"}));
        locations.add(new RecycleCenter("The Can Man","7432 Schneider Rd", "Middleton", "608-831-2775",
                new String[] {"Scrap Metal"}));
        locations.add(new RecycleCenter("Fitchburg Drop Off", "2373 S Fish Hatchery Rd", "Fitchburg", "N/A",
                new String[] {"",""}));
        locations.add(new RecycleCenter("Fitchburg City Hall Lobby", "5520 Lacy Rd", "Fitchburg", "608-270-4200",
                new String[] {"printer ink"}));

        items.add(new Recyclable("pizza box","",0,""));
        items.add(new Recyclable("Snickers wrapper","040000151463",0,""));
        items.add(new Recyclable("Red Bull can","61126999100",1,""));
        items.add(new Recyclable("Coke bottle","000004904403",1,""));
        items.add(new Recyclable("cooking oil","",2,"oil"));
        items.add(new Recyclable("motor oil","",2,"oil"));
        items.add(new Recyclable("TV","",2,"electronics"));
        items.add(new Recyclable("fax machine","",2,"electronics"));
        items.add(new Recyclable("aluminum","",2,"scrap metal"));
        items.add(new Recyclable("copper","",2,"scrap metal"));
        items.add(new Recyclable("scrap metal","",2,"scrap metal"));
        items.add(new Recyclable("HP printer cartridge","886985910554",2,"printer ink"));
        items.add(new Recyclable("leaves","",2,"yard waste"));
        items.add(new Recyclable("weeds","",2,"yard waste"));
        items.add(new Recyclable("noninvasive plants","",2,"yard waste"));

    }
}
