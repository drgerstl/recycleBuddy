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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_page);

        /*** Get IDs ***/

        btnHomeScan    = findViewById(R.id.btnHomeScan);
        btnTakePicture = findViewById(R.id.btnTakePicture);
        txtUpcOutput   = findViewById(R.id.txtUpcOutput);
        myImageView    = findViewById(R.id.image);

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
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
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

    private void clickHome(){
        Intent intent = new Intent(ScanActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void clickTakePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), file);
                barcodeDetector(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    private String barcodeDetector(Bitmap bitmap) {

        /*** Local Variables ***/

        String upc = "";

        BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.UPC_A | Barcode.UPC_E).build();

        if(!detector.isOperational()){
            txtUpcOutput.setText("Could not set up the detector!");
        }
        if (bitmap == null){
            txtUpcOutput.setText("Invalid image provided");
        }
        else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<Barcode> barcodes = detector.detect(frame);
            if (barcodes.size() == 0){
                txtUpcOutput.setText("No Valid UPC Found");
            }
            else {
                Barcode thisCode = barcodes.valueAt(0);
                upc = thisCode.rawValue;
            }
        }
        return upc;
    }
}
