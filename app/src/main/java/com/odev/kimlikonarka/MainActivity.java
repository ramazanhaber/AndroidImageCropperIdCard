package com.odev.kimlikonarka;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;


public class MainActivity extends AppCompatActivity {

    Button btnOnyuz;
    ImageView imageOnyuz;

    Button btnArkaYuz;
    ImageView imageArkaYuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageOnyuz = findViewById(R.id.imageOnyuz);
        btnOnyuz = findViewById(R.id.btnOnyuz);
        btnArkaYuz = findViewById(R.id.btnArkaYuz);
        imageArkaYuz = findViewById(R.id.imageArkaYuz);
        btnOnyuz.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                onyuz = true;
                ac();
            }
        });
        btnArkaYuz.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                onyuz = false;
                ac();
            }
        });

    }

    boolean onyuz = false;

    public void ac() {
        try {
            new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
            CroperinoFileUtil.verifyStoragePermissions(MainActivity.this);
            CroperinoFileUtil.setupDirectory(MainActivity.this);
            Croperino.prepareCamera(MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CroperinoConfig.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    /* Parameters of runCropImage = File, Activity Context, Image is Scalable or Not, Aspect Ratio X, Aspect Ratio Y, Button Bar Color, Background Color */
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), MainActivity.this, true, 1, 1, R.color.gray, R.color.gray_variant);
                }
                break;
            case CroperinoConfig.REQUEST_PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    CroperinoFileUtil.newGalleryFile(data, MainActivity.this);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), MainActivity.this, true, 0, 0, R.color.gray, R.color.gray_variant);
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    if (onyuz) {
                        imageOnyuz.setImageURI(i);
                    } else {
                        imageArkaYuz.setImageURI(i);
                    }
                }
                break;
            default:
                break;
        }
    }


}