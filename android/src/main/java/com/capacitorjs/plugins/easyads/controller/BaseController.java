package com.capacitorjs.plugins.easyads.controller;

import com.capacitorjs.plugins.easyads.utils.AdCallback;

public interface BaseController {
    void load(AdCallback pluginCallback);
    void destroy();
}