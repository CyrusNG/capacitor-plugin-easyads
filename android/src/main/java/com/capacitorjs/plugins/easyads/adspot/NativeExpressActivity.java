package com.capacitorjs.plugins.easyads.adspot;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;

public class NativeExpressActivity extends BaseActivity {
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_express);
        container = findViewById(R.id.native_express_container);
        SettingModel setting = getIntent().getParcelableExtra("setting");
        new EasyADController(this).loadNativeExpress(setting.toJson(),container);
    }

    public void loadNEAD(View view) {
        SettingModel setting = getIntent().getParcelableExtra("setting");
        new EasyADController(this).loadNativeExpress(setting.toJson(),container);
    }
}