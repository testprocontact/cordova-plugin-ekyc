#
# Be sure to run `pod lib lint eKYC.podspec' to ensure this is a
# valid spec before submitting.
#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see https://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
  s.name             = 'eKYC'
  s.version          = "3.7.4"
  s.summary          = "eKYC SDK."
  s.description      = "Some sort of long description of the pod"
  s.homepage         = "http://fpt.ai/"
  s.license          = { :type => "MIT", :file => "LICENSE" }
  s.author           = { "tuyennv39" => "tuyennv39@fpt.com" }
  s.platform         = :ios, "10.3"
  s.ios.deployment_target = '10.3'
  s.source           = { :path => "./eKYC_xc.zip" }
 
  s.vendored_frameworks = "eKYC.xcframework"
  s.resource = "eKYC.bundle"

  s.dependency 'OpenSSL-Universal', '1.1.2200'
  s.dependency 'lottie-ios', '3.5.0'
  s.dependency 'TensorFlowLiteSwift'
end
