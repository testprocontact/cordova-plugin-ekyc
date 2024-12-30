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
        if (data.length() >= 6) {
            front = data.getString(4);
            back = data.getString(5);
        }
        EkycSDK.Builder builder = new EkycSDK.Builder(requireActivity())
            .setUUID(uuid.isEmpty() ? UUID.randomUUID().toString() : uuid)
            .setApiKey(apiKey)
            .setEnvironment(env)
            .setLang("vi")
            .setTheme(true)
			.setDocType(docType)
			.addListener(object : EkycSDK.CompleteListener {
                override fun onSuccess(results: String?) {
					this.callbackContext.success(results);
                }
                override fun onFailed(results: String?, errorMessage: String?) {
                    println("result OCR failed result $results errMessage = $errorMessage")
                }
                override fun onTracking(message: String?) {

                }
		if (!TextUtils.isEmpty(front) && !front.equals("null")) {
            builder.setFrontPath(front)
            builder.setBackPath(back)
        }
		builder.build()
        
    }    

}

