package com.capacitorjs.plugins.easyads.controller;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.model.EasyAdError;

@SuppressLint("ViewConstructor")
public class BannerController extends RelativeLayout implements BaseController {
    Activity context;
    SettingModel setting;
    ViewGroup appRootViewGroup;
    EasyADController ad;

    public BannerController(@NonNull final Activity context, SettingModel setting) {
        super(context);
        //保存当前activity
        this.context = context;
        //保存当前setting
        this.setting = setting;
        //加载layout
        inflate(getContext(), R.layout.adspot_banner, this);
        //获取当前activity的根ViewGroup
        this.appRootViewGroup = (ViewGroup) ((ViewGroup) this.context.findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    public void load(AdCallback pluginCallback) {
        //先销毁广告（如有）
        this.destroy();
        //找到banner_layout
        RelativeLayout adContainer = this.findViewById(R.id.banner_container);
        //初始化广告处理封装类
        this.ad = new EasyADController(this.context);
        //加载banner并在成功时在appRootViewGroup中添加此RelativeLayout
        BannerController self = this;
        AdCallback adspotCallback = new AdCallback() {
            @Override
            public void start() { appRootViewGroup.addView(self); pluginCallback.start(); }
            @Override
            public void skip() { pluginCallback.skip(); }
            @Override
            public void end() { pluginCallback.end(); }
            @Override
            public void fail(EasyAdError error) { pluginCallback.fail(error); }
        };
        this.ad.loadBanner(this.setting.toJsonString(), adContainer, adspotCallback);
        //把activity_banner嵌入当前activity中
        //this.bannerView = LayoutInflater.from(context).inflate(R.layout.activity_banner, rootViewGroup, true);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        underlapStatusBar();
    }

    @Override
    public void destroy() {
        //销毁广告
        if (this.ad != null) this.ad.destroy();
        //删除view
        if (this.appRootViewGroup != null) this.appRootViewGroup.removeView(this);
    }

    private void underlapStatusBar() {
        //获取状态栏高度
        Rect rectangle = new Rect();
        this.context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        //// 另一种获取状态栏高度的方法
        //int statusBarHeight = 0;
        //final Resources resources = context.getResources();
        //final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        //if (resourceId > 0) { statusBarHeight = resources.getDimensionPixelSize(resourceId); }
        //else { statusBarHeight = (int) Math.ceil((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25) * resources.getDisplayMetrics().density); }
        //设置Padding避免状态栏重叠
        LayoutParams linearParams = new LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        linearParams.setMargins(0, statusBarHeight, 0, 0);
        this.setLayoutParams(linearParams);
    }

}
