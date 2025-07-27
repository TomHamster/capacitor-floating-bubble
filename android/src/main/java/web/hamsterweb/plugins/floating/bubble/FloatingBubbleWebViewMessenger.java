package web.hamsterweb.plugins.floating.bubble;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class FloatingBubbleWebViewMessenger {
    Context context;

    public FloatingBubbleWebViewMessenger(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void sendMessage(String data) {
        FloatingBubblePlugin.sendEventToCapacitor(data);
    }

}

