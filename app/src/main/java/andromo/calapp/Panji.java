package andromo.calapp;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;

public class Panji extends AppCompatActivity {
    private AdView adView;
    private AdView adView1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview111);
        WebView wb = (WebView) findViewById(R.id.webview);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setDisplayZoomControls(false);
        wb.getSettings().setUseWideViewPort(true);
        wb.setInitialScale(50);
        wb.loadUrl("https://www.sm05.online/dpanji.png");
        wb.setWebViewClient(new WebViewClient());
        wb.getSettings().setJavaScriptEnabled(true);
        AudienceNetworkAds.initialize(this);
        adView = new AdView(this, "2714843328612870_2714847785279091", AdSize.BANNER_HEIGHT_50);
        adView1 = new AdView(this, "2714843328612870_2714847785279091", AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container2);
        LinearLayout adContainer1 = (LinearLayout) findViewById(R.id.banner_container1);
        adContainer.addView(adView);
        adContainer1.addView(adView1);
        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Toast.makeText(Panji.this, "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };

        adView.loadAd();
        adView1.loadAd();
        wb.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                webView.loadUrl("file:///android_res/drawable/intc.png");
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }
        });
    }
    @Override
    protected void onDestroy() {
        if (adView != null)  {
            adView.destroy();
        }
        super.onDestroy();
    }

}

