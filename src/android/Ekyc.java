package com.fci.plugin;

import android.content.Intent;
import com.fpt.fci.ekycfull.domain.BaseConfig;
import com.fpt.fci.ekycfull.presentation.view.EkycActivity;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.UUID;

public class Ekyc extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) {

        if (action.equals("startSDK")) {
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
        Intent intent = new Intent(cordova.getContext(), EkycActivity.class);
        intent.putExtra(BaseConfig.ParamType.API_KEY.name(), apiKey);
        intent.putExtra(BaseConfig.ParamType.DOCUMENT_TYPE.name(), docType);
        intent.putExtra(BaseConfig.ParamType.UUID.name(), uuid.isEmpty() ? UUID.randomUUID().toString() : uuid);
        intent.putExtra(BaseConfig.ParamType.ENVIRONMENT.name(), env);
        cordova.getActivity().startActivity(intent);
    }
}
