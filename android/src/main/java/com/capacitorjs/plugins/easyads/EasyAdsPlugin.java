package com.capacitorjs.plugins.easyads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;

import com.capacitorjs.plugins.easyads.activity.BannerActivity;
import com.capacitorjs.plugins.easyads.activity.DrawActivity;
import com.capacitorjs.plugins.easyads.activity.FullScreenVideoActivity;
import com.capacitorjs.plugins.easyads.activity.InterstitialActivity;
import com.capacitorjs.plugins.easyads.activity.NativeExpressActivity;
import com.capacitorjs.plugins.easyads.activity.NativeExpressRecyclerViewActivity;
import com.capacitorjs.plugins.easyads.activity.RewardVideoActivity;
import com.capacitorjs.plugins.easyads.activity.SplashActivity;
import com.capacitorjs.plugins.easyads.activity.CustomActivity;
import com.capacitorjs.plugins.easyads.dialog.SplashDialog;
import com.capacitorjs.plugins.easyads.model.ConfigModel;
import com.easyads.core.BuildConfig;
import com.easyads.model.EALogLevel;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.easyads.EasyAds;

import org.json.JSONException;
import org.json.JSONObject;

@CapacitorPlugin(name = "EasyAds")
public class EasyAdsPlugin extends Plugin {
    
    @PluginMethod
    public void init(PluginCall call) {
        String value = call.getString("value", "defaultValue");

        //设置debug模式，日志可分等级打印，默认只打印简单的事件信息
        EasyAds.setDebug(BuildConfig.DEBUG, EALogLevel.DEFAULT);

        //自定义渠道-华为广告的初始化，如果不需要自定义可忽略此处
        //HwAds.init(getContext());

        // 初始化 Toast 框架
        ToastUtils.init(getActivity().getApplication());

        // 返回 JSObject 结果
        JSObject ret = new JSObject();
        ret.put("result", true);
        call.resolve(ret);
    }

    @PluginMethod
    public void splash(PluginCall call) {
        String mode = call.getString("mode", "dialog");
        JSObject config = call.getObject("config");
//        ConfigModel x = ConfigModel.fromJson(config);
//        String test = ConfigModel.toJson(x);
        // TODO: check settings format
        switch (mode) {
            case "page":
                Intent intent = new Intent(getContext(), SplashActivity.class);
                intent.putExtra("config", config.toString());
                startActivityForResult(call, intent, "onStartActivityCallback");
                break;
            case "dialog":
            default:
                Activity activity = getActivity();
                new SplashDialog(activity).show();
        }
    }

    @PluginMethod
    public void banner(PluginCall call) {
        JSObject config = call.getObject("config");
        Intent intent = new Intent(getContext(), BannerActivity.class);
        intent.putExtra("config", config.toString());
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void nativeExpress(PluginCall call) {
        JSObject config = call.getObject("config");
        Intent intent = new Intent(getContext(), NativeExpressActivity.class);
        intent.putExtra("config", config.toString());
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void rewardVideo(PluginCall call) {
        JSObject config = call.getObject("config");
        Intent intent = new Intent(getContext(), RewardVideoActivity.class);
        intent.putExtra("config", config.toString());
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void nativeExpressRecyclerView(PluginCall call) {
        JSObject config = call.getObject("config");
        Intent intent = new Intent(getContext(), NativeExpressRecyclerViewActivity.class);
        intent.putExtra("config", config.toString());
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void interstitial(PluginCall call) {
        JSObject config = call.getObject("config");
        Intent intent = new Intent(getContext(), InterstitialActivity.class);
        intent.putExtra("config", config.toString());
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void fullVideo(PluginCall call) {
        JSObject config = call.getObject("config");
        Intent intent = new Intent(getContext(), FullScreenVideoActivity.class);
        intent.putExtra("config", config.toString());
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void draw(PluginCall call) {
        JSObject config = call.getObject("config");
        Intent intent = new Intent(getContext(), DrawActivity.class);
        intent.putExtra("config", config.toString());
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void customChannel(PluginCall call) {
        JSObject config = call.getObject("config");
        Intent intent = new Intent(getContext(), CustomActivity.class);
        intent.putExtra("config", config.toString());
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @ActivityCallback
    private void onStartActivityCallback(PluginCall call, ActivityResult result) {
        if (call == null) return;
        try {
            String jsonInString = new Gson().toJson(result);
            JSObject data = new JSObject(jsonInString);
            call.resolve(data);
        } catch (JSONException e) {
            call.reject(e.getMessage(), "JSONException", e);
        }
    }

}
