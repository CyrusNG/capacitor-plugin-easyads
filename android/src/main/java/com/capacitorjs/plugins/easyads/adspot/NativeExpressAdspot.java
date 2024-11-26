package com.capacitorjs.plugins.easyads.adspot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;

@SuppressLint("ViewConstructor")
public class NativeExpressAdspot extends RelativeLayout {
    Activity context;
    SettingModel setting;
    ViewGroup nativeContainer;
    EasyADController ad;

    public NativeExpressAdspot(@NonNull final Activity context, SettingModel setting, ViewGroup nativeContainer) {
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


    public void load() {
        //先销毁广告（如有）
        this.destroy();
        //找到banner_layout
        RelativeLayout adContainer = this.findViewById(R.id.native_express_container);
        //初始化广告处理封装类
        this.ad = new EasyADController(this.context);
        //加载banner并在成功时在appRootViewGroup中添加此RelativeLayout
        this.ad.loadNativeExpress(this.setting.toJson(), adContainer, () -> this.nativeContainer.addView(this));
        //把activity_banner嵌入当前activity中
        //this.bannerView = LayoutInflater.from(context).inflate(R.layout.activity_native_express, rootViewGroup, true);
    }

    public void destroy() {
        //销毁广告
        if (this.ad != null) this.ad.destroy();
        //删除view
        if (this.nativeContainer != null) this.nativeContainer.removeView(this);
    }

}

