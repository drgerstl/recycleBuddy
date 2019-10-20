package com.example.recyclebuddy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener,
                                    AdapterView.OnItemClickListener {

    /*** Class Variables ***/

    private Button btnHome;
    private Button btnSearch;
    private AutoCompleteTextView txtSearch;
    private String userSelection;
    private TextView txtOutput;
    private ArrayAdapter<String> adapter;
    private String[] itemIDs = {"Pizza Box", "Snickers Wrapper", "Red Bull Can", "Coke Bottle",
                                "Cooking Oil", "Motor Oil", "TV", "fax machine", "aluminum",
                                "copper","scrap metal","HP printer cartridge" , "leaves",  "weeds",
                                "noninvasive plants"};

    private ArrayList<Recyclable> items = new ArrayList<>();
    private ArrayList<RecycleCenter> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*** Load database ***/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        /*** Instantiate ArrayAdapter ***/

        adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, itemIDs);

        /*** Get IDs ***/

        btnHome = findViewById(R.id.btnHomeSearch);
        txtSearch = findViewById(R.id.txtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        txtOutput = findViewById(R.id.txtOutput);

        /*** Set autocomplete textfield properties ***/

        txtSearch.setThreshold(1);
        txtSearch.setAdapter(adapter);
        txtSearch.setTextColor(Color.BLACK);

        /*** Add Listeners ***/

        btnHome.setOnClickListener(this);
        txtSearch.setOnItemClickListener(this);
        btnSearch.setOnClickListener(this);

        /*** Lock orientation to portrait ***/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        /*** Set title ***/

        getSupportActionBar().setTitle("Recycle Buddy - Product Search");

        /*** Fill Arrays with data ***/

        cramTime();
    }

    /*** Listener methods ***/
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnHomeSearch:
                clickHome();
                break;

            case R.id.txtSearchPrompt:
                clearText();
                break;

            case R.id.btnSearch:
                clickSearch();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
       userSelection =  parent.getItemAtPosition(position).toString();
    }

    private void clickHome() {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void clearText() {
        txtSearch.setText("");
        txtSearch.requestFocus();
    }

    private void clickSearch() {
        String output = "Item can be recycled at: \n";
        String text = userSelection;
        Recyclable item = null;

        if (text.equals("") || text.equals(null)) {
            txtOutput.setText("Item not provided");
        }

        else
        {
            for (int i = 0; i < items.size(); i++)
                if (items.get(i).itemID.equalsIgnoreCase(text)){
                    item = items.get(i);
                }
        }

        if (item != null){
            for (int i = 0; i < locations.size(); i++) {
                if (locations.get(i).typesAccepted.length > 0) {
                    for (int j = 0; j < locations.get(i).typesAccepted.length; j++) {
                        if (item.type.equalsIgnoreCase(locations.get(i).typesAccepted[j])) {
                            output += "\n" + (locations.get(i).locationID);
                        }
                    }
                }
            }
        }
        hideKeyboard(this);
        txtOutput.setText(output);
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*** Helper method to fill arrays ***/

    private void cramTime() {

        locations.add(new RecycleCenter("Madison Wast", "1501 W Badger Rd", "Madison", "608-266-4681",
                new String[] {"oil","electronics","yard waste","scrap metal"}));
        locations.add(new RecycleCenter("Madison Eest", "4602 Sycamore Ave", "Madison", "608-246-4532",
                new String[] {"electronics","yard waste","scrap metal"}));
        locations.add(new RecycleCenter("Madison Yard Waste", "402 South Point Rd", "Madison", null,
                new String[] {"yard waste"}));
        locations.add(new RecycleCenter("Reynolds Urethane Recycling", "2701 Progress Rd", "Madison", "608-906-4244",
                new String[] {"poly materials"}));
        locations.add(new RecycleCenter("Henry Vilas Zoo", "606 Randall Ave", "Madison", "608-266-4732",
                new String[] {"cell phones"}));
        locations.add(new RecycleCenter("Sun Prarie", "1798 South Bird St", "Sun Prarie", "608-837-3050",
                new String[] {"Yard Waste","Oil", "Scrap Metal"}));
        locations.add(new RecycleCenter("Middleton Recycling Center", "7426 Hubbard Ave", "Middleton", "608-821-8350",
                new String[] {"electronics","printer ink","scrap metal"}));
        locations.add(new RecycleCenter("The Can Man","7432 Schneider Rd", "Middleton", "608-831-2775",
                new String[] {"Scrap Metal"}));
        locations.add(new RecycleCenter("Fitchburg Drop Off", "2373 S Fish Hatchery Rd", "Fitchburg", "N/A",
                new String[] {"electronics","Scrap Metal"}));
        locations.add(new RecycleCenter("Fitchburg City Hall Lobby", "5520 Lacy Rd", "Fitchburg", "608-270-4200",
                new String[] {"printer ink"}));

        items.add(new Recyclable("pizza box","",0,""));
        items.add(new Recyclable("Snickers Wrapper","040000151463",0,""));
        items.add(new Recyclable("Red Bull can","61126999100",1,""));
        items.add(new Recyclable("Coke bottle","000004904403",1,""));
        items.add(new Recyclable("cooking oil","",2,"oil"));
        items.add(new Recyclable("motor oil","",2,"oil"));
        items.add(new Recyclable("TV","",2,"electronics"));
        items.add(new Recyclable("fax machine","",2,"electronics"));
        items.add(new Recyclable("aluminum","",2,"scrap metal"));
        items.add(new Recyclable("copper","",2,"scrap metal"));
        items.add(new Recyclable("scrap metal","",2,"scrap metal"));
        items.add(new Recyclable("HP printer cartridge","886985910554",2,"printer ink"));
        items.add(new Recyclable("leaves","",2,"yard waste"));
        items.add(new Recyclable("weeds","",2,"yard waste"));
        items.add(new Recyclable("noninvasive plants","",2,"yard waste"));

    }
}
