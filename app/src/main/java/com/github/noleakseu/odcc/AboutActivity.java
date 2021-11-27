package com.github.noleakseu.odcc;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.noleakseu.odcc.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAboutBinding viewBinding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        viewBinding.versionTextView.setText(getAppVersion());
    }

    public void sourceCode(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/noleaks/odcc")));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private String getAppVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return "0.0.0";
    }
}