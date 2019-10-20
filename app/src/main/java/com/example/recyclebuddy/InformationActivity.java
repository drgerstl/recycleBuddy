package com.example.recyclebuddy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InformationActivity  extends AppCompatActivity implements View.OnClickListener{

    /*** Class Variables ***/

    private Button btnHomeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_page);

        /*** Get IDs ***/

        btnHomeInfo = findViewById(R.id.btnHomeInfo);

        /*** Add onClickListeners ***/

        btnHomeInfo.setOnClickListener(this);

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
        }
    }

    private void clickHome(){
        Intent intent = new Intent(InformationActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
