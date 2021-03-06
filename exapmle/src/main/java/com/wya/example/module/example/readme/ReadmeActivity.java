package com.wya.example.module.example.readme;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;

import butterknife.BindView;

public class ReadmeActivity extends BaseActivity {
    
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    
    private String url = "";
    private boolean skip;
    
    @Override
    protected int getLayoutID() {
        return R.layout.activity_readme;
    }
    
    @Override
    protected void initView() {
        url = getIntent().getStringExtra("url");
        skip = getIntent().getBooleanExtra("skip", false);
        setToolBarTitle(url.split("/")[url.split("/").length - 1].replace(".md", ""));
        initWebView(url);
    }
    
    @SuppressLint("NewApi")
    private void initWebView(String content) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setBlockNetworkImage(false);//解决图片不显示
        webView.setDrawingCacheEnabled(true);
        webView.setWebViewClient(new HelloWebViewClient());
        webView.loadUrl(content);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress != 100) {
                    progressBar.setProgress(newProgress);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        webView.setInitialScale(150);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        settings.setDisplayZoomControls(false);
    }
    
    
    //Web视图
    class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (skip) {
                view.loadUrl(url);
            }
            return true;
        }
        
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
