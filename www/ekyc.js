/*global cordova, module*/

module.exports = {
    startSDK: function (apiKey, uuid, docType, env, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Ekyc", "startSDK", [apiKey, uuid, docType, env]);
    }
};
