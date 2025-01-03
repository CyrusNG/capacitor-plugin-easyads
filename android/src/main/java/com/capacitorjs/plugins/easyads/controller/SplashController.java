package com.capacitorjs.plugins.easyads.controller;

import android.app.Activity;
import android.app.Dialog;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.OptionModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.capacitorjs.plugins.easyads.utils.UIUtils;
import com.easyads.core.splash.EASplashListener;
import com.easyads.core.splash.EasyAdSplash;
import com.easyads.model.EasyAdError;
import com.getcapacitor.PluginCall;

public class SplashController extends Dialog implements BaseController {
    Activity context;
    PluginCall call;
    AdCallback pluginCallback;
    EasyAdSplash easySplash;
    SettingModel setting;
    OptionModel option;
    ImageView logo;
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
        this.logo = findViewById(R.id.splash_logo_image);
        //设置App logo
        this.logo.setImageDrawable(UIUtils.getAppLogo(context));
    }

    /**
     * 加载开屏广告，开屏推荐使用加载并展示开屏广告方式，所有的广告均支持请求和展示分离，如有必要，可分别调用加载广告和展示广告，可参考"插屏广告"中的处理示例。
     */
    @Override
    public void load() {
        //先销毁广告（如有）
        this.destroy();
        //初始化广告实例
        this.easySplash = new EasyAdSplash(this.context, this.adContainer, this.createListeners());
        //注意：如果开屏页是fragment或者dialog实现，这里需要置为false。默认为true，代表开屏和首页为两个不同的activity
        this.easySplash.setShowInSingleActivity(false);
        //注意：此处自定义渠道的tag，一定要和setData()中配置的tag一致。
//        if (cusXiaoMi) this.easySplash.addCustomSupplier("xm", new XiaoMiSplashAdapter(new SoftReference<>(this.context), this.easySplash));
//        if (cusHuaWei) this.easySplash.addCustomSupplier("hw", new HuaWeiSplashAdapter(new SoftReference<>(this.context), this.easySplash));
        //必须：设置策略信息
        this.easySplash.setData(this.setting.toJsonString());
        //必须：请求/展示广告
        if(this.option.showLater()) { this.easySplash.loadOnly(); }
        else {  this.easySplash.loadAndShow(); }
        //展示提示
        Log.d(TAG, "广告请求中");
    }

    @Override
    public void show() {
        UIUtils.fullscreenShow(getWindow(), () -> {
            super.show();
            this.easySplash.show();
        });
    }

    @Override
    public void destroy() {
        //销毁广告
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
                if(!self.option.showLater()) self.show(); //广告加载成功后随即打开Dialog
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
                self.dismiss(); //广告关闭后随即关闭Dialog
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
