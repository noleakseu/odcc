package com.github.noleakseu.odcc;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.noleakseu.odcc.databinding.ActivityMainBinding;

import java.nio.charset.StandardCharsets;

import github.nisrulz.qreader.QREader;

public class MainActivity extends AppCompatActivity {

    private QREader qrEader;
    private SurfaceView surfaceView;
    private Vibrator vibrator;

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);
            recreate();
        }
        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        surfaceView = findViewById(R.id.camera_view);
        qrEader = new QREader.Builder(this, surfaceView, data -> {
            vibrator.vibrate(100);
            Intent intent = new Intent(getBaseContext(), CertificateActivity.class);
            intent.putExtra("BYTES", data.getBytes(StandardCharsets.UTF_8));
            startActivity(intent);
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(surfaceView.getWidth())
                .width(surfaceView.getWidth())
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!qrEader.isCameraRunning()) {
            qrEader.initAndStart(surfaceView);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!qrEader.isCameraRunning()) {
            qrEader.releaseAndCleanup();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!qrEader.isCameraRunning()) {
            qrEader.start();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!qrEader.isCameraRunning()) {
            recreate();
        }
    }
}
