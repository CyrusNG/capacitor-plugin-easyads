package com.capacitorjs.plugins.easyads;

import android.app.Activity;

import com.capacitorjs.plugins.easyads.controller.BaseController;
import com.capacitorjs.plugins.easyads.controller.FullScreenVideoController;
import com.capacitorjs.plugins.easyads.controller.InterstitialController;
import com.capacitorjs.plugins.easyads.controller.RewardVideoController;
import com.capacitorjs.plugins.easyads.controller.SplashController;
import com.capacitorjs.plugins.easyads.model.ConfigModel;
import com.capacitorjs.plugins.easyads.model.EventModel;
import com.capacitorjs.plugins.easyads.model.OptionModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.controller.BannerController;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.capacitorjs.plugins.easyads.utils.ModelConverter;
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
import java.util.Objects;

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
    public void load(PluginCall call) {
        //获取参数
        String type = call.getString("type");
        String tag = call.getString("tag");
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        if(type == null || tag == null) call.reject("Param invalid.", "TYPE_AND_TAG_REQUIRED");
        //导航到对应函数
        switch(Objects.requireNonNull(type)) {
            case "splash": this.splash(type, tag, call); break;
            case "banner": this.banner(type, tag, call); break;
            case "interstitial": this.interstitial(type, tag, call); break;
            case "reward": this.reward(type, tag, call); break;
            case "fullscreen": this.fullscreen(type, tag, call); break;
            default: call.reject("Unknown ad type.", "UNKNOWN_AD_TYPE"); break;
        }
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


    // Api implementation ===============================
    private void splash(String adType, String adTag, PluginCall call) {
        //将配置和选项转换成EasyADController需要的格式
        SettingModel setting = ModelConverter.convertSetting(this.config, adTag);
        OptionModel option = ModelConverter.convertOption(this.config, adTag);
        //加载Splash广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback();
        activity.runOnUiThread(() -> {
            BaseController ad = new SplashController(activity, call, callback, setting, option);
            adspotList.put(call.getCallbackId(), ad);
            ad.load();
        });
        //返回结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));
    }

    private void banner(String adType, String adTag, PluginCall call) {
        //将配置和选项转换成EasyADController需要的格式
        SettingModel setting = ModelConverter.convertSetting(this.config, adTag);
        OptionModel option = ModelConverter.convertOption(this.config, adTag);
        //加载Banner广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback();
        activity.runOnUiThread(() -> {
            BaseController ad = new BannerController(activity, call, callback, setting, option);
            adspotList.put(call.getCallbackId(), ad);
            ad.load();
        });
        //返回结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));
    }

    private void interstitial(String adType, String adTag, PluginCall call) {
        //将配置和选项转换成EasyADController需要的格式
        SettingModel setting = ModelConverter.convertSetting(this.config, adTag);
        OptionModel option = ModelConverter.convertOption(this.config, adTag);
        //加载插屏广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback();
        activity.runOnUiThread(() -> {
            BaseController ad = new InterstitialController(activity, call, callback, setting, option);
            adspotList.put(call.getCallbackId(), ad);
            ad.load();
        });
        //返回结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));

    }

    private void reward(String adType, String adTag, PluginCall call) {
        //将配置和选项转换成EasyADController需要的格式
        SettingModel setting = ModelConverter.convertSetting(this.config, adTag);
        OptionModel option = ModelConverter.convertOption(this.config, adTag);
        //加载激励视频广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback();
        activity.runOnUiThread(() -> {
            BaseController ad = new RewardVideoController(activity, call, callback, setting, option);
            adspotList.put(call.getCallbackId(), ad);
            ad.load();
        });
        //返回结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));

    }

    private void fullscreen(String adType, String adTag, PluginCall call) {
        //将配置和选项转换成EasyADController需要的格式
        SettingModel setting = ModelConverter.convertSetting(this.config, adTag);
        OptionModel option = ModelConverter.convertOption(this.config, adTag);
        //加载全屏视频广告
        Activity activity = getActivity();
        AdCallback callback = this.createAdCallback();
        activity.runOnUiThread(() -> {
            BaseController ad = new FullScreenVideoController(activity, call, callback, setting, option);
            adspotList.put(call.getCallbackId(), ad);
            ad.load();
        });
        //返回结果
        call.resolve(new JSObject().put("callId", call.getCallbackId()));
    }


    // AdCallback implementation ===============================
    private AdCallback createAdCallback() {
        return (event, call, error) -> notifyListeners(event, EventModel.create(event, call.getString("type"), call.getString("tag"), call.getCallbackId(), error).toJsObject());
    }

}
