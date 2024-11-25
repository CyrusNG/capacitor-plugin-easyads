package com.capacitorjs.plugins.easyads.adspot;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.easyads.core.full.EasyAdFullScreenVideo;

public class FullScreenVideoActivity extends BaseActivity {
    EasyAdFullScreenVideo fullScreenVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
    }


    public void loadFull(View view) {
        // 获取配置
        SettingModel setting = getIntent().getParcelableExtra("setting");
        fullScreenVideo = new EasyADController(this).initFullVideo(setting.toJson());
        fullScreenVideo.loadOnly();

    }

    public void showFull(View view) {
        if (fullScreenVideo != null) {
            fullScreenVideo.show();
        } else {
            EasyADController.logAndToast(this, "需要先调用loadOnly()");
        }
    }

    public void loadAndShowFull(View view) {
        // 获取配置
        SettingModel setting = getIntent().getParcelableExtra("setting");
        new EasyADController(this).initFullVideo(setting.toJson()).loadAndShow();
    }
}
