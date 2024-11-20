package com.capacitorjs.plugins.easyads.activity;

import android.os.Bundle;
import android.view.View;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.easyads.core.inter.EasyAdInterstitial;

public class InterstitialActivity extends BaseActivity {
    EasyAdInterstitial interstitialAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

    }

    public void loadAd(View view) {
        // 获取配置
        String configJson = getIntent().getStringExtra("config");
        interstitialAD = new EasyADController(this).initInterstitial(configJson);
        interstitialAD.loadOnly();
    }

    public void showAd(View view) {
        if (interstitialAD != null) {
            interstitialAD.show();
        } else {
            EasyADController.logAndToast(this, "需要先调用loadOnly()");
        }
    }

    public void loadAndShowAd(View view) {
        // 获取配置
        String configJson = getIntent().getStringExtra("config");
        new EasyADController(this).initInterstitial(configJson).loadAndShow();
    }
}
