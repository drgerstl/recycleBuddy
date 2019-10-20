package com.example.recyclebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*** Class Variables ***/

    private Button btnSearch;
    private Button btnInfo;
    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*** Get IDs ***/

        btnScan   = findViewById(R.id.btnScan);
        btnInfo   = findViewById(R.id.btnInfo);
        btnSearch = findViewById(R.id.btnSearch);

        /*** Add onClickListeners ***/

        btnScan.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        /*** Lock orientation to portrait ***/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        /*** Set title ***/

        getSupportActionBar().setTitle("Recycle Buddy");
    }

    /*** onClick listener method ***/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnScan:
                clickScan();
                break;

            case R.id.btnInfo:
                clickInfo();
                break;

            case R.id.btnSearch:
                clickSearch();
                break;
        }
    }

    /*** Scan button tapped, switch to scan page***/

    private void clickScan(){
        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(intent);
    }

    /*** Info button tapped, switch to info page ***/

    private void clickInfo(){
        Intent intent = new Intent(MainActivity.this, InformationActivity.class);
        startActivity(intent);
    }

    /*** Search button tapped, switch to search page ***/

    private void clickSearch() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }
}