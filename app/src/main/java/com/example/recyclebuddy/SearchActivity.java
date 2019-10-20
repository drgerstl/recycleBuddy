package com.example.recyclebuddy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    /*** Class Variables ***/

    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*** Load database ***/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        /*** Get IDs ***/

        btnHome = findViewById(R.id.btnHomeSearch);

        /*** Add onClickListeners ***/

        btnHome.setOnClickListener(this);

        /*** Lock orientation to portrait ***/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        /*** Set title ***/

        getSupportActionBar().setTitle("Recycle Buddy");
    }

    /*** onClick listener method ***/
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnHomeSearch:
                clickHome();
                break;
        }
    }

    private void clickHome(){
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }
}