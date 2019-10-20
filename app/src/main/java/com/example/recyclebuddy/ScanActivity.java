package com.example.recyclebuddy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    /*** Class Variables ***/

    private Button btnHomeScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_page);

        /*** Get IDs ***/

        btnHomeScan = findViewById(R.id.btnHomeScan);

        /*** Add onClickListeners ***/

        btnHomeScan.setOnClickListener(this);

        /*** Lock orientation to portrait ***/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        /*** Set title ***/

        getSupportActionBar().setTitle("Recycle Buddy");
    }

    /*** onClick listener method ***/
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnHomeScan:
                clickHome();
                break;
        }
    }

    private void clickHome(){
        Intent intent = new Intent(ScanActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
