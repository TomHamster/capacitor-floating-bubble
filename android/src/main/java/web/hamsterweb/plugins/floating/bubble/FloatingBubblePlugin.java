package web.hamsterweb.plugins.floating.bubble;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;



@CapacitorPlugin(name = "FloatingBubble")
public class FloatingBubblePlugin extends Plugin {
    private String imageBase64;

    @Override
    public void load() {
        // Odczytaj konfigurację przekazaną z capacitor.config
        imageBase64 = getConfig().getString("imageBase64", null);

        if (imageBase64 != null) {
            // Możesz teraz użyć imageBase64 w pluginie
            System.out.println("FloatingBubble loaded imageBase64: " + imageBase64);
        } else {
            System.out.println("FloatingBubble: imageBase64 not set in config");
        }
    }
    @PluginMethod
    public void showBubble(PluginCall call) {
        Context context = getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getContext().getPackageName()));
                // Otwórz ekran ustawień, aby użytkownik mógł nadać uprawnienie
                getActivity().startActivityForResult(intent, 1234);
                call.reject("Permission required: ACTION_MANAGE_OVERLAY_PERMISSION");
                return;
            }
        }
        // Tutaj implementujesz logikę wyświetlania bąbelka (bubble)
        Intent intent = new Intent(context, FloatingBubbleService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        call.resolve();
    }



}
