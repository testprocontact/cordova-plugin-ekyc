package com.fci.plugin;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.fpt.fci.ekycfull.EkycResult;
import com.fpt.fci.ekycfull.EkycSDK;
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

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) {

        if (action.equals("startSDK")) {
            cordova.getActivity().runOnUiThread(() -> {
                try {
                    startSDK(callbackContext, data);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
            return true;

        } else {
            return false;
        }
    }

    private void startSDK (CallbackContext callbackContext, JSONArray data) throws JSONException {
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
        EkycSDK.Builder builder = new EkycSDK.Builder(cordova.getContext())
            .setUUID(uuid.isEmpty() ? UUID.randomUUID().toString() : uuid)
            .setApiKey(apiKey)
            .setEnvironment(env)
            .setLang("vi")
            .setTheme(true)
			.setDocType(docType)
			.addListener(new EkycSDK.CompleteListener() {
                @Override
                public void onSuccess(@Nullable String s) {
                    callbackContext.success(s);
                }

                @Override
                public void onFailed(@Nullable String s, @Nullable String s1) {
                    callbackContext.error(s);
                }

                @Override
                public void onTracking(@Nullable String s) {

                }
            });
		if (!TextUtils.isEmpty(front) && !front.equals("") && !front.equals("null")) {
            builder.setFrontPath(front);
            builder.setBackPath(back);
        }
		builder.build();
    }

}

