package com.codingblocks.msitnotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url1 = getIntent().getStringExtra("url");
        //Log.d("TAG", "onCreate: "+url);
        //String url="https://firebasestorage.googleapis.com/v0/b/msitnotes.appspot.com/o/CSE-sem5%2Fgoogle.pdf?alt=media&token=f6835386-78af-41a1-8cf0-e02425b820dc";
      //  String doc="<iframe src="http://docs.google.com/gview?embedded=true&url='https://firebasestorage.googleapis.com/v0/b/msitnotes.appspot.com/o/CSE-sem5%2Fgoogle.pdf?alt=media&token=f6835386-78af-41a1-8cf0-e02425b820dc'</iframe>;


        //String url2="http://docs.google.com/gview?embedded=true&url=https://firebasestorage.googleapis.com/v0/b/msitnotes.appspot.com/o/CSE-sem5%2Fgoogle.pdf?alt=media&token=f6835386-78af-41a1-8cf0-e02425b820dc";

        String url2="http://docs.google.com/gview?embedded=true&url="+url1;
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebViewActivity.this, description, Toast.LENGTH_SHORT).show();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webView.loadUrl(url2);
    }
}
