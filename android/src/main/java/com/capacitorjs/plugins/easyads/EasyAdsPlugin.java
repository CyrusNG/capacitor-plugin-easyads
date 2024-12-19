package com.capacitorjs.plugins.easyads;

import android.app.Activity;

import com.capacitorjs.plugins.easyads.controller.BaseController;
import com.capacitorjs.plugins.easyads.controller.FullScreenVideoController;
import com.capacitorjs.plugins.easyads.controller.InterstitialController;
import com.capacitorjs.plugins.easyads.controller.RewardVideoController;
import com.capacitorjs.plugins.easyads.controller.SplashController;
import com.capacitorjs.plugins.easyads.model.ConfigModel;
import com.capacitorjs.plugins.easyads.model.EventModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.controller.BannerController;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.core.BuildConfig;
import com.easyads.model.EALogLevel;
import com.easyads.model.EasyAdError;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.hjq.toast.ToastUtils;
import com.easyads.EasyAds;

import java.util.HashMap;

@CapacitorPlugin(name = "EasyAds")
public class EasyAdsPlugin extends Plugin {

    private ConfigModel config;

    private final HashMap<String, BaseController> adspotList = new HashMap<>();

    @PluginMethod
    public void init(PluginCall call) {
        //获取参数
        JSObject config = call.getObject("config");
        //保存配置
        this.config = ConfigModel.create(config);
        //设置debug模式，日志可分等级打印，默认只打印简单的事件信息
        EasyAds.setDebug(BuildConfig.DEBUG, EALogLevel.DEFAULT);
        //自定义渠道-华为广告的初始化，如果不需要自定义可忽略此处
        //HwAds.init(getContext());
        // 初始化 Toast 框架
        ToastUtils.init(getActivity().getApplication());
        // 返回 JSObject 结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));
    }

    @PluginMethod
    public void splash(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        // TODO: check setting format
        SettingModel setting = SettingModel.create(this.config, name);
        //加载Splash广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback("splash", call, name);
        activity.runOnUiThread(() -> {
            BaseController ad = new SplashController(activity, callback, setting, null);
            //adspotList.put(callId, ad);
            ad.load();
        });
        //返回结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));
    }

    @PluginMethod
    public void banner(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加载Banner广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback("banner", call, name);
        activity.runOnUiThread(() -> {
            BaseController ad = new BannerController(activity, callback, setting, null);
            adspotList.put(call.getCallbackId(), ad);
            ad.load();
        });
        //返回结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));
    }

    @PluginMethod
    public void interstitial(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加载插屏广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback("interstitial", call, name);
        activity.runOnUiThread(() -> {
            BaseController ad = new InterstitialController(activity, callback, setting, null);
            //adspotList.put(callId, ad);
            ad.load();
        });
        //返回结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));

    }

    @PluginMethod
    public void reward(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加载激励视频广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback("reward", call, name);
        activity.runOnUiThread(() -> {
            BaseController ad = new RewardVideoController(activity, callback, setting, null);
            //adspotList.put(callId, ad);
            ad.load();
        });
        //返回结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));

    }

    @PluginMethod
    public void fullscreen(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加载全屏视频广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback("fullscreen", call, name);
        activity.runOnUiThread(() -> {
            BaseController ad = new FullScreenVideoController(activity, callback, setting, null);
            //adspotList.put(callId, ad);
            ad.load();
        });
        //返回结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));
    }

    @PluginMethod
    public void destroy(PluginCall call) {
        //获取参数
        String callId = call.getString("callId");
        //查找目标Adspot
        BaseController targetAdspot = this.adspotList.get(callId);
        //销毁广告位(如有)
        if(targetAdspot != null) getActivity().runOnUiThread(() -> targetAdspot.destroy());
    }

    // AdCallback implementation ===============================
    private AdCallback createAdCallback(String type, PluginCall call, String tag) {
        return new AdCallback() {
            @Override
            public void ready() { notifyListeners(type, EventModel.create(type, "ready", call.getCallbackId(), tag, null).toJsObject()); }

            @Override
            public void start() { notifyListeners(type, EventModel.create(type, "start", call.getCallbackId(), tag, null).toJsObject()); }

            @Override
            public void end() { notifyListeners(type, EventModel.create(type, "end", call.getCallbackId(), tag, null).toJsObject()); }

            @Override
            public void fail(EasyAdError error) { notifyListeners(type, EventModel.create(type, "fail", call.getCallbackId(), tag, error).toJsObject()); }

            @Override
            public void didClick() { notifyListeners(type, EventModel.create(type, "did-click", call.getCallbackId(), tag, null).toJsObject()); }

            @Override
            public void didPlay() { notifyListeners(type, EventModel.create(type, "did-play", call.getCallbackId(), tag, null).toJsObject()); }

            @Override
            public void didCache() { notifyListeners(type, EventModel.create(type, "did-cache", call.getCallbackId(), tag, null).toJsObject()); }

            @Override
            public void didRewardable() { notifyListeners(type, EventModel.create(type, "did-rewardable", call.getCallbackId(), tag, null).toJsObject()); }

            @Override
            public void didCountdown() { notifyListeners(type, EventModel.create(type, "did-countdown", call.getCallbackId(), tag, null).toJsObject()); }

            @Override
            public void didSkip() { notifyListeners(type, EventModel.create(type, "did-skip", call.getCallbackId(), tag, null).toJsObject()); }
        };
    }

}
