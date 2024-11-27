package com.capacitorjs.plugins.easyads.adspot;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.model.EasyAdError;

public class SplashAdspot extends Dialog implements BaseAdspot {
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
        Window window = this.getWindow();
        //设置View内容
        setContentView(R.layout.adspot_splash);
        //获取广告容器和LogoView
        this.adContainer = findViewById(R.id.splash_container);
        this.logo = findViewById(R.id.ll_logo);
    }

    @Override
    public void load(AdCallback pluginCallback) {
        //先销毁广告（如有）
        this.destroy();
        //初始化广告处理封装类
        this.ad = new EasyADController(this.context);
        //加载广告
        SplashAdspot self = this;
        AdCallback adspotCallback = new AdCallback() {
            @Override
            public void start() { self.show(); pluginCallback.start(); }
            @Override
            public void skip() { pluginCallback.skip(); }
            @Override
            public void end() { self.dismiss(); pluginCallback.end(); }
            @Override
            public void fail(EasyAdError error) { pluginCallback.fail(error); }
        };
        this.ad.loadSplash(setting.toJsonString(), this.adContainer, this.logo, false, adspotCallback);
    }

    @Override
    public void destroy() {
        //销毁广告
        if (this.ad != null) this.ad.destroy();
    }

    @Override
    public void show() {
        Window window = getWindow();
        if (window == null) { super.show(); return; }
        dismissPadding(window);
        focusNotAle(window);
        super.show();
        hideNavigationBar(window);
        clearFocusNotAle(window);
    }


    private void dismissPadding(Window window) {
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void hideNavigationBar(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    //布局位于状态栏下方
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    //全屏
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    //隐藏导航栏
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= 19) {
                uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            } else {
                uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            window.getDecorView().setSystemUiVisibility(uiOptions);
        });
    }

    private void focusNotAle(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    private void clearFocusNotAle(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

}
