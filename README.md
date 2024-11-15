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


## 问题与解决
* 构建成功，但打开APP闪退报错：`java.lang.RuntimeException: Unable to get provider com.bytedance.sdk.openadsdk.TTFileProvider: java.lang.ClassNotFoundException: Didn't find class "com.bytedance.sdk.openadsdk.TTFileProvider"`？

根项目的gradle.properties添加：
```
# Automatically convert third-party libraries to use AndroidX
android.enableJetifier=true
```
