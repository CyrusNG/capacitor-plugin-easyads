package com.capacitorjs.plugins.easyads.adspot;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.model.SettingModel;

public class InterstitialAdspot {
    Activity context;
    SettingModel setting;
    EasyADController ad;

    public InterstitialAdspot(@NonNull final Activity context, SettingModel setting) {
        //保存当前activity
        this.context = context;
        //保存当前setting
        this.setting = setting;
        //加载layout
        this.ad = new EasyADController(context);
    }


    public void load() {
        this.ad.initInterstitial(this.setting.toJson()).loadAndShow();
    }

    public void destory() {
        //销毁广告
        if (this.ad != null) this.ad.destroy();
    }

}
