package com.capacitorjs.plugins.easyads.controller;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.model.EasyAdError;

public class FullScreenVideoController implements BaseController {
    Activity context;
    SettingModel setting;
    EasyADController ad;

    public FullScreenVideoController(@NonNull final Activity context, SettingModel setting) {
        //保存当前activity
        this.context = context;
        //保存当前setting
        this.setting = setting;
    }

    @Override
    public void load(AdCallback pluginCallback) {
        //先销毁广告（如有）
        this.destroy();
        //初始化广告处理封装类
        this.ad = new EasyADController(this.context);
        //加载广告
        FullScreenVideoController self = this;
        AdCallback adspotCallback = new AdCallback() {
            @Override
            public void start() { pluginCallback.start(); }
            @Override
            public void skip() { pluginCallback.skip(); }
            @Override
            public void end() { pluginCallback.end(); }
            @Override
            public void fail(EasyAdError error) { pluginCallback.fail(error); }
        };
        this.ad.initFullVideo(this.setting.toJsonString(), adspotCallback).loadAndShow();
    }

    @Override
    public void destroy() {
        //销毁广告
        if (this.ad != null) this.ad.destroy();
    }

}