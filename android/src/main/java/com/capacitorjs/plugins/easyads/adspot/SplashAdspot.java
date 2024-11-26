package com.capacitorjs.plugins.easyads.adspot;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;

public class SplashAdspot extends Dialog {
    LinearLayout logo;
    FrameLayout adContainer;
    Activity context;
    SettingModel setting;
    EasyADController ad;


    public SplashAdspot(@NonNull final Activity context, SettingModel setting) {
        super(context);
        //保存当前activity
        this.context = context;
        //保存当前setting
        this.setting = setting;
        //设置View内容
        setContentView(R.layout.adspot_splash);
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //获取广告容器和LogoView
        this.adContainer = findViewById(R.id.splash_container);
        this.logo = findViewById(R.id.ll_logo);
    }

    public void load(AdCallback callback) {
        //先销毁广告（如有）
        this.destroy();
        //初始化广告处理封装类
        this.ad = new EasyADController(this.context);
        //加载广告
        this.ad.loadSplash(setting.toJsonString(), this.adContainer, this.logo, false, callback);

    }

    public void destroy() {
        //销毁广告
        if (this.ad != null) this.ad.destroy();
    }

}
