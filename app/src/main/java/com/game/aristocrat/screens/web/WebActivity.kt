package com.game.aristocrat.screens.web


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.game.aristocrat.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@Suppress("DEPRECATION")
class WebActivity : AppCompatActivity() {
    private var _webView: WebView? = null
    private val webView: WebView
        get() {
            if(_webView == null) {
                _webView = findViewById(getNecessaryId("webView"))
            }
            return _webView!!
        }
    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    private var callback: Uri? = null
    private lateinit var mainUrl: String

    private fun getNecessaryId(name: String): Int = when(name) {
        "activity" -> R.layout.activity_web
        "webView" -> R.id.singleView
        else -> throw IllegalArgumentException("Not found resource id element for $name.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getNecessaryId("activity"))
        mainUrl = intent.getStringExtra("url") ?: throw NullPointerException("Url is null...")
        onBackPressedDispatcher.addCallback(MainOnBackPressedCallback(webView)
        )
        assignSettingProperties()
    }

    private fun assignSettingProperties() {
        webView.webViewClient = Client()
        webView.webChromeClient = WebViewWebChromeClient(requestPermissionLauncher) {
            mFilePathCallback = it
            true
        }
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.mixedContentMode = 0
        webView.settings.userAgentString = webView.settings.userAgentString.replace("; wv", "")
        true.assignForSomeSettingsOneValue()
        val cocoManager = CookieManager.getInstance()
        cocoManager.setAcceptCookie(true)
        cocoManager.setAcceptThirdPartyCookies(webView, true)
        webView.loadUrl(mainUrl)
    }

    private fun Boolean.assignForSomeSettingsOneValue() {
        webView.settings.domStorageEnabled = this
        webView.settings.allowContentAccess = this
        webView.settings.databaseEnabled = this
        webView.settings.loadWithOverviewMode = this
        webView.settings.useWideViewPort = this
        webView.settings.allowFileAccess = this
        webView.settings.javaScriptCanOpenWindowsAutomatically = this
        webView.settings.javaScriptEnabled = this
        webView.settings.allowFileAccessFromFileURLs = this
        webView.settings.allowUniversalAccessFromFileURLs = this
    }

    class Client : WebViewClient() {
        private var accountIsNull = false

        override fun onReceivedLoginRequest(
            view: WebView,
            realm: String,
            account: String?,
            args: String
        ) {
            accountIsNull = account == null
            Log.i("Account is null", "${if(accountIsNull) "Yes" else "No"}, account is${if(accountIsNull) "" else "n't"} null.")
            super.onReceivedLoginRequest(view, realm, account, args)
        }

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val uri = request.url.toString()
            return if (uri.contains("/")) {
                Log.i("Uri", uri)
                if(uri.contains("http")) false
                else {
                    throw IllegalArgumentException("Url not contains \"http\"")
                }
            } else true
        }
    }

    private val requestPermissionLauncher = registerForActivityResult<String, Boolean>(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean? ->
        makeLog("Is granted?: $isGranted")
        lifecycleScope.launch(Dispatchers.IO) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                val photoFile = File.createTempFile(
                    "FILE".lowercase(),
                    ".JPG".lowercase(),
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                )
                takePictureIntent.putExtra(
                    "output",
                    Uri.fromFile(photoFile)
                )
                callback = Uri.fromFile(photoFile)
            }
            catch (e: Exception) {
                makeLog("Unable to create temp file. Exception message: ${e.message}")
            }
            workWithPrevious(takePictureIntent)
        }
    }

    private fun workWithPrevious(takePictureIntent: Intent) {
        val startNewIntent = { previous: Intent ->
            Intent(Intent.ACTION_CHOOSER).also { ci ->
                ci.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePictureIntent))
                ci.putExtra(Intent.EXTRA_INTENT, previous)
                startActivityForResult(ci, 1)
            }
        }
        val previous = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        previous.type = "*%s*".format("/")
        startNewIntent(previous)
    }

    private fun Uri.toArray(): Array<Uri> = arrayOf(this@toArray)
    private fun String.toUri(): Uri = Uri.parse(this@toUri)

    @Deprecated("Deprecated in Java")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mFilePathCallback?.onReceiveValue(if (resultCode != -1) {
            null
        } else {
            data?.dataString?.toUri()?.toArray() ?: callback?.toArray()
        })
        mFilePathCallback = null
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView.restoreState(savedInstanceState)
    }

    private fun makeLog(text: String) {
        Log.e("Web activity", text)
    }
}