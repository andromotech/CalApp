package andromo.calapp.CalDisp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import andromo.calapp.R;

public class BiraCalD extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview111);
        WebView wb = (WebView) findViewById(R.id.webview);
      //  AdView mAdView = (AdView) findViewById(R.id.ad_view);
     //   AdRequest adRequest = new AdRequest.Builder().build();
     //   mAdView.loadAd(adRequest);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setDisplayZoomControls(false);
        wb.getSettings().setUseWideViewPort(true);
        wb.setInitialScale(50);
        String url = getIntent().getExtras().getString("url");
        wb.loadUrl(url);
        wb.setWebViewClient(new WebViewClient());
        wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                webView.loadUrl("file:///android_res/drawable/internetc.png");
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }
        });
    }
}