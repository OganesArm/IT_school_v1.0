package com.example.itschool;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
//встроенный браузер
        webView= findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://gb.ru/");
        webView.setWebViewClient(new WebViewClient()); // любая ссылка будет срабатывать внутри приложения без перехода в браузер
    }
// добавляем кнопку назад внутри браузера
    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();

    }

    public void goHome (View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
    public void goContacts (View view) {
        Intent intent = new Intent (this, ContactsActivity.class);
        startActivity(intent);
    }

}