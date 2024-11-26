package com.capacitorjs.plugins.easyads.adspot;

import com.capacitorjs.plugins.easyads.utils.AdCallback;

public interface BaseAdspot {
    void load(AdCallback pluginCallback);
    void destroy();
}
