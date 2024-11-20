package com.capacitorjs.plugins.easyads.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;

public class NativeExpressActivity extends BaseActivity {
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_express);
        container = findViewById(R.id.native_express_container);
        String configJson = getIntent().getStringExtra("config");
        new EasyADController(this).loadNativeExpress(configJson,container);
    }

    public void loadNEAD(View view) {
        String configJson = getIntent().getStringExtra("config");
        new EasyADController(this).loadNativeExpress(configJson,container);
    }
}