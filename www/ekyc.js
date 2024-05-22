/*global cordova, module*/

module.exports = {
    startSDK: function (apiKey, uuid, docType, env, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Ekyc", "startSDK", [apiKey, uuid, docType, env]);
    }
	startSDK: function (apiKey, uuid, docType, env, front, back, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Ekyc", "startSDK", [apiKey, uuid, docType, env, front, back]);
    }
};
