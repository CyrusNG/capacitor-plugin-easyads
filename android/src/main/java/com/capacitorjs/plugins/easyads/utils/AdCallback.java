package com.capacitorjs.plugins.easyads.utils;

import com.easyads.model.EasyAdError;

/**
 * 开屏跳转回调
 */
public interface AdCallback {
    void ready();
    void start();
    void end();
    void fail(EasyAdError error);
    void didClick();
    void didPlay();
    void didCache();
    void didRewardable();
    void didCountdown();
    void didSkip();
}
