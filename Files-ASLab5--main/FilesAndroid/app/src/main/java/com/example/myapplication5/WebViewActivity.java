package com.example.myapplication5;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity
{

    WebView webView;
    TextView browserUrl;
    ImageButton backButton;
    ImageButton forwardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = getIntent().getStringExtra("url");

        findViewById(R.id.closeBrowser).setOnClickListener(v -> close());

        backButton = findViewById(R.id.browserBackButton);
        forwardButton = findViewById(R.id.browserForwardButton);
        backButton.setVisibility(View.GONE);
        forwardButton.setVisibility(View.GONE);
        backButton.setOnClickListener(v -> back());
        forwardButton.setOnClickListener(v -> forward());

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        if (url == null)
            webView.loadUrl("http://google.com");
        else
        {
            url = url.toLowerCase();
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            webView.loadUrl(url);
        }
        webView.setWebViewClient(new CustomWebViewClient());

        browserUrl = findViewById(R.id.browserURL);
        browserUrl.setText(url);
    }

    void back()
    {
        if (webView.canGoBack())
        {
            webView.goBack();
        }
    }

    void forward()
    {
        if (webView.canGoForward())
        {
            webView.goForward();
        }
    }

    @Override
    public void onBackPressed()
    {

        if (webView.canGoBack())
            webView.goBack();
        else
        {
            close();
        }
    }

    void close()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("");
        builder.setMessage("Close Web View?");
        builder.setPositiveButton("Confirm",
                (dialog, which) -> finish());
        builder.setNegativeButton("Cancel", (dialog, which) ->
        {
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class CustomWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
        {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
        {
            handler.proceed();
        }

        @Override
        public void onLoadResource(WebView view, String url)
        {
            super.onLoadResource(view, url);
            if (webView.canGoBack())
                backButton.setVisibility(View.VISIBLE);
            else
                backButton.setVisibility(View.GONE);
            if (webView.canGoForward())
                forwardButton.setVisibility(View.VISIBLE);
            else
                forwardButton.setVisibility(View.GONE);
            browserUrl.setText(webView.getTitle());
        }
    }
}