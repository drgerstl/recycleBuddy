package com.example.recyclebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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

    private void clickScan(){

    }

    private void clickInfo(){

    }

    private void clickSearch(){

    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
