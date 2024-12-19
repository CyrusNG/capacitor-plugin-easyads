package com.capacitorjs.plugins.easyads.controller;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.capacitorjs.plugins.easyads.model.OptionModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.core.inter.EAInterstitialListener;
import com.easyads.core.inter.EasyAdInterstitial;
import com.easyads.model.EasyAdError;

public class InterstitialController implements BaseController {
    Activity context;
    AdCallback pluginCallback;
    SettingModel setting;
    OptionModel option;
    private static final String TAG = InterstitialController.class.getSimpleName();

    public InterstitialController(@NonNull final Activity context, AdCallback pluginCallback, SettingModel setting, OptionModel option) {
        //保存当前activity
        this.context = context;
        //保存插件回调
        this.pluginCallback = pluginCallback;
        //保存当前setting
        this.setting = setting;
        //保存当前option
        this.option = option;
    }

    /**
     * 初始话插屏广告。
     * 可以选择性先提前加载，然后在合适的时机再调用展示方法
     * 或者直接调用加载并展示广告
     * <p>
     * 注意！！！：穿山甲默认为"新插屏广告"
     */
    @Override
    public void load() {
        //先销毁广告（如有）
        this.destroy();
        //初始化广告实例
        EasyAdInterstitial easyInterstitial = new EasyAdInterstitial(this.context, this.createListeners());
        //注意：穿山甲默认为"新插屏广告"，如果要使用旧版请打开这条设置
        //easyInterstitial.setCsjNew(false);
        //必须：设置策略信息
        easyInterstitial.setData(this.setting.toJsonString());
        //必须：请求并展示广告
        easyInterstitial.loadAndShow();
    }



    @Override
    public void destroy() {
        //销毁广告
    }

    // MARK: ======================= Banner Listeners =======================
    private EAInterstitialListener createListeners() {
        InterstitialController self = this;
        //必须：核心事件监听回调
        return new EAInterstitialListener() {

            @Override
            public void onAdFailed(EasyAdError error) {
                Log.d(TAG, "广告加载失败 code=" + error.code + " msg=" + error.msg);
                if(self.pluginCallback != null) self.pluginCallback.fail(error);
            }

            @Override
            public void onAdSucceed() {
                Log.d(TAG, "广告加载成功");
                if(self.pluginCallback != null) self.pluginCallback.ready();
            }

            @Override
            public void onAdExposure() {
                Log.d(TAG, "广告展现");
                if(self.pluginCallback != null) self.pluginCallback.start();
            }

            @Override
            public void onAdClose() {
                Log.d(TAG, "广告关闭");
                if(self.pluginCallback != null) self.pluginCallback.end();
            }

            @Override
            public void onAdClicked() {
                Log.d(TAG, "广告点击");
                if(self.pluginCallback != null) self.pluginCallback.didClick();
            }
        };
    }

}
