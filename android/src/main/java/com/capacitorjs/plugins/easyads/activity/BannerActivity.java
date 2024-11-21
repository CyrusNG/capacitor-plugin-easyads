package com.capacitorjs.plugins.easyads.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;

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
        SettingModel setting = getIntent().getParcelableExtra("setting");
        //加载banner
        ad.loadBanner(setting.toJson(), rl);
    }

    public void loadBanner(View view) {
        if (ad != null) { 
            // 获取配置
            SettingModel setting = getIntent().getParcelableExtra("setting");
            ad.destroy();
            ad.loadBanner(setting.toJson(), rl);
        }
    }
}
