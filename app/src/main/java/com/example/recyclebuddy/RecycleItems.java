package com.example.recyclebuddy;
import android.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class RecycleItems {
    public ArrayList<String> itemNames;
    public ArrayList<Integer> recycleInt;
    public ArrayList<String> UPCs;
    public ArrayList<String> types;

// https://abhiandroid.com/programming/json
    protected void readFile() {
        try {
            String in = "items.json";
            JSONObject obj = new JSONObject(in);
            JSONArray itemArray  = obj.getJSONArray("items");
            itemID = sys.getString("itemID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
