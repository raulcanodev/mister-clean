package dev.raulcano.misterclean;

import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.getcapacitor.Bridge;
import com.getcapacitor.BridgeActivity;
import com.getcapacitor.BridgeWebViewClient;

import java.io.ByteArrayInputStream;
import java.util.Locale;

public class MainActivity extends BridgeActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = getBridge().getWebView();
        webView.setBackgroundColor(0xff080b07);
        webView.setWebViewClient(new MisterWebViewClient(getBridge()));
        enableImmersiveMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableImmersiveMode();
    }

    private void enableImmersiveMode() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    private static final class MisterWebViewClient extends BridgeWebViewClient {
        MisterWebViewClient(Bridge bridge) {
            super(bridge);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Uri uri = request.getUrl();
            String host = uri.getHost();
            if (host != null && isAdHost(host)) {
                return new WebResourceResponse(
                    "text/plain",
                    "UTF-8",
                    new ByteArrayInputStream(new byte[0])
                );
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Uri uri = Uri.parse(url);
            String host = uri.getHost();
            if (host != null && (host.equals("mister.mundodeportivo.com") || host.endsWith(".mundodeportivo.com"))) {
                view.evaluateJavascript(GeneratedFilters.CSS_INJECTION_JAVASCRIPT, null);
            }
        }

        private static boolean isAdHost(String host) {
            String normalized = host.toLowerCase(Locale.ROOT);
            for (String blocked : GeneratedFilters.AD_HOSTS) {
                if (normalized.equals(blocked) || normalized.endsWith("." + blocked)) return true;
            }
            return false;
        }
    }
}
