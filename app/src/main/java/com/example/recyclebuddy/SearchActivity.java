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

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    /*** Class Variables ***/

    private Button btnHome;
    private MultiAutoCompleteTextView txtSearch;
    private RadioGroup radioGroup;
    private RadioButton radUPC;
    private RadioButton radProductType;

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


}