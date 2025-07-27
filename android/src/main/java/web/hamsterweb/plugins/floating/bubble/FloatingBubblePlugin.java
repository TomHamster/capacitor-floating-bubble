package web.hamsterweb.plugins.floating.bubble;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


@CapacitorPlugin(name = "FloatingBubble")
public class FloatingBubblePlugin extends Plugin {
    private String imageBase64;

    private static FloatingBubblePlugin instance;

    public FloatingBubblePlugin() {
        instance = this; // ustaw singleton
    }

    public static void sendEventToCapacitor(String jsonData) {
        if (instance != null) {
            JSObject data = new JSObject();
            try {
                JSONObject obj = new JSONObject(jsonData);
                Iterator<String> keys = obj.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    data.put(key, obj.get(key));
                }
            } catch (JSONException e) {
                data.put("message", jsonData); // fallback
            }

            instance.notifyListeners("onBubbleMessage", data);
        } else {
            Log.w("FloatingBubblePlugin", "Instance is null, can't send event.");
        }
    }

    @Override
    public void load() {
        imageBase64 = getConfig().getString("imageBase64", null);

        if (imageBase64 != null) {
            System.out.println("FloatingBubble loaded imageBase64: " + imageBase64);
        } else {
            System.out.println("FloatingBubble: imageBase64 not set in config");
        }
    }
    @PluginMethod
    public void showBubble(PluginCall call) {
        Context context = getContext();
        SharedPreferences prefs = context.getSharedPreferences("plugin_prefs", Context.MODE_PRIVATE);
        prefs.edit().putString("imageBase64", imageBase64).apply();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getContext().getPackageName()));

                getActivity().startActivityForResult(intent, 1234);
                call.reject("Permission required: ACTION_MANAGE_OVERLAY_PERMISSION");
                return;
            }
        }

        Intent intent = new Intent(context, FloatingBubbleService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        call.resolve();
    }


    @PluginMethod
    public void closeBubble(PluginCall call) {
        Context context = getContext();

        Intent intent = new Intent(context, FloatingBubbleService.class);
        intent.setAction(FloatingBubbleService.ACTION_REMOVE_BUBBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        call.resolve();

    }

    @PluginMethod
    public void sendToBubble(PluginCall call) {
        Context context = getContext();

        Intent intent = new Intent(context, FloatingBubbleService.class);
        String message = call.getString("message", "");


        Log.d("tag", message);
        intent.putExtra("message", message);
        intent.setAction(FloatingBubbleService.ACTION_SEND_TO_WEBVIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        call.resolve();

    }
}
