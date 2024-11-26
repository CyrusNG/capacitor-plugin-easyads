package com.capacitorjs.plugins.easyads.adspot;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;

public class RewardVideoAdspot {
    Activity context;
    SettingModel setting;
    EasyADController ad;

    public RewardVideoAdspot(@NonNull final Activity context, SettingModel setting) {
        //保存当前activity
        this.context = context;
        //保存当前setting
        this.setting = setting;
        //加载layout
        this.ad = new EasyADController(context);
    }


    public void load(AdCallback pluginCallback) {
        this.ad.initReward(this.setting.toJsonString(), pluginCallback).loadAndShow();
    }

    public void destory() {
        //销毁广告
        if (this.ad != null) this.ad.destroy();
    }

}
