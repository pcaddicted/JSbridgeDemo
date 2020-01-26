package com.wb.jsbridgedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Button  refreshBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.web);
        refreshBtn = findViewById(R.id.refreshBtn);

        webView.loadUrl("http://192.168.31.242:8080?timestamp"+new Date().getTime());

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("http://192.168.31.242:8080?timestamp"+new Date().getTime());
            }
        });
    }
}
