package com.capacitorjs.plugins.easyads;

import android.app.Activity;
import android.content.Intent;

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
import com.capacitorjs.plugins.easyads.model.ResultModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.easyads.core.BuildConfig;
import com.easyads.model.EALogLevel;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.hjq.toast.ToastUtils;
import com.easyads.EasyAds;

import org.json.JSONException;

@CapacitorPlugin(name = "EasyAds")
public class EasyAdsPlugin extends Plugin {

    private ConfigModel config;

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
        JSObject ret = new JSObject();
        ret.put("result", true);
        call.resolve(ret);
    }

    @PluginMethod
    public void splash(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String mode = call.getString("mode", "dialog");
        //将配置转换成EasyADController需要的格式
        // TODO: check setting format
        SettingModel setting = SettingModel.create(this.config, "splash");
        //选择模式
        switch (mode) {
            case "page":
                Intent intent = new Intent(getContext(), SplashActivity.class);
                intent.putExtra("setting", setting);
                startActivityForResult(call, intent, "onStartActivityCallback");
                break;
            case "dialog":
            default:
                Activity activity = getActivity();
                new SplashDialog(activity, setting).show();
        }
    }

    @PluginMethod
    public void banner(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), BannerActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void nativeExpress(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), NativeExpressActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void rewardVideo(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), RewardVideoActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void nativeExpressRecyclerView(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), NativeExpressRecyclerViewActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void interstitial(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), InterstitialActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void fullVideo(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), FullScreenVideoActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void draw(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), DrawActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @PluginMethod
    public void customChannel(PluginCall call) {
        //检查初始化状态
        if(this.config == null) call.reject("Not yet init.", "NOT_INIT");
        //获取参数
        String name = call.getString("name");
        //将配置转换成EasyADController需要的格式
        SettingModel setting = SettingModel.create(this.config, name);
        //加入参数到Intent
        Intent intent = new Intent(getContext(), CustomActivity.class);
        intent.putExtra("setting", setting);
        //打开Activity
        startActivityForResult(call, intent, "onStartActivityCallback");
    }

    @ActivityCallback
    private void onStartActivityCallback(PluginCall call, ActivityResult result) {
        if (call == null) return;
        try {
            ResultModel resultModel = ResultModel.create("SUCCESS", null, null);
            JSObject data = new JSObject(resultModel.toJson());
            call.resolve(data);
        } catch (JSONException e) {
            call.reject(e.getMessage(), "JSONException", e);
        }
    }

}
