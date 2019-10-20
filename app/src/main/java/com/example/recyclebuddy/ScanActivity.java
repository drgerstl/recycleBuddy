package com.example.recyclebuddy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import java.io.IOException;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    /*** Class Constants ***/

    private static final int PICK_IMAGE = 100;

    /*** Class Variables ***/

    private Button btnHomeScan;
    private Button btnSelectImage;
    private TextView txtUpcOutput;
    private Bitmap myBitmap;
    private ImageView myImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_page);

        /*** Get IDs ***/

        btnHomeScan    = findViewById(R.id.btnHomeScan);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        txtUpcOutput   = findViewById(R.id.txtUpcOutput);
        myImageView    = findViewById(R.id.imgGallery);

        /*** Add onClickListeners ***/

           btnHomeScan.setOnClickListener(this);
        btnSelectImage.setOnClickListener(this);

        /*** Lock orientation to portrait ***/

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        /*** Set title ***/

        getSupportActionBar().setTitle("Recycle Buddy - Scanner");
    }

    /*** onClick listener method ***/
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnHomeScan:
                clickHome();
                break;

            case R.id.btnSelectImage:
                clickSelectImage();
                break;
        }
    }

    /*** Click Event Helper Methods ***/

    private void clickHome(){
        Intent intent = new Intent(ScanActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void clickSelectImage() {
        Intent gallery = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(gallery, PICK_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && data != null) {

            Uri imageUri = data.getData();
            myImageView.setImageURI(imageUri);

            /*** Get image from gallery ***/

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                myImageView.setImageBitmap(bitmap);
                barcodeDetector(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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

    // aye
}
