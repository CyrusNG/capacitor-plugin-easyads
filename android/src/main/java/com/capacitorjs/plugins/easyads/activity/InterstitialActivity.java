package com.capacitorjs.plugins.easyads.activity;

import android.os.Bundle;
import android.view.View;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;
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
        SettingModel setting = getIntent().getParcelableExtra("setting");
        interstitialAD = new EasyADController(this).initInterstitial(setting.toJson());
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
        SettingModel setting = getIntent().getParcelableExtra("setting");
        new EasyADController(this).initInterstitial(setting.toJson()).loadAndShow();
    }
}
