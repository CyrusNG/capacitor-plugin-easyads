package com.capacitorjs.plugins.easyads.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.model.EasyAdError;

@SuppressLint("ViewConstructor")
public class NativeExpressController extends RelativeLayout implements BaseController {
    Activity context;
    SettingModel setting;
    ViewGroup nativeContainer;
    EasyADController ad;

    public NativeExpressController(@NonNull final Activity context, SettingModel setting, ViewGroup nativeContainer) {
        super(context);
        //保存当前activity
        this.context = context;
        //保存当前setting
        this.setting = setting;
        //保存广告位
        this.nativeContainer = nativeContainer;
        //加载layout
        inflate(getContext(), R.layout.adspot_native_express, this);
    }

    @Override
    public void load(AdCallback pluginCallback) {
        //先销毁广告（如有）
        this.destroy();
        //找到banner_layout
        RelativeLayout adContainer = this.findViewById(R.id.native_express_container);
        //初始化广告处理封装类
        this.ad = new EasyADController(this.context);
        //加载banner并在成功时在appRootViewGroup中添加此RelativeLayout
        //TODO: this.nativeContainer.addView(this)
        NativeExpressController self = this;
        AdCallback adspotCallback = new AdCallback() {
            @Override
            public void start() { nativeContainer.addView(self); pluginCallback.start(); }
            @Override
            public void skip() { pluginCallback.skip(); }
            @Override
            public void end() { pluginCallback.end(); }
            @Override
            public void fail(EasyAdError error) { pluginCallback.fail(error); }
        };
        this.ad.loadNativeExpress(this.setting.toJsonString(), adContainer, adspotCallback);
        //把activity_banner嵌入当前activity中
        //this.bannerView = LayoutInflater.from(context).inflate(R.layout.activity_native_express, rootViewGroup, true);
    }

    @Override
    public void destroy() {
        //销毁广告
        if (this.ad != null) this.ad.destroy();
        //删除view
        if (this.nativeContainer != null) this.nativeContainer.removeView(this);
    }

}

