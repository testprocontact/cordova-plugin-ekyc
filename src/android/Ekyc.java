package com.fci.plugin;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.fpt.fci.ekycfull.EkycResult;
import com.fpt.fci.ekycfull.domain.BaseConfig;
import com.fpt.fci.ekycfull.presentation.view.EkycActivity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.UUID;

public class Ekyc extends CordovaPlugin {
    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) {

        if (action.equals("startSDK")) {
            this.callbackContext = callbackContext;
            cordova.getActivity().runOnUiThread(() -> {
                try {
                    startSDK(data);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
            return true;

        } else {
            return false;
        }
    }

    private void startSDK (JSONArray data) throws JSONException {
        String apiKey = data.getString(0);
        String uuid = data.getString(1);
        int docType = data.getInt(2);
        int env = data.getInt(3);
        String front = null;
        String back = null;
        if (data.length() == 6) {
            front = data.getString(4);
            back = data.getString(5);
        }
        System.out.println("startSDK "+front+" back "+back);
        Intent intent = new Intent(cordova.getContext(), EkycActivity.class);
        intent.putExtra(BaseConfig.ParamType.API_KEY.name(), apiKey);
        intent.putExtra(BaseConfig.ParamType.DOCUMENT_TYPE.name(), docType);
        intent.putExtra(BaseConfig.ParamType.LANG.name(), "vi");
        intent.putExtra(BaseConfig.ParamType.UUID.name(), uuid.isEmpty() ? UUID.randomUUID().toString() : uuid);
        intent.putExtra(BaseConfig.ParamType.ENVIRONMENT.name(), env);
        if (!TextUtils.isEmpty(front) && !TextUtils.isEmpty(back)) {
//            intent.putExtra(BaseConfig.ParamType.FRONT_PATH.name(), front);
//            intent.putExtra(BaseConfig.ParamType.BACK_PATH.name(), back);
        }
        cordova.startActivityForResult(this, intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_OK) {
            String front = intent.getStringExtra(BaseConfig.ReturnParamType.FRONT_FILE_PATH.name());
            String back = intent.getStringExtra(BaseConfig.ReturnParamType.BACK_FILE_PATH.name());
            String selfie = intent.getStringExtra(BaseConfig.ReturnParamType.SELFIE_FILE_PATH.name());
            String video = intent.getStringExtra(BaseConfig.ReturnParamType.VIDEO_FILE_PATH.name());
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(EkycResult.INSTANCE.getOcrData());
            String liveElement = gson.toJson(EkycResult.INSTANCE.getLiveData());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            jsonObject.addProperty("front", front);
            jsonObject.addProperty("back", back);
            jsonObject.addProperty("selfie", selfie);
            jsonObject.addProperty("video", video);
            jsonObject.addProperty("live", liveElement);
            if (EkycResult.INSTANCE.getNfcData() != null) {
                String nfcElement = gson.toJson(EkycResult.INSTANCE.getNfcData());
                jsonObject.addProperty("nfc", nfcElement);
            }
            if (EkycResult.INSTANCE.getSessionData() != null) {
                String nfcElement = gson.toJson(EkycResult.INSTANCE.getSessionData());
                jsonObject.addProperty("session", nfcElement);
            }
            this.callbackContext.success(jsonObject.toString());
        }
    }

}

