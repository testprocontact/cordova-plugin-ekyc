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
* Xcode 14.0 and above
* Cocoapod v1.11.3
* A physical iOS device
* An Apple developer account

### Integrate framework
* Please contact FCI to obtain the SDK and other necessary information.

* Install

To integrate the eKYC into your Xcode project using CocoaPods, specify it in your Podfile:

```sh
pod 'eKYC', :path => "./"
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
```

let config = FEKYCConfig(apiKey: "",
                                    sessionId:"",
                                      isFullFlow: true,
                                      clientUUID: UUID().uuidString,
                                      fullName: "",
                                      ocrTypes: NSArray(objects: FEKYCOcrType.idCard.rawValue,
                                                       FEKYCOcrType.driveLicense.rawValue,
                                                       FEKYCOcrType.passport.rawValue),
                                      environment: .product,
                                      livenessType: 1,
                                      onlyDoccument: false,
                                      isScanQrCode : false,
                                      isShowResult: false,
                                      themeColor: nil,
                                      nfcHasLiveness: false,
                                      language:"",
                                      countryCode: "vn")
        
FEKYC.startFPTEKYCFlow(withConfig: config, from: self) { result in
    print(result)
}
```

In which:
- apiKey: is the key provided by FCI to a third party. The process of processing images and comparing information in documents and selfies will be done on FCI's server and provided in the form of public APIs. Only when owning a valid api key can the sdk be able to connect to the backend. Please contact FPT Smart Cloud for provision.
- uuid: is the unique string which should be provided from client.
- flow: is the name of version such as:
    - 1: should not include NFC function
    - 2||3: compare information that was read from NFC flow and from ocr. If the information is correct, the user can go through the next step. And vise versa.
    - 4||5: verify card information that was read from NFC flow from the backend side.
    you can login portal for set flow.
- orcTypes: An array of document types that third parties want to provide to end users. Where .idCard is the citizen identification, excluding the function of reading information from chip card, .nfc is the citizen identification with the function of reading information from chip card, .driveLicense is for driving license, and finally .passport is for users who want to do eKYC with passport.
- environment: is enviroment you want check (dev, stagging, product)
- onlyDoccument : is a boolean variable, if true you can using OCR funtion and if false  you can all funtion in our SDK
- isScanQrCode: is a boolean variable, if true you can scan qrcode on idcard with your device when ocr not scan qrcode, and false when you dont want scan qrcode on idcard
- 
- livenessType: is a way to compare the information in the document and the person performing the eKYC is the same or not. FCI provides 3 ways of confirmation, one is with a selfie photo, the other is with a close-up video and the third is providing liveness video with different face angles.
- The isShowResult is a boolean variable, if true, the details of the identity card will be displayed if the process of reading and retrieving information from the card is successful. The deafault value of this variable is 'true', means always show the result if the card is valid after verifying.
- The isReturnPhoto is a boolean variable as well. If true, the SDK will return all photos during the eKYC process into the results.
- limit: is a number which limits the times the user can be verified by the liveness api for a card reading turn. If this number is exceeded, the user can only go back to the first step. The default number is 5.
- language : is your langue you want in sdk (vi, en)
- countryCode : is your paper in country your want kyc
- mainStoryboard: is your name file storyboard you want custom 
- customInfo: is file your can download on portal. and copy file in your project (use: EKYCConfigCustom().getCustomUI())
- The result, which will be returned in the closure, is the object contains personal information.


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


