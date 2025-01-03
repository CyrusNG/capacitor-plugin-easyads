# capacitor-plugin-easyads

[EasyAds](https://github.com/bayescom/EasyAds) 的Capacitor插件!

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
* 暂时只完成Happy Flow的功能开发；

## 安装

```bash
npm install capacitor-plugin-easyads
npx cap sync
```

## 配置

  **注意：** 如你的主项目有相应配置，请因情况适当修改。

### Android

Gradle会自动整合所有子项目的AndroidManifest.xml，因此无需手动配置AndroidManifest.xml。

* 第一步：在主项目build.gradle中添加需要的仓库
```gradle
buildscript {

    repositories {
        google()
        mavenCentral()
        ...
        
        maven { url "https://jitpack.io" }                                     //EasyAds依赖
        maven { url "https://artifact.bytedance.com/repository/pangle" }       //GroMoreSDK依赖
        maven { url "https://developer.huawei.com/repo/" }                     //HuaweiAdsSDK依赖
    }

  ...
}
```

### iOS

考虑到冲突问题，CocoaPods没有整合子项目的Info.plist和PrivacyInfo.xcprivacy，因此请手动配置一下内容：

* 第一步：将以下内容添加到你的主项目中的Info.plist内；

  ```xml
    <!-- all ads SDK API by https but some ads' photo -->
    <key>NSAppTransportSecurity</key>
    <dict>
      <key>NSAllowsArbitraryLoads</key>
      <true/>
    </dict>
    <!-- iOS14+ need user to agree tracking -->
    <key>NSMotionUsageDescription</key>
    <string>App正在请求权限为您更好地提供个性化内容</string>
    <!-- no need ask for user to approve, just require this setting for avoiding crash app -->
    <key>NSUserTrackingUsageDescription</key>
    <string>App正在请求权限为您更好地提供个性化内容</string>
    <!-- ads required if user disagree tracking -->
    <key>SKAdNetworkItems</key>
    <array>
      <dict>
        <key>SKAdNetworkIdentifier</key>
        <string>238da6jt44.skadnetwork</string>
      </dict>
      <dict>
        <key>SKAdNetworkIdentifier</key>
        <string>x2jnk7ly8j.skadnetwork</string>
      </dict>
      <dict>
        <key>SKAdNetworkIdentifier</key>
        <string>22mmun2rn5.skadnetwork</string>
      </dict>
    </array>
  ```

* 第二步：将以下内容添加到你的主项目中的PrivacyInfo.xcprivacy内（如果没有请先创建）；

  ```xml
    <key>NSPrivacyCollectedDataTypes</key>
    <array/>
    <key>NSPrivacyAccessedAPITypes</key>
    <array>
      <dict>
        <key>NSPrivacyAccessedAPIType</key>
        <string>NSPrivacyAccessedAPICategorySystemBootTime</string>
        <key>NSPrivacyAccessedAPITypeReasons</key>
        <array>
          <string>35F9.1</string>
        </array>
      </dict>
      <dict>
        <key>NSPrivacyAccessedAPIType</key>
        <string>NSPrivacyAccessedAPICategoryFileTimestamp</string>
        <key>NSPrivacyAccessedAPITypeReasons</key>
        <array>
          <string>C617.1</string>
        </array>
      </dict>
      <dict>
        <key>NSPrivacyAccessedAPIType</key>
        <string>NSPrivacyAccessedAPICategoryDiskSpace</string>
        <key>NSPrivacyAccessedAPITypeReasons</key>
        <array>
          <string>7D9E.1</string>
          <string>E174.1</string>
        </array>
      </dict>
      <dict>
        <key>NSPrivacyAccessedAPIType</key>
        <string>NSPrivacyAccessedAPICategoryUserDefaults</string>
        <key>NSPrivacyAccessedAPITypeReasons</key>
        <array>
          <string>CA92.1</string>
        </array>
      </dict>
    </array>
  ```
  

## 使用

```javascript



// 初始化 - 可在用户首次确认隐私前调用
await window.EasyAds.init({ config: CONFIG.ads });

// 监听事件 
window.EasyAds.addListener('fail', ({ error }) => { /*业务逻辑*/ });
window.EasyAds.addListener('ready', ({ event, adType, adTag, callId }) => { /*业务逻辑*/ });
window.EasyAds.addListener('start', ({ event, adType, adTag, callId }) => { /*业务逻辑*/ });
window.EasyAds.addListener('end', ({ event, adType, adTag, callId }) => { /*业务逻辑*/ });
window.EasyAds.addListener('did-click', ({ event, adType, adTag, callId }) => { /*业务逻辑*/ });
window.EasyAds.addListener('did-cache', ({ event, adType, adTag, callId }) => { /*业务逻辑*/ });
window.EasyAds.addListener('did-skip', ({ event, adType, adTag, callId }) => { /*业务逻辑*/ });
window.EasyAds.addListener('did-play', ({ event, adType, adTag, callId }) => { /*业务逻辑*/ });
window.EasyAds.addListener('did-rewardable', ({ event, adType, adTag, callId }) => { /*业务逻辑*/ });

// 加载广告
const { callId } = await window.EasyAds.load({ type: "splash", tag: "splash-app-port" });

// 销毁广告
await window.EasyAds.destroy({ callId });

// 检查权限
const permRes = await window.EasyAds.checkPermission({ name: "location" });

// 请求权限 - 所有权限都不是必须的，如涉及权限必须在隐私说明中提及此权限应用于广告，否则影响上架审核
if(permRes !== "granted") await window.EasyAds.requestPermission({ name: "location" });

```

## API

<docgen-index>

* [`init(...)`](#初始化插件)
* [`addListener(...)`](#监听事件)
* [`load(...)`](#加载广告)
* [`show(...)`](#展示广告)
* [`destroy(...)`](#销毁广告)
* [`checkPermission(...)`](#请求权限)
* [`requestPermission(...)`](#检查权限)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->


### 数据结构

#### Config

| 属性          | 类型                                          |
| ------------- | -------------------------------------------- |
| **`rules`**   | <code>Array&lt;{ tag: string, sort: Array&lt;number&gt;, percent: number }&gt;</code> |
| **`apps`**    | <code>Array&lt;{ tag: string, appId: string, index: number }&gt;</code> |
| **`adspots`** | <code>Array&lt;{ tag: string, targets: Array&lt;string&gt;, options: [`Options`](#Options) }&gt;</code> |

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
    { tag: "splash-app-port",        targets: [ "csj-103256285", "ylh-2001447730515391", "bd-2058622", "ks-4000000042" ], options: { showLater: true, showLogo: false } },
    { tag: "interhalf-project-port", targets: [ "csj-103260324", "ylh-4080298282218338", "bd-2403633", "ks-4000000276" ], options: { }                                  },
    { tag: "banner-task-320x50",     targets: [ "csj-103256315", "ylh-4080052898050840", "bd-2015351"                  ], options: { width: 320, height: 50}            },
    { tag: "reward-report-port",     targets: [ "csj-103256483", "ylh-9011264358826997", "bd-5989414", "ks-90009001"   ], options: { }                                  }
  ]
}
```

#### Options

| 属性            | 类型                        | 说明                                        |
| --------------- | -------------------------- | ------------------------------------------ |    
| **`showLater`** | <code>string</code>        | 适用于: 所有广告，随后调用show(...)展示广告     |
| **`showLogo`**  | <code>string</code>        | 适用于: 开屏广告(iOS)                        |     
| **`width`**     | <code>Integer</code>       | 适用于: 横幅广告                             |     
| **`height`**    | <code>Integer</code>       | 适用于: 横幅广告                             |     

#### Exception

| 属性          | 类型                                       |
| ------------- | ----------------------------------------- |
| **`code`**    | <code>string</code>                       |
| **`message`** | <code>string</code>                       |
| **`data`**    | <code>Object</code>                       |


### 接口

### 初始化插件

```typescript
init({ config: Config }) => Promise<Result>
```

| 参数          | 类型                                      | 说明  
| ------------ | ----------------------------------------- | ------------ 
| **`config`** | <code><a href="#config">Config</a></code> | 必须先初始化再调用其他API

**Returns:** <code>Promise&lt;{ callId: string }&gt;</code>

**Throw:** <code><a href="#result">Exception</a></code>



### 监听事件

```typescript
addListener(event: string, callback: function) => void
```

| 参数            | 类型                                      | 说明  
| -------------- | ----------------------------------------- | ------------ 
| **`event`**    | <code>string</code> | 事件类型：fail / ready / start / end / did-click / did-cache / did-skip / did-play / did-rewardable
| **`callback`** | <code>(info) => {...}</code> | info结构：{ event, adType, adTag, callId } / { error }

**Returns:** void

**Throw:** <code><a href="#result">Exception</a></code>



### 加载广告

```typescript
load({ type: string, tag: string }) => Promise<Result>
```

| 参数       | 类型                 | 说明
| ---------- | ------------------- | --------------
| **`type`** | <code>string</code> | 广告类型：splash / banner / interstitial / reward / fullscreen
| **`tag`**  | <code>string</code> | 广告位名称（对应Config内的tag名），如：app_splash

**Returns:** <code>Promise&lt;{ callId: string }&gt;</code>

**Throw:** <code><a href="#result">Exception</a></code>



### 展示广告

```typescript
show({ callId: string }) => Promise<Result>
```

| 参数          | 类型                 | 说明
| ------------- | ------------------- | --------------
| **`callId`**  | <code>string</code> | 广告callId，如：14912745，仅在Options设置为`showLater: true`时才有效！

**Returns:** <code>Promise&lt;{ callId: string }&gt;</code>

**Throw:** <code><a href="#result">Exception</a></code>


### 销毁广告

```typescript
destroy({ callId: string }) => Promise<Result>
```

| 参数          | 类型                 | 说明
| ------------- | ------------------- | --------------
| **`callId`**  | <code>string</code> | 广告callId，如：14912745

**Returns:** <code>Promise&lt;{ callId: string }&gt;</code>

**Throw:** <code><a href="#result">Exception</a></code>


### 检查权限

```typescript
checkPermission({ name: string }) => Promise<Result>
```

| 参数       | 类型                 | 说明
| ---------- | ------------------- | --------------
| **`name`**  | <code>string</code> | 权限名，如 location / storage / phone / install / track

**Returns:** <code>Promise&lt;{ state: "grant" | "denied" | "never" | "asked" | "unknown" }&gt;</code>

**Throw:** <code><a href="#result">Exception</a></code>


### 请求权限

```typescript
requestPermission({ name: string }) => Promise<Result>
```

| 参数       | 类型                 | 说明
| ---------- | ------------------- | --------------
| **`name`**  | <code>string</code> | 权限名，如 location / storage / phone / install / track

**Returns:** <code>Promise&lt;{ state: "grant" | "denied" | "never" | "asked" | "unknown" }&gt;</code>

**Throw:** <code><a href="#result">Exception</a></code>

</docgen-api>

## 问题与解决

### 1、无法在arm64的simulator中运行

原因：优量汇的libGDTMobSDK.a预编译文件不支持arm64架构的iPhone模拟器

解决办法：配置 @ app -> Build Settings Architectures -> Exclude Architectures -> Debug/Release -> Any iOS Simulator SDK: arm64

 [<img src="https://github.com/CyrusNG/capacitor-plugin-easyads/blob/575cda0de6fee22b59f87c6a1f11016cc4510a6d/doc/readme-settingExcludeArm64.png"/>](readme-settingExcludeArm64.png)


### 2、构建成功，但打开APP闪退报错

错误：`java.lang.RuntimeException: Unable to get provider com.bytedance.sdk.openadsdk.TTFileProvider: java.lang.ClassNotFoundException: Didn't find class "com.bytedance.sdk.openadsdk.TTFileProvider"`

解决办法：根项目的gradle.properties添加以下配置
```properties
# Automatically convert third-party libraries to use AndroidX
android.enableJetifier=true
```

## 参考
EasyAd中ad.load()配置格式 - 仅供参考不需配置，程序内会自动将init()输入的参数转成以下格式传去EasyAdSDK:
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