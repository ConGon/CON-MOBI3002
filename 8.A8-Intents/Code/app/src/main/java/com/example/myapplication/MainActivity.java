package com.example.myapplication;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawView drawView;
    private RecyclerView recyclerView;
    private final List<Uri> imageUris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = findViewById(R.id.drawView);
        recyclerView = findViewById(R.id.recyclerView);

        setupGalleryFromAssets();
    }

    private void setupGalleryFromAssets() {
        try {
            AssetManager am = getAssets();
            String[] files = am.list(""); // root of assets folder

            File picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!picturesDir.exists()) picturesDir.mkdirs();

            for (String filename : files) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")) {

                    File outFile = new File(picturesDir, filename);

                    // Only copy if it doesn't already exist
                    if (!outFile.exists()) {
                        InputStream is = am.open(filename);
                        OutputStream os = new FileOutputStream(outFile);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.flush();
                        os.close();
                        is.close();

                        // Notify MediaStore
                        Uri contentUri = Uri.fromFile(outFile);
                        sendBroadcast(new android.content.Intent(android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri));
                    }

                    imageUris.add(Uri.fromFile(outFile));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("Gallery", "Found " + imageUris.size() + " images");

        // Set up RecyclerView
        GalleryAdapter adapter = new GalleryAdapter(this, imageUris, bitmap -> drawView.setBackgroundBitmap(bitmap));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }
}

