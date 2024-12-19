package com.capacitorjs.plugins.easyads.controller;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.capacitorjs.plugins.easyads.model.OptionModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.core.full.EAFullScreenVideoListener;
import com.easyads.core.full.EasyAdFullScreenVideo;
import com.easyads.model.EasyAdError;

public class FullScreenVideoController implements BaseController {
    Activity context;
    AdCallback pluginCallback;
    SettingModel setting;
    OptionModel option;
    private static final String TAG = FullScreenVideoController.class.getSimpleName();

    public FullScreenVideoController(@NonNull final Activity context, AdCallback pluginCallback, SettingModel setting, OptionModel option) {
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
     * 初始化获取展示全屏视频的广告对象。
     * 也可以选择先提前加载，然后在合适的时机再调用展示方法
     */
    @Override
    public void load() {
        //先销毁广告（如有）
        this.destroy();
        //初始化
        EasyAdFullScreenVideo easyFullScreenVideo = new EasyAdFullScreenVideo(this.context, this.createListeners());
        //必须：设置策略信息
        easyFullScreenVideo.setData(this.setting.toJsonString());
        //返回实例
        easyFullScreenVideo.loadAndShow();
    }


    @Override
    public void destroy() {
        //销毁广告
    }


    // MARK: ======================= Banner Listeners =======================
    private EAFullScreenVideoListener createListeners() {
        FullScreenVideoController self = this;
        //必须：核心事件监听回调
        return new EAFullScreenVideoListener() {

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

            @Override
            public void onVideoCached() {

                Log.d(TAG, "广告缓存成功");
                if(self.pluginCallback != null) self.pluginCallback.didCache();
            }

            @Override
            public void onVideoSkipped() {
                Log.d(TAG, "跳过视频播放");
                if(self.pluginCallback != null) self.pluginCallback.didSkip();
            }

            @Override
            public void onVideoComplete() {
                Log.d(TAG, "视频播放完毕");
                if(self.pluginCallback != null) self.pluginCallback.didPlay();
            }
        };
    }
}