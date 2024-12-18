import Foundation
import Capacitor

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
        CAPPluginMethod(name: "splash", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "banner", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "interstitial", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "rewardVideo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "fullVideo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "destroy", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = EasyAds()
    
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
    
    @objc func splash(_ call: CAPPluginCall) {
        //获取参数
        let name = call.getString("name")
        //获取viewController
        let vc = self.bridge?.viewController
        //将配置转换成Controller需要的格式
        let setting = ModelConverter().convertSetting(fromConfig: self.config!, adspotTag: name!)
        //错误检查
        if(self.config == nil) { call.reject("Not yet init.", "NOT_INIT") }
        if(name == nil) { call.reject("Invalid param.", "INVALID_PARAM") }
        if(setting == nil) { call.reject("Invalid name.", "INVALID_NAME") }
        if(vc == nil) { call.reject("Not found view controller.", "NOT_FOUND_VIEW_CONTROLLER") }
        //初始化广告控制器
        let splashCtlr = SplashController.init(viewController: vc!, setting: setting!, pluginCall: call, delegate: self)
        //加载广告
        splashCtlr.load()
        //记录广告位以便随后销毁
        adspotList[call.callbackId] = splashCtlr as AdControllerProtocol;
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    @objc func banner(_ call: CAPPluginCall) {
        //获取参数
        let name = call.getString("name")
        //获取viewController
        let vc = self.bridge?.viewController
        //将配置转换成Controller需要的格式
        let setting = ModelConverter().convertSetting(fromConfig: self.config!, adspotTag: name!)
        //错误检查
        if(self.config == nil) { call.reject("Not yet init.", "NOT_INIT") }
        if(name == nil) { call.reject("Invalid param.", "INVALID_PARAM") }
        if(setting == nil) { call.reject("Invalid name.", "INVALID_NAME") }
        if(vc == nil) { call.reject("Not found view controller.", "NOT_FOUND_VIEW_CONTROLLER") }
        //初始化广告控制器
        let bannerCtlr = BannerController.init(viewController: vc!, setting: setting!, pluginCall: call, delegate: self)
        //加载广告
        bannerCtlr.load()
        //记录广告位以便随后销毁
        adspotList[call.callbackId] = bannerCtlr as AdControllerProtocol;
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    @objc func interstitial(_ call: CAPPluginCall) {
        //获取参数
        let name = call.getString("name")
        //获取viewController
        let vc = self.bridge?.viewController
        //将配置转换成Controller需要的格式
        let setting = ModelConverter().convertSetting(fromConfig: self.config!, adspotTag: name!)
        //错误检查
        if(self.config == nil) { call.reject("Not yet init.", "NOT_INIT") }
        if(name == nil) { call.reject("Invalid param.", "INVALID_PARAM") }
        if(setting == nil) { call.reject("Invalid name.", "INVALID_NAME") }
        if(vc == nil) { call.reject("Not found view controller.", "NOT_FOUND_VIEW_CONTROLLER") }
        //初始化广告控制器
        let interstitialCtlr = InterstitialController.init(viewController: vc!, setting: setting!, pluginCall: call, delegate: self)
        //加载广告
        interstitialCtlr.load()
        //记录广告位以便随后销毁
        adspotList[call.callbackId] = interstitialCtlr as AdControllerProtocol;
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    @objc func rewardVideo(_ call: CAPPluginCall) {
        //获取参数
        let name = call.getString("name")
        //获取viewController
        let vc = self.bridge?.viewController
        //将配置转换成Controller需要的格式
        let setting = ModelConverter().convertSetting(fromConfig: self.config!, adspotTag: name!)
        //错误检查
        if(self.config == nil) { call.reject("Not yet init.", "NOT_INIT") }
        if(name == nil) { call.reject("Invalid param.", "INVALID_PARAM") }
        if(setting == nil) { call.reject("Invalid name.", "INVALID_NAME") }
        if(vc == nil) { call.reject("Not found view controller.", "NOT_FOUND_VIEW_CONTROLLER") }
        //初始化广告控制器
        let rewardVideoCtlr = RewardVideoController.init(viewController: vc!, setting: setting!, pluginCall: call, delegate: self)
        //加载广告
        rewardVideoCtlr.load()
        //记录广告位以便随后销毁
        adspotList[call.callbackId] = rewardVideoCtlr as AdControllerProtocol;
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    @objc func fullScreenVideo(_ call: CAPPluginCall) {
        //获取参数
        let name = call.getString("name")
        //获取viewController
        let vc = self.bridge?.viewController
        //将配置转换成Controller需要的格式
        let setting = ModelConverter().convertSetting(fromConfig: self.config!, adspotTag: name!)
        //错误检查
        if(self.config == nil) { call.reject("Not yet init.", "NOT_INIT") }
        if(name == nil) { call.reject("Invalid param.", "INVALID_PARAM") }
        if(setting == nil) { call.reject("Invalid name.", "INVALID_NAME") }
        if(vc == nil) { call.reject("Not found view controller.", "NOT_FOUND_VIEW_CONTROLLER") }
        //初始化广告控制器
        let fullScreenVideoCtlr = FullScreenVideoController.init(viewController: vc!, setting: setting!, pluginCall: call, delegate: self)
        //加载广告
        fullScreenVideoCtlr.load()
        //记录广告位以便随后销毁
        adspotList[call.callbackId] = fullScreenVideoCtlr as AdControllerProtocol;
        //返回结果
        call.resolve(["callId": call.callbackId!])
    }
    
    @objc func destroy(_ call: CAPPluginCall) {
        let callId = call.getString("callId") ?? ""
        adspotList[callId]?.destroy()
        call.resolve(["callId": callId])
    }
    
    
    // AdCallbackProtocol implementation ===============================
    @objc public func notify(_ callId: String, event: String, data: [String : Any]?) {
        self.notifyListeners(event, data: data)
    }
    
}
