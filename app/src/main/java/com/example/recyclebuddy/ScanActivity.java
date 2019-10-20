package com.example.recyclebuddy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    /*** Class Constants ***/

    private static final int PICK_IMAGE = 100;

    /*** Class Variables ***/

    private Button btnHomeScan;
    private Button btnTakePicture;
    private TextView txtUpcOutput;
    private Bitmap myBitmap;
    private ImageView myImageView;
    private Uri file;
    private ArrayList<Recyclable> items = new ArrayList<>();
    private ArrayList<RecycleCenter> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_page);

        /*** Get IDs ***/

        btnHomeScan = findViewById(R.id.btnHomeScan);
        btnTakePicture = findViewById(R.id.btnTakePicture);
        txtUpcOutput = findViewById(R.id.txtUpcOutput);
        myImageView = findViewById(R.id.image);

        /*** Ignores file URI exposure ***/

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        /*** Add onClickListeners ***/

        btnHomeScan.setOnClickListener(this);
        btnTakePicture.setOnClickListener(this);

        /*** Lock orientation to portrait ***/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        /*** Set title ***/

        getSupportActionBar().setTitle("Recycle Buddy - Scanner");

        /*** Check for camera and storage permissions***/

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            btnTakePicture.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        locations.add(new RecycleCenter("Madison Wast", "1501 W Badger Rd", "Madison", "608-266-4681",
                new String[]{"oil", "electronics", "yard waste", "scrap metal"}));
        locations.add(new RecycleCenter("Madison Eest", "4602 Sycamore Ave", "Madison", "608-246-4532",
                new String[]{"electronics", "yard waste", "scrap metal"}));
        locations.add(new RecycleCenter("Madison Yard Waste", "402 South Point Rd", "Madison", null,
                new String[]{"yard waste"}));
        locations.add(new RecycleCenter("Reynolds Urethane Recycling", "2701 Progress Rd", "Madison", "608-906-4244",
                new String[]{"poly materials"}));
        locations.add(new RecycleCenter("Henry Vilas Zoo", "606 Randall Ave", "Madison", "608-266-4732",
                new String[]{"cell phones"}));
        locations.add(new RecycleCenter("Sun Prarie", "1798 South Bird St", "Sun Prarie", "608-837-3050",
                new String[]{"Yard Waste", "Oil", "Scrap Metal"}));
        locations.add(new RecycleCenter("Middleton Recycling Center", "7426 Hubbard Ave", "Middleton", "608-821-8350",
                new String[]{"electronics", "printer ink", "scrap metal"}));
        locations.add(new RecycleCenter("The Can Man", "7432 Schneider Rd", "Middleton", "608-831-2775",
                new String[]{"Scrap Metal"}));
        locations.add(new RecycleCenter("Fitchburg Drop Off", "2373 S Fish Hatchery Rd", "Fitchburg", "N/A",
                new String[]{"electronics", "Scrap Metal"}));
        locations.add(new RecycleCenter("Fitchburg City Hall Lobby", "5520 Lacy Rd", "Fitchburg", "608-270-4200",
                new String[]{"printer ink"}));

        items.add(new Recyclable("pizza box", "", 0, ""));
        items.add(new Recyclable("Snickers Wrapper", "040000151463", 0, ""));
        items.add(new Recyclable("Red Bull can", "61126999100", 1, ""));
        items.add(new Recyclable("Coke bottle", "000004904403", 1, ""));
        items.add(new Recyclable("cooking oil", "", 2, "oil"));
        items.add(new Recyclable("motor oil", "", 2, "oil"));
        items.add(new Recyclable("TV", "", 2, "electronics"));
        items.add(new Recyclable("fax machine", "", 2, "electronics"));
        items.add(new Recyclable("aluminum", "", 2, "scrap metal"));
        items.add(new Recyclable("copper", "", 2, "scrap metal"));
        items.add(new Recyclable("scrap metal", "", 2, "scrap metal"));
        items.add(new Recyclable("HP printer cartridge", "886985910554", 2, "printer ink"));
        items.add(new Recyclable("leaves", "", 2, "yard waste"));
        items.add(new Recyclable("weeds", "", 2, "yard waste"));
        items.add(new Recyclable("noninvasive plants", "", 2, "yard waste"));
    }

    /*** Enable button if permissions granted ***/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                btnTakePicture.setEnabled(true);
            }
        }
    }

    /*** onClick listener method ***/

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnHomeScan:
                clickHome();
                break;

            case R.id.btnTakePicture:
                clickTakePicture();
                break;
        }
    }

    /*** Click Event Helper Methods ***/

    private void clickHome() {
        Intent intent = new Intent(ScanActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void clickTakePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
//
//        Intent searchIntent = new Intent(ScanActivity.this, SearchActivity.class);
//        startActivity(searchIntent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), file);
                barcodeDetector(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    private String barcodeDetector(Bitmap bitmap) {

        /*** Local Variables ***/

        String strUpc = "";

        BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.UPC_A | Barcode.UPC_E).build();

        if (!detector.isOperational()) {
            txtUpcOutput.setText("Could not set up the detector!");
        }
        if (bitmap == null) {
            txtUpcOutput.setText("Invalid image provided");
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<Barcode> barcodes = detector.detect(frame);
            if (barcodes.size() == 0) {
                txtUpcOutput.setText("No Valid UPC Found");
            } else {
                Barcode thisCode = barcodes.valueAt(0);
                strUpc = thisCode.rawValue;
            }
        }
        lookUpByScan(strUpc);
        return strUpc;
    }

    private void lookUpByScan(String upcCode) {
        String output = "";
        for (int i = 0; i < items.size(); i++) {
            if (upcCode.equals(items.get(i).UPC)) {
                //upc is found and matched to type
                for (int j = 0; j < locations.get(i).typesAccepted.length; j++) {
                    if (items.get(i).type.equalsIgnoreCase(locations.get(j).typesAccepted[j])) {
                        output += "\n" + (locations.get(i).locationID);
                    }
                }
            }
        }
        txtUpcOutput.setText(output);
    }
}

