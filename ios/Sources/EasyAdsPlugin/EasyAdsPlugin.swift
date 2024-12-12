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
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "init", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "splash", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "banner", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "interstitial", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "rewardVideo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "fullVideo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "destroy", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = EasyAds()
    private var adspotList: [String:any AdControllerProtocol] = [:]

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    @objc func `init`(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    @objc func splash(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    @objc func banner(_ call: CAPPluginCall) {
        //生成随机callId
        let callId = UUID.init().uuidString
        //获取参数
        let name = call.getString("name")
        //获取cap的viewController
        // guard let appDelegate = UIApplication.shared.delegate as? AppDelegate else { return }
        // DispatchQueue.main.async {
        //    let window = rootViewController?.view.window
        // }
        //初始化广告控制器
        let bannerCtlr = BannerController.init(viewController: self.bridge?.viewController, pluginCall: call, delegate: self)
        //加载广告
        bannerCtlr.load()
        //记录广告位以便随后销毁
        adspotList[callId] = bannerCtlr as AdControllerProtocol;
        
        //返回结果
        call.resolve(["callId": callId])
        
    }
    
    @objc func interstitial(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    @objc func rewardVideo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    @objc func fullVideo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    @objc func destroy(_ call: CAPPluginCall) {
        let callId = call.getString("callId") ?? ""
        adspotList[callId]?.destroy()
        call.resolve(["callId": callId])
        
    }
    
    
    // AdCallbackProtocol implementation ===============================
    @objc public func notify(_ event: String, data: [String : Any]?) {
        self.notifyListeners(event, data: data)
    }
    
}
