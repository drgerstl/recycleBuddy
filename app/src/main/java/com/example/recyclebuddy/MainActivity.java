package com.example.recyclebuddy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*** Local Variables ***/

    Button btnSearch;
    Button btnInfo;
    Button btnScan;

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
        }
    }
//test
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
}
