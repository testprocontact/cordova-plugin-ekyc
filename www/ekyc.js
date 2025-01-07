/*global cordova, module*/

module.exports = {
	startSDK: function (apiKey, uuid, docType, env, front, back, lang, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Ekyc", "startSDK", [apiKey, uuid, docType, env, front, back, lang]);
    }
};
