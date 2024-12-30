# FEKYC

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#how_to_build_framework">How to build framework</a></li>
		<li><a href="#integrate_framework">Integrate framework</a></li>
      </ul>
    </li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

This project is developed by FCI Team. This is a project to build a framework that provides the function of electronic know your customer (eKYC) on
four kinds of Vietnam identity documents as identity card (with and without chip), driving lisence and passport.

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites
* Xcode 13.3 and above
* Cocoapod v1.11.3
* A physical iOS device
* An Apple developer account

### Integrate framework
* Please contact FCI to obtain the SDK and other necessary information.

* Install

To integrate the eKYC into your Xcode project using CocoaPods, specify it in your Podfile:

```sh
pod 'eKYC', :path => "eKYC/"
```
Replace the temporary path with the path that you place unzipped eKYC framework.
> Relative paths can be used. In case you put the framework in the same directory as the Podfile, the above declaration is correct.

2. Install the pod into your project by typing in terminal:
```sh
pod install
```

* Add capability to your Xcode target: Near Field Communication Tag Reading
IDCardReader requires following keys in Info.plist:

```
<key>NSCameraUsageDescription</key>
<string>Please allow the app uses camera for detecting information from identity documents</string>
<key>NFCReaderUsageDescription</key>
<string>NFC tag to read NDEF messages into the application</string>

<key>com.apple.developer.nfc.readersession.iso7816.select-identifiers</key>
<array>
	<string>A0000002471001</string>
</array>
```



* Usage

full eKYC:

```
            let config = FEKYCConfig(apiKey: "",
                                                  sessionId:"",
                                                  isFullFlow: true,
                                                  clientUUID: UUID().uuidString,
                                                  ocrTypes: NSArray(objects: FEKYCOcrType.idCard.rawValue),
                                                  environment: .staging,
                                      language: "vi",customInfo: EKYCConfigCustom().getCustomUI(),setBaseUrl: "", headers: ["key":"value"])

                                FEKYC.startFPTEKYCFlow(withConfig: config5, from: v) { result in
                print(result)
            } onFail: { resultFail, errorString in
                print(resultFail)
            } onTracking: { tracking in
                print(tracking)
            }
```

only NFC: 

        var documentData :[[String:Any]] = []
        var idNum : [String:Any] = [:]
        idNum["key"] = "ID"
        idNum["value"] = ""
        
        var dob : [String:Any] = [:]
        dob["key"] = "Date of birth"
        dob["value"] = ""
        
        var doe : [String:Any] = [:]
        doe["key"] = "Expired Date"
        doe["value"] = ""
        documentData.append(idNum)
        documentData.append(dob)
        documentData.append(doe)
        
        let config = FEKYCConfig(apiKey: "",
                                 ocrTypes: .nfc,
                                 environment: .dev,
                                 language: "vi",
                                 documentData:documentData
                                )
            FEKYC.startFPTEKYCFlow(withConfig: config5, from: v) { result in
                print(result)
            } onFail: { resultFail, errorString in
                print(resultFail)
            } onTracking: { tracking in
                print(tracking)
            }

only OCR:

            let config = FEKYCConfig(apiKey: "",
                                                  sessionId:"",
                                                  isFullFlow: true,
                                                  clientUUID: UUID().uuidString,
                                                  ocrTypes: NSArray(objects: FEKYCOcrType.idCard.rawValue),
                                                  environment: .dev,
                                                  onlyDoccument: true,
                                                  language: "vi")

            FEKYC.startFPTEKYCFlow(withConfig: config5, from: v) { result in
                print(result)
            } onFail: { resultFail, errorString in
                print(resultFail)
            } onTracking: { tracking in
                print(tracking)
            }
                                     
only Liveness:

        let config = FEKYCConfig(apiKey: "",
                                 flow: "6",
                                 environment: .dev,
                                 livenessType: 1,
                                 isShowResult:false,
                                 language: "vi")
        
            FEKYC.startFPTEKYCFlow(withConfig: config5, from: v) { result in
                print(result)
            } onFail: { resultFail, errorString in
                print(resultFail)
            } onTracking: { tracking in
                print(tracking)
            }

In which:
- apiKey: is the key provided by FCI to a third party. The process of processing images and comparing information in documents and selfies will be done on FCI's server and provided in the form of public APIs. Only when owning a valid api key can the sdk be able to connect to the backend. Please contact FPT Smart Cloud for provision.
- sessionId:
- orcTypes: An array of document types that third parties want to provide to end users. Where .idCard is the citizen identification, excluding the function of reading information from chip card, .nfc is the citizen identification with the function of reading information from chip card, .driveLicense is for driving license, and finally .passport is for users who want to do eKYC with passport.
- environment: .
- The isShowResult is a boolean variable, if true, the details of the identity card will be displayed if the process of reading and retrieving information from the card is successful. The deafault value of this variable is 'true', means always show the result if the card is valid after verifying.
- The result, which will be returned in the closure, is the object contains personal information.
- customInfo: is the info you download from portal, then copy to your project and config like example.


Note: Because our SDk only support portrait. if your app support landscape then add following code:
 Add this to Appdelegate:    
 
  var orientationLock = UIInterfaceOrientationMask.all
    func application(_ application: UIApplication, supportedInterfaceOrientationsFor window: UIWindow?) -> UIInterfaceOrientationMask {
        return self.orientationLock
    }

Add this from any swift class:
struct AppUtility {
    static func lockOrientation(_ orientation: UIInterfaceOrientationMask) {
        if let delegate = UIApplication.shared.delegate as? AppDelegate {
            delegate.orientationLock = orientation
        }
    }
}

When start SDK you add this: AppUtility.lockOrientation(.portrait) 
and this: AppUtility.lockOrientation(.all) when receive resutl from sdk
For example: 
            AppUtility.lockOrientation(.portrait)
            FEKYC.startFPTEKYCFlow(withConfig: config5, from: self) { result in
                AppUtility.lockOrientation(.all)
                print(result)
            }


<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.


<!-- CONTACT -->
## Contact

[FPT.AI](https://fpt.ai/) - support@fpt.ai

FPT Tower, 10 Pham Van Bach street, Cau Giay District, Hanoi, Vietnam

1900 638 399

Project Link: [https://github.com/](https://github.com/)



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

During development, we consulted from the following sources:
* [AndyQ/NFCPassportReader](https://github.com/AndyQ/NFCPassportReader)







