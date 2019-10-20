package com.example.recyclebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*** Local Variables ***/

    Button btnSearch;
    Button btnInfo;
    Button btnScan;
    View v;
    Button btnHome;

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
        btnHome.setOnClickListener(this);

        /*** Lock orientation to portrait ***/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        /*** Set title ***/

        getSupportActionBar().setTitle("Recycle Buddy");
    }

    /*** onClick listener method ***/

    @Override
    public void onClick(View view){

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

            case R.id.btnHome:
                clickHome();
                break;
        }
    }

    /*** Back button press event ***/

//    public void onBackPressed(){
//        View currentView = this.findViewById(android.R.id.content).getRootView();
//
//        if (!currentView.equals(R.layout.activity_main)) {
//            setContentView(R.layout.activity_main);
//        }
//        else {
//            moveTaskToBack(true);
//        }
//    }

    /*** Scan button tapped, switch to scan page***/

    private void clickScan(){
        setContentView(R.layout.scan_page);
    }

    /*** Info button tapped, switch to info page ***/

    private void clickInfo(){
        setContentView(R.layout.information_page);
    }

    /*** Search button tapped, switch to search page ***/

    private void clickSearch(){
        setContentView(R.layout.seach_page);
    }

    private void clickHome(){
        setContentView(R.layout.activity_main);
    }
}