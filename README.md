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
example:
```
{
  "rules": [
    {
      "tag": "A",
      "sort": [
        1,
        3
      ],
      "percent": 50
    },
    {
      "tag": "B",
      "sort": [
        2,
        4
      ],
      "percent": 50
    }
  ],
  "suppliers": [
    {
      "tag": "csj",
      "adspotId": "103226189",
      "appId": "5625617",
      "index": 1
    },
    {
      "tag": "ylh",
      "adspotId": "2001447730515391",
      "appId": "1101152570",
      "index": 2
    },
    {
      "tag": "ks",
      "adspotId": "4000000042",
      "appId": "90009",
      "index": 3
    },
    {
      "tag": "bd",
      "adspotId": "2058622",
      "appId": "e866cfb0",
      "index": 4
    }
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
