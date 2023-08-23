package com.game.aristocrat.screens.web

import android.webkit.WebView
import androidx.activity.OnBackPressedCallback

class MainOnBackPressedCallback(private val webView: WebView): OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack()
        }
    }
}