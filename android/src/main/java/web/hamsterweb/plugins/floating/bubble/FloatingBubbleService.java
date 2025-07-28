package web.hamsterweb.plugins.floating.bubble;

import static web.hamsterweb.plugins.floating.bubble.BubbleHelper.getCircularBitmap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.torrydo.floatingbubbleview.CloseBubbleBehavior;
import com.torrydo.floatingbubbleview.FloatingBubbleListener;
import com.torrydo.floatingbubbleview.helper.ViewHelper;
import com.torrydo.floatingbubbleview.service.expandable.BubbleBuilder;
import com.torrydo.floatingbubbleview.service.expandable.ExpandableBubbleService;
import com.torrydo.floatingbubbleview.service.expandable.ExpandedBubbleBuilder;


public class FloatingBubbleService extends ExpandableBubbleService {
    public static final String ACTION_REMOVE_BUBBLE = "REMOVE_BUBBLE";
    public static final String ACTION_SEND_TO_WEBVIEW = "SEND_TO_WEBVIEW";

    private String imageBase64 = null;
    private Bitmap circularBitmap = null;
    private WebView webView = null;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && ACTION_REMOVE_BUBBLE.equals(intent.getAction())) {
            assert getBubble() != null;
            getBubble().remove();
        } else if (intent != null && ACTION_SEND_TO_WEBVIEW.equals(intent.getAction())){
            String message = intent.getStringExtra("message");

            if (message == null) {
                message = "";
            }

            String safeMessage = message.replace("\"", "\\\"");

            String js = "javascript:window.dispatchEvent(new CustomEvent('onCapacitorMessage', { detail: { message: \"" + safeMessage + "\" } }))";

            if (webView != null) {
                webView.post(() -> {
                    webView.evaluateJavascript(js, null);
                });
            }
        }
        else {
            minimize();
        }

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        minimize();
    }

    @Nullable
    @Override
    public BubbleBuilder configBubble() {
        SharedPreferences prefs = getSharedPreferences("plugin_prefs", MODE_PRIVATE);
        String imageBase64 = prefs.getString("imageBase64", null);

        if (imageBase64 == null) {
            imageBase64 = DefaultVariables.DEFAULT_BUBBLE_IMAGE_BASE_64;
        }

        byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
        circularBitmap = getCircularBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        View bubbleView = ViewHelper.fromBitmap(this, circularBitmap, 60, 60);

        bubbleView.setOnClickListener(view -> expand());

        return new BubbleBuilder(this)
                .bubbleView(bubbleView)
                .bubbleStyle(com.torrydo.floatingbubbleview.R.style.default_bubble_style)
                .bubbleDraggable(true)
                .forceDragging(true)
                .closeBubbleView(ViewHelper.fromDrawable(this, com.torrydo.floatingbubbleview.R.drawable.ic_close_bubble))
                .closeBubbleStyle(com.torrydo.floatingbubbleview.R.style.default_close_bubble_style)
                .distanceToClose(100)
                .triggerClickablePerimeterPx(5f)
                .closeBehavior(CloseBubbleBehavior.FIXED_CLOSE_BUBBLE)
                .startLocation(100, 100)
                .enableAnimateToEdge(true)
                .bottomBackground(true)
                .addFloatingBubbleListener(new FloatingBubbleListener() {
                    @Override
                    public void onFingerDown(float x, float y) {
                    }

                    @Override
                    public void onFingerUp(float x, float y) {
                    }

                    @Override
                    public void onFingerMove(float x, float y) {
                    }
                });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public ExpandedBubbleBuilder configExpandedBubble() {

        View expandedView = LayoutInflater.from(this).inflate(R.layout.expand_view, null);
        ImageButton btn = expandedView.findViewById(R.id.btn);
//        LinearLayout layout = expandedView.findViewById(R.id.layout);
        btn.setImageBitmap(circularBitmap);

        View bubbleView = ViewHelper.fromBitmap(this, circularBitmap, 60, 60);
//        layout.addView(bubbleView);

        webView = expandedView.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new FloatingBubbleWebViewMessenger(this), "FloatingBubbleWebViewMessenger");



        webView.loadUrl("file:///android_asset/public/bubbleWebView.html");

        btn.setOnClickListener(v -> {
            minimize();
        });

        return new ExpandedBubbleBuilder(this)
                .expandedView(expandedView)
                .startLocation(0, 0)
                .draggable(true)
                .style(com.torrydo.floatingbubbleview.R.style.default_bubble_style)
                .fillMaxWidth(true)
                .enableAnimateToEdge(true)
                .dimAmount(0.5f);
    }


}
