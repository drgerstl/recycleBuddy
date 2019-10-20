package com.example.recyclebuddy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InformationActivity  extends AppCompatActivity implements View.OnClickListener{

    /*** Class Variables ***/

    private Button btnHomeInfo;
    private Button btnSearch;
    private AutoCompleteTextView txtInformation;
    private String[] cities = {"Sun Prairie", "Middleton", "Madison","Fitchburg"};
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_page);

        /*** Get IDs ***/

        btnHomeInfo = findViewById(R.id.btnHomeInfo);
        txtInformation = findViewById(R.id.txtInformation);
        btnSearch = findViewById(R.id.btnSearch);

        /*** Instantiate ArrayAdapter ***/

        adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, cities);

        /*** Set autocomplete textfield properties ***/

        txtInformation.setThreshold(1);
        txtInformation.setAdapter(adapter);
        txtInformation.setTextColor(Color.BLACK);

        /*** Add onClickListeners ***/

        btnHomeInfo.setOnClickListener(this);
        txtInformation.setOnClickListener(this);

        /*** Lock orientation to portrait ***/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        /*** Set title ***/

        getSupportActionBar().setTitle("Recycle Buddy - Information");
    }

    /*** onClick listener method ***/
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnHomeInfo:
                clickHome();
                break;

            case R.id.txtInformation:
                selectInformation();
                break;

            case R.id.btnSearch:
                clickSearch();
                break;
        }
    }

    private void clickHome(){
        Intent intent = new Intent(InformationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void selectInformation(){
        txtInformation.setText("");
        txtInformation.requestFocus();
    }

    private void clickSearch(){
        //TODO
    }
}

