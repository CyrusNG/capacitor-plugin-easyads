package com.capacitorjs.plugins.easyads.adspot;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;

public class FullScreenVideoAdspot implements BaseAdspot {
    Activity context;
    SettingModel setting;
    EasyADController ad;

    public FullScreenVideoAdspot(@NonNull final Activity context, SettingModel setting) {
        //保存当前activity
        this.context = context;
        //保存当前setting
        this.setting = setting;
        //加载layout
        this.ad = new EasyADController(context);
    }

    @Override
    public void load(AdCallback pluginCallback) {
        this.ad.initFullVideo(this.setting.toJsonString(), pluginCallback).loadAndShow();
    }

    @Override
    public void destroy() {
        //销毁广告
        if (this.ad != null) this.ad.destroy();
    }

}