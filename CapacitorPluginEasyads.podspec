require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name = 'CapacitorPluginEasyads'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.source_files = 'ios/Sources/**/*.{swift,h,m,c,cc,mm,cpp}'
  s.ios.deployment_target  = '13.0'
  s.dependency 'Capacitor'
  s.swift_version = '5.1'

  s.dependency "Ads-CN", "6.5.0.9"
  # # s.dependency "GMBaiduAdapter-Beta", "5.370.1"
  s.dependency "BaiduMobAdSDK", "5.370"
  # # s.dependency "GMGdtAdapter-Beta", "4.15.10.0"
  s.dependency "GDTMobSDK", "4.15.10"
  # # s.dependency "GMKsAdapter-Beta", "3.3.69.1"
  s.dependency "KSAdSDK", "3.3.69"

  s.dependency "Masonry", "1.1.0"

  s.user_target_xcconfig = {'OTHER_LDFLAGS' => ['-ObjC']}
    
  # bitcode
  # s.pod_target_xcconfig = { 'ENABLE_BITCODE' => 'NO', 'VALID_ARCHS' => valid_archs.join(' '), 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'arm64' }
  # s.user_target_xcconfig = { 'ENABLE_BITCODE' => 'NO', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'arm64' }
  s.pod_target_xcconfig = { 'ENABLE_BITCODE' => 'NO'}
  s.user_target_xcconfig = { 'ENABLE_BITCODE' => 'NO'}

  s.default_subspec = 'Core'
  
  s.requires_arc = true
  s.static_framework = true
  
  s.xcconfig = { 'VALID_ARCHS' => 'i386 armv7 x86_64 arm64' }

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
      gdt.libraries     = 'xml2', 'z'
  end
      
  s.subspec 'KS' do |ks|
      ks.dependency 'ios/Sources/EasyAdsSDK/Core'
      ks.dependency 'ios/Sources/EasyAdsSDK/Adspot'
      ks.dependency 'KSAdSDK'
      ks.source_files = 'ios/Sources/EasyAdsSDK/Adapter/KS/**/*.{h,m}'
      ks.frameworks = ["Foundation", "UIKit", "MobileCoreServices", "CoreGraphics", "Security", "SystemConfiguration", "CoreTelephony", "AdSupport", "CoreData", "StoreKit", "AVFoundation", "MediaPlayer", "CoreMedia", "WebKit", "Accelerate", "CoreLocation", "AVKit", "MessageUI", "QuickLook", "AudioToolBox", "AddressBook"]
      ks.libraries =  ["z", "resolv.9", "sqlite3", "c++", "c++abi"]
  end
  
  s.subspec 'BD' do |bd|
      bd.dependency 'ios/Sources/EasyAdsSDK/Core'
      bd.dependency 'ios/Sources/EasyAdsSDK/Adspot'
      bd.dependency 'BaiduMobAdSDK'
      bd.source_files =  'ios/Sources/EasyAdsSDK/Adapter/BD/**/*.{h,m}'
      bd.frameworks = 'CoreLocation', 'SystemConfiguration', 'CoreGraphics', 'CoreMotion', 'CoreTelephony', 'AdSupport', 'SystemConfiguration', 'QuartzCore', 'WebKit', 'MessageUI','SafariServices','AVFoundation','EventKit','QuartzCore','CoreMedia','StoreKit'
      bd.libraries     = 'c++'
      bd.weak_frameworks = "WebKit"
      valid_archs = ['armv7', 'armv7s', 'x86_64', 'arm64'] 
  end

end
