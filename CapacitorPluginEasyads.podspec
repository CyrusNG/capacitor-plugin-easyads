require 'json'

target = '13.0'
valid_archs = 'i386 armv7 x86_64 arm64'
package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  # basic settings
  s.name = 'CapacitorPluginEasyads'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.source_files = 'ios/Sources/**/*.{swift,h,m,c,cc,mm,cpp}'
  s.ios.deployment_target  = target
  s.dependency 'Capacitor'
  s.swift_version = '5.1'

  # plugin settings
  s.requires_arc = true
  s.static_framework = true

  # dependencies
  s.dependency 'Ads-CN', '6.5.0.9'
  s.dependency 'Ads-CN/BUAdSDK', '6.5.0.9'
  s.dependency 'Ads-CN/CSJMediation', '6.5.0.9'
  s.dependency 'BaiduMobAdSDK', '5.352'
  s.dependency 'GDTMobSDK', '4.15.10'
  s.dependency 'KSAdSDK', '3.3.69'
  # Masonry is a light-weight layout framework which wraps AutoLayout with a nicer syntax.
  # ref: https://cocoapods.org/pods/Masonry
  s.dependency 'Masonry', '1.1.0'
    
  # GDT only support x86_64 iPhone simulator
  s.pod_target_xcconfig = { 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'arm64' }
  
  # prefix header for Masonry
  s.prefix_header_file = 'ios/Sources/PrefixHeader.pch'
  
  # info plist settings
  s.info_plist = {
    'LSRequiresIPhoneOS' => true,
    'NSAppTransportSecurity' => { 'NSAllowsArbitraryLoads': true },
    'NSUserTrackingUsageDescription' => '请求idfa权限',
    'SKAdNetworkItems' => [ 
      { 'SKAdNetworkIdentifier': 'r3y5dwb26t.skadnetwork' },
      { 'SKAdNetworkIdentifier': '238da6jt44.skadnetwork' },
      { 'SKAdNetworkIdentifier': '22mmun2rn5.skadnetwork' }
    ],
    'UILaunchStoryboardName' => 'LaunchScreen',
    'UIMainStoryboardFile' => 'Main',
    'UIRequiredDeviceCapabilities' => [ 'armv7' ],
    'UISupportedInterfaceOrientations' => [ 'UIInterfaceOrientationPortrait', 'UIInterfaceOrientationLandscapeLeft', 'UIInterfaceOrientationLandscapeRight' ],
    'UISupportedInterfaceOrientations~ipad' => [ 'UIInterfaceOrientationPortrait', 'UIInterfaceOrientationPortraitUpsideDown', 'UIInterfaceOrientationLandscapeLeft', 'UIInterfaceOrientationLandscapeRight' ]
  }

  # subspec settings
  s.default_subspec = 'Core'
  s.subspec 'Core' do |core|
      core.source_files = 'ios/Sources/EasyAdsSDK/Core/**/*.{h,m}'
      core.frameworks = 'UIKit', 'Foundation', 'AdSupport'
  end
  s.subspec 'Adspot' do |adspot|
      adspot.dependency 'ios/Sources/EasyAdsSDK/Core'
      adspot.source_files = 'ios/Sources/EasyAdsSDK/Adspot/**/*.{h,m}'
  end
  s.subspec 'CSJ' do |csj|
      csj.dependency 'ios/Sources/EasyAdsSDK/Core'
      csj.dependency 'ios/Sources/EasyAdsSDK/Adspot'
      csj.dependency 'Ads-CN'
      csj.source_files = 'ios/Sources/EasyAdsSDK/Adapter/CSJ/**/*.{h,m}'
      csj.frameworks = 'UIKit', 'MapKit', 'WebKit', 'MediaPlayer', 'CoreLocation', 'AdSupport', 'CoreMedia', 'AVFoundation', 'CoreTelephony', 'StoreKit', 'SystemConfiguration', 'MobileCoreServices', 'CoreMotion', 'Accelerate','AudioToolbox','JavaScriptCore','Security','CoreImage','AudioToolbox','ImageIO','QuartzCore','CoreGraphics','CoreText'
      csj.libraries = 'c++', 'resolv', 'z', 'sqlite3', 'bz2', 'xml2', 'iconv', 'c++abi'
      # valid_archs = ['armv7', 'i386', 'x86_64', 'arm64']
  end
  s.subspec 'GDT' do |gdt|
      gdt.dependency 'ios/Sources/EasyAdsSDK/Core'
      gdt.dependency 'ios/Sources/EasyAdsSDK/Adspot'
      gdt.dependency 'GDTMobSDK'
      gdt.source_files =  'ios/Sources/EasyAdsSDK/Adapter/GDT/**/*.{h,m}'
      gdt.frameworks = 'AdSupport', 'CoreLocation', 'QuartzCore', 'SystemConfiguration', 'CoreTelephony', 'Security', 'StoreKit', 'AVFoundation', 'WebKit'
      gdt.libraries = 'xml2', 'z'
  end
  s.subspec 'KS' do |ks|
      ks.dependency 'ios/Sources/EasyAdsSDK/Core'
      ks.dependency 'ios/Sources/EasyAdsSDK/Adspot'
      ks.dependency 'KSAdSDK'
      ks.source_files = 'ios/Sources/EasyAdsSDK/Adapter/KS/**/*.{h,m}'
      ks.frameworks = ["Foundation", "UIKit", "MobileCoreServices", "CoreGraphics", "Security", "SystemConfiguration", "CoreTelephony", "AdSupport", "CoreData", "StoreKit", "AVFoundation", "MediaPlayer", "CoreMedia", "WebKit", "Accelerate", "CoreLocation", "AVKit", "MessageUI", "QuickLook", "AudioToolBox", "AddressBook"]
      ks.libraries = ["z", "resolv.9", "sqlite3", "c++", "c++abi"]
  end
  s.subspec 'BD' do |bd|
      bd.dependency 'ios/Sources/EasyAdsSDK/Core'
      bd.dependency 'ios/Sources/EasyAdsSDK/Adspot'
      bd.dependency 'BaiduMobAdSDK'
      bd.source_files = 'ios/Sources/EasyAdsSDK/Adapter/BD/**/*.{h,m}'
      bd.frameworks = 'CoreLocation', 'SystemConfiguration', 'CoreGraphics', 'CoreMotion', 'CoreTelephony', 'AdSupport', 'SystemConfiguration', 'QuartzCore', 'WebKit', 'MessageUI','SafariServices','AVFoundation','EventKit','QuartzCore','CoreMedia','StoreKit'
      bd.libraries = 'c++'
      bd.weak_frameworks = "WebKit"
      #valid_archs = ['armv7', 'armv7s', 'x86_64', 'arm64']
  end

end
