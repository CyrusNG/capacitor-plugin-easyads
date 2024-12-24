# capacitor-plugin-easyads

[EasyAds](https://github.com/bayescom/EasyAds) 的Capacitor插件，未完全开发完成！！！

## 1. 支持的SDK平台

| SDK平台 | iOS版本号 | Android版本号   | 开屏 | 激励视频 | 横幅 | 插屏(弹窗) | 全屏视频 | 模板信息流 | draw信息流 |
|--------|----------|----------------|-----|--------|------|-----------|---------|----------|-----------| 
| 穿山甲  |6.5.0.9   | 6.1.2.5        |✅   | ✅      | ✅   | ✅        | ✅       | ❌       | ❌        |
| 优量汇  |4.15.10   | 4.603.1473     |✅   | ✅      | ✅   | ✅        | ✅       | ❌       | ❌        |
| 百青藤  |5.352     | 9.3.52         |✅   | ✅      | ✅   | ❌        | ✅       | ❌       | ❌        |
| 快手   |3.3.69     | 3.3.63         |✅   | ✅      | ❌   | ✅        | ✅       | ❌       | ❌        |

**注意:** 
* 穿山甲是GroMore；
* 暂时只测试过穿山甲GroMore，其他平台可以自行测试；
* 暂时只完成Happy Flow的开发，未完成权限申请等功能；

## 安装

```bash
npm install capacitor-plugin-easyads
npx cap sync
```

## API

<docgen-index>

* [`init(...)`](#init)
* [`load(...)`](#load)
* [`destroy(...)`](#destroy)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### Interfaces


#### Config

| 属性          | 类型                                          |
| ------------- | -------------------------------------------- |
| **`rules`**   | <code><a href="#array">Array</a>&lt;{ tag: string, sort: <a href="#array">Array</a>&lt;number&gt;, percent: number }&gt;</code> |
| **`apps`**    | <code><a href="#array">Array</a>&lt;{ tag: string, appId: string, index: number }&gt;</code> |
| **`adspots`** | <code><a href="#array">Array</a>&lt;{ tag: string, targets: <a href="#array">Array</a>&lt;string&gt;, options: <a href="#object">Object</a> }&gt;</code> |

格式范例:
```json
{ 
  rules: [
    { tag: "rule-1", sort: [ 1, 3 ], percent: 50 },
    { tag: "rule-2", sort: [ 2, 4 ], percent: 50 }
  ],
  apps: [
    { tag: "csj", appId: "5625617",    index: 1 },
    { tag: "ylh", appId: "1101152570", index: 2 },
    { tag: "ks",  appId: "90009",      index: 3 },
    { tag: "bd",  appId: "e866cfb0",   index: 4 }
  ],
  adspots: [
    { tag: "app_splash",           targets: [ "csj-103256285", "ylh-2001447730515391", "bd-2058622", "ks-4000000042" ], options: { showLogo: false }       },
    { tag: "project_interstitial", targets: [ "csj-103260324", "ylh-4080298282218338", "bd-2403633", "ks-4000000276" ], options: { }                       },
    { tag: "task_banner",          targets: [ "csj-103256315", "ylh-4080052898050840", "bd-2015351"                  ], options: { width: 320, height: 50} },
    { tag: "report_reward_video",  targets: [ "csj-103256483", "ylh-9011264358826997", "bd-5989414", "ks-90009001"   ], options: { }                       }
  ]
}
```

#### Result

| Prop        | Type                                      |
| ----------- | ----------------------------------------- |
| **`code`**  | <code>string</code>                       |
| **`data`**  | <code><a href="#object">Object</a></code> |
| **`error`** | <code><a href="#object">Object</a></code> |




### init(...)

```typescript
init({ config: Config }) => Promise<Result>
```

| 参数          | 类型                                      | 说明  
| ------------ | ----------------------------------------- | ------------ 
| **`config`** | <code><a href="#config">Config</a></code> | 必须先初始化在调用其他API

**Returns:** <code>Promise&lt;<a href="#result">Result</a>&gt;</code>


### load(...)

```typescript
load({ type: string, tag: string }) => Promise<Result>
```

| 参数       | 类型                 | 说明
| ---------- | ------------------- | --------------
| **`type`** | <code>string</code> | 广告类型，如 splash / banner / interstitial / reward_video / fullscreen_video
| **`tag`**  | <code>string</code> | 广告位名称，对应Config内的tag名，如 app_splash

**Returns:** <code>Promise&lt;<a href="#result">Result</a>&gt;</code>


### destroy(...)

```typescript
destroy({ callId: string }) => Promise<Result>
```

| 参数          | 类型                 | 说明
| ------------- | ------------------- | --------------
| **`callId`**  | <code>string</code> | 广告callId,如：14912745

**Returns:** <code>Promise&lt;<a href="#result">Result</a>&gt;</code>


</docgen-api>

## 参考
EasyAd中ad.load()配置格式 - 仅供参考不需配置，程序内会自动将init()输入的参数转成以下格式传去EasyAdSDK:
```json
{
    rules: [
      { tag: "rule-1", sort: [ 1, 3 ], percent: 50 },
      { tag: "rule-2", sort: [ 2, 4 ], percent: 50 }
    ],
    suppliers: [
      { tag: "csj", appId: "5625617",    index: 1, "adspotId": "103226189" },
      { tag: "ylh", appId: "1101152570", index: 2, "adspotId": "2001447730515391" },
      { tag: "ks",  appId: "90009",      index: 3, "adspotId": "4000000042" },
      { tag: "bd",  appId: "e866cfb0",   index: 4, "adspotId": "2058622" }
    ]
}
```

## 问题与解决
### 1、编译报错尝试加以下脚本：
```pod
post_install do |installer|
  
  assertDeploymentTarget(installer)
  
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      # for support ads
      if target.name == 'Masonry'
        config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = $target              # build fail on latest xCode 16 for masonry default old date deployment target 8.0
        config.build_settings['EXCLUDED_ARCHS[sdk=iphonesimulator*]'] = 'arm64'    # GDT's libGDTMobSDK.a NOT support arm64 iPhone simulator
      end
    end
  end
  
end
```



### 2、构建成功，但打开APP闪退报错

错误：`java.lang.RuntimeException: Unable to get provider com.bytedance.sdk.openadsdk.TTFileProvider: java.lang.ClassNotFoundException: Didn't find class "com.bytedance.sdk.openadsdk.TTFileProvider"`

解决办法：根项目的gradle.properties添加以下配置
```properties
# Automatically convert third-party libraries to use AndroidX
android.enableJetifier=true
```
