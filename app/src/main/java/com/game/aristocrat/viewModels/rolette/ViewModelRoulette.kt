package com.game.aristocrat.viewModels.rolette

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.game.aristocrat.helpful.BalanceManager
import com.game.aristocrat.viewModels.ViewModelGame
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ViewModelRoulette @Inject constructor() : ViewModel(), ViewModelGame {
    override val selectedBet: MutableLiveData<Int> by lazy {
        MutableLiveData(10)
    }
    override val balance: MutableLiveData<Int> by lazy {
        MutableLiveData(BalanceManager.DEFAULT_VALUE)
    }
    override val lastWin: MutableLiveData<Int> by lazy {
        MutableLiveData(0)
    }

    suspend fun spin(iv: ImageView) {
        balance.value?.let { balance ->
            selectedBet.value?.let { selectedBet ->
                if(balance >= selectedBet) {
                    this.balance.value = balance - selectedBet

                    repeat(Random.nextInt(50, 76)) {
                        var r = iv.rotation + 20
                        if(r >= 360) r = 0f
                        iv.rotation = r
                        delay(50)
                    }
                    repeat(7) {
                        var r = iv.rotation + 20
                        if(r >= 360) r = 0f
                        iv.rotation = r
                        delay(200)
                    }
                    repeat(2) {
                        var r = iv.rotation + 20
                        if(r >= 360) r = 0f
                        iv.rotation = r
                        delay(500)
                    }
                    var lw = when(iv.rotation) {
                        60f, 240f -> 10
                        220f -> 20
                        120f, 200f, 260f, 340f -> 50
                        40f, 100f, 160f, 300f -> 100
                        20f -> 200
                        140f -> 500
                        280f -> 2000
                        else -> 0
                    }
                    lw = when(selectedBet) {
                        10 -> lw
                        25 -> (lw * 2.5).toInt()
                        50 -> lw * 5
                        else -> lw * 10
                    }
                    Log.i("Roulette win", "Rotate: ${iv.rotation}; Win: $lw")
                    val b = (balance - selectedBet) + lw
                    this.balance.value = b
                    this.lastWin.value = lw
                    BalanceManager.setBalance(b)
                }
            }
        }
    }
}