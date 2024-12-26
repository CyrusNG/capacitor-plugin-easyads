package com.capacitorjs.plugins.easyads.controller;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.capacitorjs.plugins.easyads.model.OptionModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.core.reward.EARewardServerCallBackInf;
import com.easyads.core.reward.EARewardVideoListener;
import com.easyads.core.reward.EasyAdRewardVideo;
import com.easyads.model.EasyAdError;
import com.getcapacitor.PluginCall;

public class RewardVideoController implements BaseController {
    Activity context;
    PluginCall call;
    AdCallback pluginCallback;
    EasyAdRewardVideo easyRewardVideo;
    SettingModel setting;
    OptionModel option;
    private static final String TAG = RewardVideoController.class.getSimpleName();

    public RewardVideoController(@NonNull final Activity context, PluginCall call, AdCallback pluginCallback, SettingModel setting, OptionModel option) {
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
    }

    /**
     * 加载并展示激励视频广告。
     * 也可以选择性先提前加载，然后在合适的时机再调用展示方法
     */
    @Override
    public void load() {
        //先销毁广告（如有）
        this.destroy();
        //初始化，注意需要时再初始化，不要复用。
        this.easyRewardVideo = new EasyAdRewardVideo(this.context, this.createListeners());
        //必须：设置策略信息
        this.easyRewardVideo.setData(this.setting.toJsonString());
        //必须：请求/展示广告
        if(this.option.showLater()) { this.easyRewardVideo.loadOnly(); }
        else {  this.easyRewardVideo.loadAndShow(); }
        //展示提示
        Log.d(TAG, "广告请求中");
    }

    @Override
    public void show() {
        this.easyRewardVideo.show();
    }

    @Override
    public void destroy() {
        //销毁广告
    }


    // MARK: ======================= Banner Listeners =======================
    private EARewardVideoListener createListeners() {
        RewardVideoController self = this;
        //必须：核心事件监听回调
        return new EARewardVideoListener() {

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
            @Override
            public void onVideoCached() {

                Log.d(TAG, "广告缓存成功");
                if(self.pluginCallback != null) self.pluginCallback.notify("did-cache", self.call, null);
            }
            @Override
            public void onVideoComplete() {
                Log.d(TAG, "视频播放完毕");
                if(self.pluginCallback != null) self.pluginCallback.notify("did-play", self.call, null);
            }
            @Override
            public void onVideoSkip() {
                Log.d(TAG, "跳过视频播放");
                if(self.pluginCallback != null) self.pluginCallback.notify("did-skip", self.call, null);
            }
            @Override
            public void onAdReward() {
                Log.d(TAG, "激励发放");
                if(self.pluginCallback != null) self.pluginCallback.notify("did-rewardable", self.call, null);
            }

            @Override
            public void onRewardServerInf(EARewardServerCallBackInf inf) { //优量汇和穿山甲支持回调服务端激励验证信息，详见RewardServerCallBackInf中字段信息
                Log.d(TAG, "激励服务端验证" + inf);
            }
        };
    }
}
