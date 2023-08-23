package com.game.aristocrat.viewModels.slots

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
class ViewModelSlots @Inject constructor() : ViewModel(), ViewModelGame {
    override val selectedBet: MutableLiveData<Int> by lazy {
        MutableLiveData(10)
    }
    override val balance: MutableLiveData<Int> by lazy {
        MutableLiveData(BalanceManager.DEFAULT_VALUE)
    }
    override val lastWin: MutableLiveData<Int> by lazy {
        MutableLiveData(0)
    }
    // Slots images
    private val sis = mutableListOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12)

    suspend fun spin(
        s1: ImageView?,
        s2: ImageView?,
        s3: ImageView?,
        s4: ImageView?,
        s5: ImageView?,
        s6: ImageView?,
        s7: ImageView?,
        s8: ImageView?,
        s9: ImageView?,
        s10: ImageView?,
        s11: ImageView?,
        s12: ImageView?,
        images: List<Int>
    ) {
        balance.value?.let { balance ->
            selectedBet.value?.let { selectedBet ->
                val c1 = balance >= selectedBet
                val c2 = s1 != null && s2 != null && s3 != null &&
                        s4 != null && s5 != null && s6 != null &&
                        s7 != null && s8 != null && s9 != null &&
                        s10 != null && s11 != null && s12 != null
                if(c1 && s1 != null && s2 != null && s3 != null &&
                    s4 != null && s5 != null && s6 != null &&
                    s7 != null && s8 != null && s9 != null &&
                    s10 != null && s11 != null && s12 != null) {
                    this.balance.value = balance - selectedBet
                    repeat(Random.nextInt(20, 36)) {
                        spinRandom(0, s1, s2, s3, images)
                        spinRandom(3, s4, s5, s6, images)
                        spinRandom(6, s7, s8, s9, images)
                        spinRandom(9, s10, s11, s12, images)
                        delay(100)
                    }
                    repeat(5) {
                        spinRandom(3, s4, s5, s6, images)
                        spinRandom(6, s7, s8, s9, images)
                        spinRandom(9, s10, s11, s12, images)
                        delay(100)
                    }
                    repeat(5) {
                        spinRandom(6, s7, s8, s9, images)
                        spinRandom(9, s10, s11, s12, images)
                        delay(100)
                    }
                    repeat(5) {
                        spinRandom(9, s10, s11, s12, images)
                        delay(100)
                    }
                    var lw = 0
                    if(sis[0] == sis[3] && sis[3] == sis[6] && sis[6] == sis[9]) {
                        lw += selectedBet * (sis[0] + 1)
                    }
                    if(sis[1] == sis[4] && sis[4] == sis[7] && sis[7] == sis[10]) {
                        lw += selectedBet * (sis[1] + 1)
                    }
                    if(sis[2] == sis[5] && sis[5] == sis[8] && sis[8] == sis[11]) {
                        lw += selectedBet * (sis[2] + 1)
                    }
                    val b = balance - selectedBet + lw
                    this.balance.value = b
                    this.lastWin.value = lw
                    Log.i("Spin", "Win: $lw")
                    BalanceManager.setBalance(b)
                }
                else {
                    if(!c2) {
                        Log.w("Spin", "Someone of slots is null.")
                    } else {
                        Log.w("Spin", "Balance (${balance}) is smaller than ${selectedBet}.")
                    }
                }
            }
        }
    }

    private fun spinRandom(fi: Int, s1: ImageView, s2: ImageView, s3: ImageView, images: List<Int>) {
        sis[fi+2] = sis[fi+1]
        sis[fi+1] = sis[fi]
        sis[fi] = Random.nextInt(0, images.size)
        s3.setImageDrawable(s2.drawable)
        s2.setImageDrawable(s1.drawable)
        s1.setImageResource(images[sis[fi]])
    }
}