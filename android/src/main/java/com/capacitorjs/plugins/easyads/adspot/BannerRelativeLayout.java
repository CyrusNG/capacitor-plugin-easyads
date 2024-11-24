package com.capacitorjs.plugins.easyads.adspot;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;

@SuppressLint("ViewConstructor")
public class BannerRelativeLayout extends RelativeLayout {
    Activity context;
    SettingModel setting;
    ViewGroup appRootViewGroup;
    EasyADController ad;

    public BannerRelativeLayout(@NonNull final Activity context, SettingModel setting) {
        super(context);
        //保存当前activity
        this.context = context;
        //保存当前setting
        this.setting = setting;
        //加载layout
        inflate(getContext(), R.layout.activity_banner, this);
        //获取当前activity的根ViewGroup
        this.appRootViewGroup = (ViewGroup) ((ViewGroup) this.context.findViewById(android.R.id.content)).getChildAt(0);
    }


    public void loadBanner() {
        //先销毁广告（如有）
        this.destroyBanner();
        //找到banner_layout
        RelativeLayout adContainer = this.findViewById(R.id.banner_layout);
        //初始化广告处理封装类
        this.ad = new EasyADController(this.context);
        //加载banner并在成功时在appRootViewGroup中添加此RelativeLayout
        this.ad.loadBanner(this.setting.toJson(), adContainer, () -> this.appRootViewGroup.addView(this));
        //把activity_banner嵌入当前activity中
        //this.bannerView = LayoutInflater.from(context).inflate(R.layout.activity_banner, rootViewGroup, true);
    }

    public void destroyBanner() {
        //销毁广告
        if (this.ad != null) this.ad.destroy();
        //删除view
        if (this.appRootViewGroup != null) this.appRootViewGroup.removeView(this);
    }

}
