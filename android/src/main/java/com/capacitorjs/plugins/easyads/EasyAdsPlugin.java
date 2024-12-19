package com.capacitorjs.plugins.easyads;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

import com.capacitorjs.plugins.easyads.activity.DrawActivity;
import com.capacitorjs.plugins.easyads.controller.BaseController;
import com.capacitorjs.plugins.easyads.controller.FullScreenVideoController;
import com.capacitorjs.plugins.easyads.controller.InterstitialController;
import com.capacitorjs.plugins.easyads.controller.NativeExpressController;
import com.capacitorjs.plugins.easyads.activity.NativeExpressRecyclerViewActivity;
import com.capacitorjs.plugins.easyads.controller.RewardVideoController;
import com.capacitorjs.plugins.easyads.activity.CustomActivity;
import com.capacitorjs.plugins.easyads.controller.SplashController;
import com.capacitorjs.plugins.easyads.model.ConfigModel;
import com.capacitorjs.plugins.easyads.model.EventModel;
import com.capacitorjs.plugins.easyads.model.ResultModel;
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
import java.util.UUID;

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
        AdCallback callback = this.createAdCallback("splash", call.getCallbackId(), name);
        activity.runOnUiThread(() -> {
            BaseController ad = new SplashController(activity, setting);
            //adspotList.put(callId, ad);
            ad.load(callback);
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
        AdCallback callback = this.createAdCallback("banner", call.getCallbackId(), name);
        activity.runOnUiThread(() -> {
            BaseController ad = new BannerController(activity, setting);
            adspotList.put(call.getCallbackId(), ad);
            ad.load(callback);
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
        AdCallback callback = this.createAdCallback("interstitial", call.getCallbackId(), name);
        activity.runOnUiThread(() -> {
            BaseController ad = new InterstitialController(activity, setting);
            //adspotList.put(callId, ad);
            ad.load(callback);
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
        AdCallback callback = this.createAdCallback("rewardVideo", call.getCallbackId(), name);
        activity.runOnUiThread(() -> {
            BaseController ad = new RewardVideoController(activity, setting);
            //adspotList.put(callId, ad);
            ad.load(callback);
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
        AdCallback callback = this.createAdCallback("fullVideo", call.getCallbackId(), name);
        activity.runOnUiThread(() -> {
            BaseController ad = new FullScreenVideoController(activity, setting);
            //adspotList.put(callId, ad);
            ad.load(callback);
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

    private AdCallback createAdCallback(String type, String call, String tag) {
        return new AdCallback() {
            @Override
            public void start() { notifyListeners(type, EventModel.create(type, "start", call, tag, null).toJsObject()); }

            @Override
            public void skip() { notifyListeners(type, EventModel.create(type, "skip", call, tag, null).toJsObject()); }

            @Override
            public void end() { notifyListeners(type, EventModel.create(type, "end", call, tag, null).toJsObject()); }

            @Override
            public void fail(EasyAdError error) { notifyListeners(type, EventModel.create(type, "fail", call, tag, error).toJsObject()); }
        };
    }

}
