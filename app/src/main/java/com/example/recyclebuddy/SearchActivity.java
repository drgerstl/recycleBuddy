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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    /*** Class Variables ***/

    private Button btnHome;
    private MultiAutoCompleteTextView txtSearch;
    private RadioGroup radioGroup;
    private RadioButton radUPC;
    private RadioButton radProductType;

    private final static int atHome = 1;
    private final static int dropOff = 2;
    private final static int noRecycle = 0;

    static ArrayList<Recyclable> items;
    static ArrayList<RecycleCenter> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*** Load database ***/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        /*** Get IDs ***/

        btnHome        = findViewById(R.id.btnHomeSearch);
        txtSearch      = findViewById(R.id.txtSearch);
        radioGroup     = findViewById(R.id.radioGroup);
        radUPC         = findViewById(R.id.radUPC);
        radProductType = findViewById(R.id.radProductType);

        /*** Add Listeners ***/

           btnHome.setOnClickListener(this);
         txtSearch.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);

        /*** Lock orientation to portrait ***/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        /*** Set title ***/

        getSupportActionBar().setTitle("Recycle Buddy - Product Search");

        setupLocation();
        setupItems();
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
        if (checkedId == radUPC.getId()){
            setText("Enter UPC");
        }
        else if (checkedId == radProductType.getId()){
            setText("Enter product");
        }
    }

    private void clickHome(){
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void clearText(){
        txtSearch.setText("");
    }

    private void setText(String text){
        txtSearch.setText(text);
    }

    private void setupLocation() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader readerLocations = new FileReader("locations.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(readerLocations);

            JSONArray locationsList = (JSONArray) obj;
//			System.out.println(locationsList);

//			 Iterate over employee array
            for (int i = 0; i < locationsList.size(); i++) {
                locations.add(parseLocations((JSONObject) locationsList.get(i)));
                locations.get(i).print();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static RecycleCenter parseLocations(JSONObject center) {
        String locationID = (String) center.get("locationID");
        String address = (String) center.get("address");
        String city = (String) center.get("city");
        String phone = (String) center.get("phone");

        //ArrayList<Recyclable> typesAccepted = Arrays.asList((String[]) center.get("typesAccepted"));

        return new RecycleCenter(locationID, address, city, phone, null);

    }

    private void setupItems() {


    }
}