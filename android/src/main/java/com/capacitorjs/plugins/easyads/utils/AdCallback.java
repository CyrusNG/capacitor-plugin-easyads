package com.capacitorjs.plugins.easyads.utils;
import com.easyads.model.EasyAdError;
import com.getcapacitor.PluginCall;

/**
 * 开屏跳转回调
 */
public interface AdCallback {
    void notify(String event, PluginCall call, EasyAdError error);
}
