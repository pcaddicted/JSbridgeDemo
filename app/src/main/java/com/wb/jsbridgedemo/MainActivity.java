package com.wb.jsbridgedemo;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Button  refreshBtn;
    private Button showBtn;
    private EditText editText;
    private MainActivity self=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        webView = findViewById(R.id.web);
        refreshBtn = findViewById(R.id.refreshBtn);
        showBtn = findViewById(R.id.showBtn);

        webView.loadUrl("http://192.168.31.242:8080?timestamp"+new Date().getTime());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                if (!message.startsWith("jsbridge://")) {
                    return super.onJsAlert(view, url, message, result);
                }
                String text = message.substring(message.indexOf("=")+1);
                Log.e("wb",text);
                self.showNativeDialog(text);
                result.confirm();
                return true;
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("http://192.168.31.242:8080?timestamp"+new Date().getTime());
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputVal = editText.getText().toString();
                self.showWebDialog(inputVal);
            }
        });
    }

    private void showWebDialog(String inputVal){
        String jsCode = String.format("window.showWebDialog('%s')",inputVal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(jsCode,null);
        }
    }

    private void showNativeDialog(String text){
        new AlertDialog.Builder(this).setMessage(text).create().show();
    }
}
