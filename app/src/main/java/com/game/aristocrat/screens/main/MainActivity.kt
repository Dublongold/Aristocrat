package com.game.aristocrat.screens.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.game.aristocrat.R
import com.game.aristocrat.helpful.BalanceManager
import com.game.aristocrat.helpful.FileNames
import com.game.aristocrat.internet.GetGistData
import com.game.aristocrat.internet.GetGistDataDto
import com.game.aristocrat.screens.web.WebActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    var goToMenuCallback: (() -> Unit)? = null

    private var url
        get() = getSharedPreferences("important", MODE_PRIVATE).getString("link", "") ?: ""
        set(value) = getSharedPreferences("important", MODE_PRIVATE).edit().putString("link", value).apply()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        lifecycleScope.launch {
            fun startWebIntent(url: String) {
                val i = Intent(this@MainActivity, WebActivity::class.java)
                i.putExtra("url", url)
                startActivity(i)
            }
            url.also {
                if(it != "" && it.contains("http")) {
                    startWebIntent(it)
                }
                else {
                    var result = GetGistData().getGistData()
                    if (result != null) {
                        val tResult = result
                        result = GetGistDataDto(true, "https://google.com")
                        result = tResult
                        // Отакий прикольчик ;).
                        if (result.pusk && result.link != null) {
                            Log.i("Response", "Pusk is true and link isn't null.")
                            val i = Intent(this@MainActivity, WebActivity::class.java)
                            i.putExtra("url", result.link)
                            url = result.link ?: ""
                            startActivity(i)
                            return@launch
                        }
                    }
                    BalanceManager.getBalanceFromFile = {
                        getSharedPreferences(FileNames.BALANCE_FILE, MODE_PRIVATE).getInt(FileNames.BALANCE_FILE, BalanceManager.DEFAULT_VALUE)
                    }
                    BalanceManager.setBalanceInFile = {
                        getSharedPreferences(FileNames.BALANCE_FILE, MODE_PRIVATE).edit().putInt(FileNames.BALANCE_FILE, it).apply()
                    }
                    goToMenuCallback?.invoke()
                }
            }
        }
    }
}