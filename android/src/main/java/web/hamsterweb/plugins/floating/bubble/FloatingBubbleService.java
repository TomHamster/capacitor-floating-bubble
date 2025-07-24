package web.hamsterweb.plugins.floating.bubble;

import static web.hamsterweb.plugins.floating.bubble.BubbleHelper.getCircularBitmap;

import android.graphics.Bitmap;
import android.util.Base64;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.Nullable;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.torrydo.floatingbubbleview.CloseBubbleBehavior;
import com.torrydo.floatingbubbleview.FloatingBubbleListener;
import com.torrydo.floatingbubbleview.helper.ViewHelper;
import com.torrydo.floatingbubbleview.service.expandable.BubbleBuilder;
import com.torrydo.floatingbubbleview.service.expandable.ExpandableBubbleService;
import com.torrydo.floatingbubbleview.service.expandable.ExpandedBubbleBuilder;


public class FloatingBubbleService extends ExpandableBubbleService {
    @Override
    public void onCreate() {
        super.onCreate();
        minimize(); // pokazuje bąbelek na start
    }

    @Nullable
    @Override
    public BubbleBuilder configBubble() {
        String imageBase64 = DefaultVariables.DEFAULT_BUBBLE_IMAGE_BASE_64;
        byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        Bitmapa
        Bitmap circularBitmap = getCircularBitmap(bitmap);
        View bubbleView = ViewHelper.fromBitmap(this, circularBitmap, 60, 60);

        //Inny view
//        View bubbleView = LayoutInflater.from(this).inflate(R.layout.layout_view_test, null);

//        View bubbleView = ViewHelper.fromDrawable(this, com.torrydo.floatingbubbleview.R.drawable.ic_rounded_blue_diamond, 60, 60);
        bubbleView.setOnClickListener(view -> expand());
//        return new BubbleBuilder(this)
//                .bubbleView(bubbleView)
//                .bubbleDraggable(true)
//                .enableAnimateToEdge(true)
//                .startLocation(100, 100);


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
                                .bottomBackground(false)
                                .addFloatingBubbleListener(new FloatingBubbleListener() {
                                    @Override
                                    public void onFingerDown(float x, float y) {}
                                    @Override
                                    public void onFingerUp(float x, float y) {}
                                    @Override
                                    public void onFingerMove(float x, float y) {}
                                })
                                ;
    }

    @Nullable
    @Override
    public ExpandedBubbleBuilder configExpandedBubble() {
//webview
        View expandedView = LayoutInflater.from(this).inflate(R.layout.cpacitorview, null);

        WebView webView = expandedView.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Załaduj wszystkie URL-e w tym samym WebView
                return false;
            }
        });
        // Załaduj lokalną stronę Capacitor (np. index.html z assets)
        webView.loadUrl("https://localhost/index.html"); // lub inny adres Twojej aplikacji

        expandedView.findViewById(R.id.btn).setOnClickListener(view -> minimize());


//        View expandedView = LayoutInflater.from(this).inflate(R.layout.layout_view_test, null);

        expandedView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minimize();
            }
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
