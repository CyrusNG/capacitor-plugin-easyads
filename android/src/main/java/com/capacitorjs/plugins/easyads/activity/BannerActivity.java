package com.capacitorjs.plugins.easyads.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;

public class BannerActivity extends BaseActivity {
    RelativeLayout rl;
    EasyADController ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        rl = findViewById(R.id.banner_layout);

        //初始化广告处理封装类
        ad = new EasyADController(this);
        // 获取配置
        String configJson = getIntent().getStringExtra("config");
        //加载banner
        ad.loadBanner(configJson, rl);
    }

    public void loadBanner(View view) {
        if (ad != null) { 
            // 获取配置
            String configJson = getIntent().getStringExtra("config");
            ad.destroy();
            ad.loadBanner(configJson, rl);
        }
    }
}
