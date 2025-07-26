package web.hamsterweb.plugins.floating.bubble;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
    Context context;

    public WebAppInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void sendEvent(String data) {
        Log.d("WebAppInterface", "JS sent event: " + data);
    }
}

