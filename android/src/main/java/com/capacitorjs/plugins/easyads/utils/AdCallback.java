package com.capacitorjs.plugins.easyads.utils;

import com.easyads.model.EasyAdError;

/**
 * 开屏跳转回调
 */
public interface AdCallback {
    void start();
    void skip();
    void end();
    void fail(EasyAdError error);
}
