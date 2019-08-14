package com.example.barcodesample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText=(EditText)findViewById(R.id.text);
        Button button=(Button)findViewById(R.id.button);
        Button button1=findViewById(R.id.button_barcode);
        final ImageView imageView=(ImageView)findViewById(R.id.image_barcode);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this,"Please enter text",Toast.LENGTH_LONG).show();
                }else {
                    try {
                        BarcodeEncoder encoder=new BarcodeEncoder();
                        Bitmap bitmap=encoder.encodeBitmap(editText.getText().toString(), BarcodeFormat.QR_CODE,500,500);
                        imageView.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().equals("")){
                    Toast.makeText(MainActivity.this,"Please enter text",Toast.LENGTH_LONG).show();
                }else {
                    try {
                        Bitmap bitmapBarcode=createBarcodeBitmap(editText.getText().toString(),500,500);
                        imageView.setImageBitmap(bitmapBarcode);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
    private Bitmap createBarcodeBitmap(String data, int width, int height) throws WriterException {
        MultiFormatWriter writer = new MultiFormatWriter();
        String finalData = Uri.encode(data);

        // Use 1 as the height of the matrix as this is a 1D Barcode.
        BitMatrix bm = writer.encode(finalData, BarcodeFormat.CODE_128, width, 1);
        int bmWidth = bm.getWidth();

        Bitmap imageBitmap = Bitmap.createBitmap(bmWidth, height, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < bmWidth; i++) {
            // Paint columns of width 1
            int[] column = new int[height];
            Arrays.fill(column, bm.get(i, 0) ? Color.BLACK : Color.WHITE);
            imageBitmap.setPixels(column, 0, 1, i, 0, 1, height);
        }

        return imageBitmap;
    }
}
