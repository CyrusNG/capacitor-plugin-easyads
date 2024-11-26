package com.capacitorjs.plugins.easyads;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;

import com.capacitorjs.plugins.easyads.activity.DrawActivity;
import com.capacitorjs.plugins.easyads.adspot.FullScreenVideoAdspot;
import com.capacitorjs.plugins.easyads.adspot.InterstitialAdspot;
import com.capacitorjs.plugins.easyads.adspot.NativeExpressAdspot;
import com.capacitorjs.plugins.easyads.activity.NativeExpressRecyclerViewActivity;
import com.capacitorjs.plugins.easyads.adspot.RewardVideoAdspot;
import com.capacitorjs.plugins.easyads.activity.CustomActivity;
import com.capacitorjs.plugins.easyads.adspot.SplashAdspot;
import com.capacitorjs.plugins.easyads.model.ConfigModel;
import com.capacitorjs.plugins.easyads.model.EventModel;
import com.capacitorjs.plugins.easyads.model.ResultModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.adspot.BannerAdspot;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.core.BuildConfig;
import com.easyads.model.EALogLevel;
import com.easyads.model.EasyAdError;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.hjq.toast.ToastUtils;
import com.easyads.EasyAds;

import java.util.UUID;

@CapacitorPlugin(name = "EasyAds")
public class EasyAdsPlugin extends Plugin {

    private ConfigModel config;

    @PluginMethod
    public void init(PluginCall call) {
        //生成随机callId
        String callId = UUID.randomUUID().toString();
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
        call.resolve(ResultModel.create(callId).toJsObject());
    }

    @PluginMethod
    public void splash(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //生成随机callId
        String callId = UUID.randomUUID().toString();
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        // TODO: check setting format
        SettingModel setting = SettingModel.create(this.config, name);
        //加载Splash广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback("splash", callId, name);
        activity.runOnUiThread(() -> new SplashAdspot(activity, setting).load(callback));
        //返回结果
        call.resolve(ResultModel.create(callId).toJsObject());
    }

    @PluginMethod
    public void banner(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //生成随机callId
        String callId = UUID.randomUUID().toString();
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加载Banner广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback("banner", callId, name);
        activity.runOnUiThread(() -> new BannerAdspot(activity, setting).load(callback));
        //返回结果
        call.resolve(ResultModel.create(callId).toJsObject());
    }

    @PluginMethod
    public void interstitial(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //生成随机callId
        String callId = UUID.randomUUID().toString();
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加载插屏广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback("interstitial", callId, name);
        activity.runOnUiThread(() -> new InterstitialAdspot(activity, setting).load(callback));
        //返回结果
        call.resolve(ResultModel.create(callId).toJsObject());

    }

    @PluginMethod
    public void rewardVideo(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //生成随机callId
        String callId = UUID.randomUUID().toString();
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加载激励视频广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback("interstitial", callId, name);
        activity.runOnUiThread(() -> new RewardVideoAdspot(activity, setting).load(callback));
        //返回结果
        call.resolve(ResultModel.create(callId).toJsObject());

    }

    @PluginMethod
    public void fullVideo(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //生成随机callId
        String callId = UUID.randomUUID().toString();
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加载全屏视频广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback("interstitial", callId, name);
        activity.runOnUiThread(() -> new FullScreenVideoAdspot(activity, setting).load(callback));
        //返回结果
        call.resolve(ResultModel.create(callId).toJsObject());
    }

    @PluginMethod
    public void nativeExpress(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //生成随机callId
        String callId = UUID.randomUUID().toString();
        //获取参数
        String name = call.getString("name");
        Integer containerId = call.getInt("containerId");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加载原生模板广告
        Activity activity = getActivity();
        ViewGroup nativeContainer = (ViewGroup) activity.findViewById(containerId);
        AdCallback callback = this.createAdCallback("interstitial", callId, name);
        activity.runOnUiThread(() -> new NativeExpressAdspot(activity, setting, nativeContainer).load(callback));
        //返回结果
        call.resolve(ResultModel.create(callId).toJsObject());
    }

    @PluginMethod
    public void nativeExpressRecyclerView(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //生成随机callId
        String callId = UUID.randomUUID().toString();
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), NativeExpressRecyclerViewActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, null);
        //返回结果
        call.resolve(ResultModel.create(callId).toJsObject());
    }

    @PluginMethod
    public void draw(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //生成随机callId
        String callId = UUID.randomUUID().toString();
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), DrawActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, null);
        //返回结果
        call.resolve(ResultModel.create(callId).toJsObject());
    }

    @PluginMethod
    public void customChannel(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //生成随机callId
        String callId = UUID.randomUUID().toString();
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), CustomActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, null);
        //返回结果
        call.resolve(ResultModel.create(callId).toJsObject());
    }

    @ActivityCallback
    private void onStartActivityCallback(PluginCall call, ActivityResult result) {
        if (call == null) return;
        call.resolve(ResultModel.create("<CallID>").toJsObject());
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
