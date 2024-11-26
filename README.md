# capacitor-plugin-easyads

A Capacitor Plugin for EasyAds

## Install

```bash
npm install capacitor-plugin-easyads
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------

</docgen-api>

## Configuration
EasyAd中ad.load()配置:
```
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


capacitor-plugin-easyad的init()配置:
```
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
      { tag: "app_splash",            targets: [ "csj-103256285", "ylh-2001447730515391", "bd-2058622", "ks-4000000042" ] },
      { tag: "project_interstitial",  targets: [ "csj-103260324", "ylh-4080298282218338", "bd-2403633", "ks-4000000276" ] },
      { tag: "task_banner",           targets: [ "csj-103256315", "ylh-4080052898050840", "bd-2015351"                  ] },   // 320dp x 50dp
      { tag: "report_reward_video",   targets: [ "csj-103256483", "ylh-9011264358826997", "bd-5989414", "ks-90009001"   ] }
    ]
  }
```

## 问题与解决
* 构建成功，但打开APP闪退报错：`java.lang.RuntimeException: Unable to get provider com.bytedance.sdk.openadsdk.TTFileProvider: java.lang.ClassNotFoundException: Didn't find class "com.bytedance.sdk.openadsdk.TTFileProvider"`？

根项目的gradle.properties添加：
```
# Automatically convert third-party libraries to use AndroidX
android.enableJetifier=true
```
