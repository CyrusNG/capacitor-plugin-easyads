package com.capacitorjs.plugins.easyads.controller;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.OptionModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.core.splash.EASplashListener;
import com.easyads.core.splash.EasyAdSplash;
import com.easyads.model.EasyAdError;
import com.getcapacitor.PluginCall;

public class SplashController extends Dialog implements BaseController {
    Activity context;
    PluginCall call;
    AdCallback pluginCallback;
    SettingModel setting;
    OptionModel option;
    LinearLayout logo;
    FrameLayout adContainer;

    private static final String TAG = SplashController.class.getSimpleName();

    public SplashController(@NonNull final Activity context, PluginCall call, AdCallback pluginCallback, SettingModel setting, OptionModel option) {
        super(context);
        //保存当前activity
        this.context = context;
        //保存当前Capacitor插件call
        this.call = call;
        //保存插件回调
        this.pluginCallback = pluginCallback;
        //保存当前setting
        this.setting = setting;
        //保存当前option
        this.option = option;
        //设置View内容
        setContentView(R.layout.adspot_splash);
        //获取广告容器和LogoView
        this.adContainer = findViewById(R.id.splash_container);
        this.logo = findViewById(R.id.ll_logo);
    }

    /**
     * 加载开屏广告，开屏推荐使用加载并展示开屏广告方式，所有的广告均支持请求和展示分离，如有必要，可分别调用加载广告和展示广告，可参考"插屏广告"中的处理示例。
     */
    @Override
    public void load() {
        //先销毁广告（如有）
        this.destroy();
        //初始化广告实例
        EasyAdSplash easySplash = new EasyAdSplash(this.context, this.adContainer, this.createListeners());
        //注意：如果开屏页是fragment或者dialog实现，这里需要置为false。默认为true，代表开屏和首页为两个不同的activity
        easySplash.setShowInSingleActivity(this.option.singleActivity());
        //注意：此处自定义渠道的tag，一定要和setData()中配置的tag一致。
//        if (cusXiaoMi) easySplash.addCustomSupplier("xm", new XiaoMiSplashAdapter(new SoftReference<>(this.context), easySplash));
//        if (cusHuaWei) easySplash.addCustomSupplier("hw", new HuaWeiSplashAdapter(new SoftReference<>(this.context), easySplash));
        //必须：设置策略信息
        easySplash.setData(this.setting.toJsonString());
        //必须：请求并展示广告
        easySplash.loadAndShow();
        //展示提示
        Log.d(TAG, "广告请求中");
    }

    @Override
    public void destroy() {
        //销毁广告
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
                    //View.SYSTEM_UI_FLAG_FULLSCREEN |
                    //隐藏导航栏
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    //全屏布局
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



    // MARK: ======================= Banner Listeners =======================
    private EASplashListener createListeners() {
        SplashController self = this;
        //必须：核心事件监听回调
        return new EASplashListener() {

            @Override
            public void onAdFailed(EasyAdError error) {
                Log.d(TAG, "广告加载失败 code=" + error.code + " msg=" + error.msg);
                if(self.pluginCallback != null) self.pluginCallback.notify("fail", self.call, error);
            }

            @Override
            public void onAdSucceed() {
                Log.d(TAG, "广告加载成功");
                if(self.pluginCallback != null) self.pluginCallback.notify("ready", self.call, null);
            }

            @Override
            public void onAdExposure() {
                Log.d(TAG, "广告展现");
                if(self.pluginCallback != null) self.pluginCallback.notify("start", self.call, null);
            }

            @Override
            public void onAdClose() {
                Log.d(TAG, "广告关闭");
                if(self.pluginCallback != null) self.pluginCallback.notify("end", self.call, null);
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "广告点击");
                if(self.pluginCallback != null) self.pluginCallback.notify("did-click", self.call, null);
            }
        };
    }

}
