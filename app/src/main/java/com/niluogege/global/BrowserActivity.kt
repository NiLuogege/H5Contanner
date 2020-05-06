package com.niluogege.global


import android.os.Build
import android.os.Bundle
import android.util.Log

import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.xianghuanji.jsbridge.BridgeWebView
import com.xianghuanji.jsbridge.DefaultHandler

/**
 * Created by niluogege on 2020/2/11.
 */
class BrowserActivity : AppCompatActivity() {

    var url: String = "http://www.iglobalmall.com"


    private lateinit var webView: BridgeWebView

    private var progressbar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusBarUtil.setLightModeNoFullscreen(this)
        StatusBarUtil.setColor(this, resources.getColor(android.R.color.white), 0)

        setContentView(R.layout.activity_browser)


        webView = findViewById(R.id.webView)
        progressbar = findViewById(R.id.progressbar)
        progressbar?.isIndeterminate = false

        webView.webChromeClient = client
        webView.webViewClient = webViewClient


        val webSettings = webView.settings
        //允许webview对文件的操作
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.allowUniversalAccessFromFileURLs = true
            webSettings.allowFileAccessFromFileURLs = true
        }
        webSettings.allowFileAccess = true


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        webView.loadUrl(url)

    }

    override fun onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }


    private val client = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (newProgress == 100) {
                progressbar?.visibility = View.GONE
            } else {
                if (progressbar?.visibility == View.GONE) {
                    progressbar?.visibility = View.VISIBLE
                }
                progressbar?.progress = newProgress
            }
            super.onProgressChanged(view, newProgress)
        }
    }

    private val webViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return false
        }
    }
}