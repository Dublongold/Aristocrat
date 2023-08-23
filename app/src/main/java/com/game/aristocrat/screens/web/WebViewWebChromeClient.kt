package com.game.aristocrat.screens.web

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher

class WebViewWebChromeClient(
    private val requestPermissionLauncher: ActivityResultLauncher<String>,
    private val setmFilePathCallback: (ValueCallback<Array<Uri>>) -> Boolean): WebChromeClient() {
    override fun onShowFileChooser(
        webView: WebView,
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: FileChooserParams
    ): Boolean {
        requestPermissionLauncher.launch("android.permission.CAMERA")
        return setmFilePathCallback(filePathCallback)
    }
}