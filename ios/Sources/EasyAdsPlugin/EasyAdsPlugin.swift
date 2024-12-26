import Foundation
import Capacitor
import CoreLocation
import AppTrackingTransparency
import AdSupport

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(EasyAdsPlugin)
public class EasyAdsPlugin: CAPPlugin, CAPBridgedPlugin, AdCallbackProtocol {
    
    public let identifier = "EasyAdsPlugin"
    public let jsName = "EasyAds"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "init", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "load", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "show", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "destroy", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkPermission", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermission", returnType: CAPPluginReturnPromise)
    ]
    
    private var config: ConfigModel?
    private var adspotList: [String:any AdControllerProtocol] = [:]
    
    @objc func `init`(_ call: CAPPluginCall) {
        //获取参数
        let config = call.getObject("config")
        //保存配置
        if(config != nil) { self.config = ConfigModel.easyAd_model(withJSON: config! as Dictionary) }
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    @objc func load(_ call: CAPPluginCall) {
        //获取参数
        let type = call.getString("type")
        let tag = call.getString("tag")
        //错误检查
        if(self.config == nil) { call.reject("Not yet init.", "NOT_INIT"); return }
        if(type == nil || tag == nil) { call.reject("Param invalid.", "TYPE_AND_TAG_REQUIRED"); return }
        //导航到对应函数
        switch(type) {
            case "splash": self.splash(adType:type!, adTag:tag!, call:call)
            case "banner": self.banner(adType:type!, adTag:tag!, call:call)
            case "interstitial": self.interstitial(adType:type!, adTag:tag!, call:call)
            case "reward": self.reward(adType:type!, adTag:tag!, call:call)
            case "fullscreen": self.fullscreen(adType:type!, adTag:tag!, call:call)
            default: call.reject("Unknown ad type.", "UNKNOWN_AD_TYPE")
        }
    }
    
    @objc func show(_ call: CAPPluginCall) {
        let callId = call.getString("callId") ?? ""
        self.adspotList[callId]?.show()
        call.resolve(["callId": callId])
    }
    
    @objc func destroy(_ call: CAPPluginCall) {
        let callId = call.getString("callId") ?? ""
        self.adspotList[callId]?.destroy()
        call.resolve(["callId": callId])
    }
    
    // Permission implementation ===============================
    @objc func checkPermission(_ call: CAPPluginCall) {
        //获取参数
        let name = call.getString("name")
        //检查参数
        if(name == nil || !["track"].contains(name!)) { call.reject("Unknown permission type.", "UNKNOWN_PERMISSION_TYPE"); return }
        //检查授权
        call.resolve(["state": self.checkPermissionState(name)])
    }
    
    @objc func requestPermission(_ call: CAPPluginCall) {
        //获取参数
        let name = call.getString("name")
        //检查参数
        if(name == nil || !["track"].contains(name!)) { call.reject("Unknown permission type.", "UNKNOWN_PERMISSION_TYPE"); return }
        //请求授权
        switch self.checkPermissionState(name) {
            case "denied":
                //跳转到APP设置页
                UIApplication.shared.open(URL(string: UIApplication.openSettingsURLString)!, options: [:], completionHandler: nil)
            case "never", "asked":
                //请求权限
                if #available(iOS 14, *) { ATTrackingManager.requestTrackingAuthorization(completionHandler: { status in self.permissionCallback(call) }) }
                else { self.permissionCallback(call) }
            case "granted", "unknown": fallthrough
            default:
                //返回状态
                call.resolve(["state": self.checkPermissionState(name)])
        }
    }
    
    private func permissionCallback(_ call: CAPPluginCall) {
        //获取参数
        let name = call.getString("name");
        // 返回 JSObject 结果
        call.resolve(["state": self.checkPermissionState(name)])
    }
    
    private func checkPermissionState(_ name: String?) -> String {
        let state: String
        switch name {
            case "track":
                if #available(iOS 14, *) {
                    switch ATTrackingManager.trackingAuthorizationStatus {
                        case .notDetermined: state = "never"
                        case .restricted, .denied: state = "denied"
                        case .authorized: state = "granted"
                        @unknown default: state = "asked"
                    }
                } else { state = "granted" }
            default:
                state = "unknown"
        }
        return state
    }
    
    
    // Api implementation ===============================
    private func splash(adType: String, adTag: String, call: CAPPluginCall) {
        //获取viewController
        let vc = self.bridge?.viewController
        //将配置转换成Controller需要的格式
        let setting = ModelConverter().convertSetting(fromConfig: self.config!, adspotTag: adTag)
        let option = ModelConverter().convertOption(fromConfig: self.config!, adspotTag: adTag)
        //错误检查
        if(setting == nil) { call.reject("Invalid name.", "INVALID_NAME"); return }
        if(vc == nil) { call.reject("Not found view controller.", "NOT_FOUND_VIEW_CONTROLLER"); return }
        //初始化广告控制器
        let splashCtlr = SplashController.init(viewController: vc!, pluginCall: call, delegate: self, setting: setting!, option: option!)
        //加载广告
        splashCtlr.load()
        //记录广告位以便随后销毁
        self.adspotList[call.callbackId] = splashCtlr as AdControllerProtocol;
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    private func banner(adType: String, adTag: String, call: CAPPluginCall) {
        //获取viewController
        let vc = self.bridge?.viewController
        //将配置转换成Controller需要的格式
        let setting = ModelConverter().convertSetting(fromConfig: self.config!, adspotTag: adTag)
        let option = ModelConverter().convertOption(fromConfig: self.config!, adspotTag: adTag)
        //错误检查
        if(setting == nil) { call.reject("Invalid name.", "INVALID_NAME"); return }
        if(vc == nil) { call.reject("Not found view controller.", "NOT_FOUND_VIEW_CONTROLLER"); return }
        //初始化广告控制器
        let bannerCtlr = BannerController.init(viewController: vc!, pluginCall: call, delegate: self, setting: setting!, option: option!)
        //加载广告
        bannerCtlr.load()
        //记录广告位以便随后销毁
        self.adspotList[call.callbackId] = bannerCtlr as AdControllerProtocol;
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    private func interstitial(adType: String, adTag: String, call: CAPPluginCall) {
        //获取viewController
        let vc = self.bridge?.viewController
        //将配置转换成Controller需要的格式
        let setting = ModelConverter().convertSetting(fromConfig: self.config!, adspotTag: adTag)
        let option = ModelConverter().convertOption(fromConfig: self.config!, adspotTag: adTag)
        //错误检查
        if(self.config == nil) { call.reject("Not yet init.", "NOT_INIT"); return }
        if(setting == nil) { call.reject("Invalid name.", "INVALID_NAME"); return }
        if(vc == nil) { call.reject("Not found view controller.", "NOT_FOUND_VIEW_CONTROLLER"); return }
        //初始化广告控制器
        let interstitialCtlr = InterstitialController.init(viewController: vc!, pluginCall: call, delegate: self, setting: setting!, option: option!)
        //加载广告
        interstitialCtlr.load()
        //记录广告位以便随后销毁
        self.adspotList[call.callbackId] = interstitialCtlr as AdControllerProtocol;
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    private func reward(adType: String, adTag: String, call: CAPPluginCall) {
        //获取viewController
        let vc = self.bridge?.viewController
        //将配置转换成Controller需要的格式
        let setting = ModelConverter().convertSetting(fromConfig: self.config!, adspotTag: adTag)
        let option = ModelConverter().convertOption(fromConfig: self.config!, adspotTag: adTag)
        //错误检查
        if(setting == nil) { call.reject("Invalid name.", "INVALID_NAME"); return }
        if(vc == nil) { call.reject("Not found view controller.", "NOT_FOUND_VIEW_CONTROLLER"); return }
        //初始化广告控制器
        let rewardVideoCtlr = RewardVideoController.init(viewController: vc!, pluginCall: call, delegate: self, setting: setting!, option: option!)
        //加载广告
        rewardVideoCtlr.load()
        //记录广告位以便随后销毁
        self.adspotList[call.callbackId] = rewardVideoCtlr as AdControllerProtocol;
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    private func fullscreen(adType: String, adTag: String, call: CAPPluginCall) {
        //获取viewController
        let vc = self.bridge?.viewController
        //将配置转换成Controller需要的格式
        let setting = ModelConverter().convertSetting(fromConfig: self.config!, adspotTag: adTag)
        let option = ModelConverter().convertOption(fromConfig: self.config!, adspotTag: adTag)
        //错误检查
        if(setting == nil) { call.reject("Invalid name.", "INVALID_NAME"); return }
        if(vc == nil) { call.reject("Not found view controller.", "NOT_FOUND_VIEW_CONTROLLER"); return }
        //初始化广告控制器
        let fullScreenVideoCtlr = FullScreenVideoController.init(viewController: vc!, pluginCall: call, delegate: self, setting: setting!, option: option!)
        //加载广告
        fullScreenVideoCtlr.load()
        //记录广告位以便随后销毁
        self.adspotList[call.callbackId] = fullScreenVideoCtlr as AdControllerProtocol;
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    
    // AdCallbackProtocol implementation ===============================
    @objc public func notify(_ event: String, call: CAPPluginCall, error: (any Error)?) {
        // 删除广告列表中的广告
        if(["end", "fail"].contains(event)) { self.adspotList.removeValue(forKey: call.callbackId!) }
        //通知监听
        let eventModel = EventModel.easyAd_model(withJSON: [ "event": event, "adType": call.getString("type")!, "adTag": call.getString("tag")!, "callId": call.callbackId!, "error": error as Any ])
        self.notifyListeners(event, data: eventModel?.easyAd_modelToJSONObject() as? Dictionary)
    }
    
}
