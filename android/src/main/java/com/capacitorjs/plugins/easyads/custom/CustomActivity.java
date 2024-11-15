package com.capacitorjs.plugins.easyads.custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.SplashActivity;
import com.capacitorjs.plugins.easyads.utils.Constant;

public class CustomActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
    }

    public void cusHW(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra(Constant.CUS_HW,true);
        startActivity(intent);
    }

    public void cusXM(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra(Constant.CUS_XM,true);
        startActivity(intent);
    }
}
