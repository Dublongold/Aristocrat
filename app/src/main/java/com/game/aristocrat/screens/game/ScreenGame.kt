package com.game.aristocrat.screens.game

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.game.aristocrat.R
import com.game.aristocrat.helpful.BalanceManager
import com.game.aristocrat.viewModels.ViewModelGame

abstract class ScreenGame: Fragment() {
    abstract val vm: ViewModelGame
    protected var bt: TextView? = null
    protected var lwt: TextView? = null
    protected var bs: Map<Int, LinearLayout>? = null
    protected var b: TextView? = null
    protected var lw: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.backToMenu).setOnClickListener {
            findNavController().popBackStack()
        }
        bs = mapOf<Int, LinearLayout>(
            10 to view.findViewById(R.id.bet10),
            25 to view.findViewById(R.id.bet25),
            50 to view.findViewById(R.id.bet50),
            100 to view.findViewById(R.id.bet100)
        ).also {
            for((k, b) in it) {
                b.setOnClickListener {
                    vm.selectedBet.value = k
                }
                val twwg = b.findViewById<TextView>(R.id.betTextWithGradient)
                twwg.paint.shader = getTextGradient(twwg)
            }
        }
        observeBet()
        b = view.findViewById(R.id.balanceText)
        observeBalance()
        vm.balance.value = BalanceManager.getBalance()
        lw = view.findViewById(R.id.lastWinText)
        observeLastWin()
    }

    private fun observeBet() {
        vm.selectedBet.observe(viewLifecycleOwner) {
            bs?.let { bs ->
                for((k, b) in bs) {
                    val tw = b.findViewById<TextView>(R.id.betText)
                    val twwg = b.findViewById<TextView>(R.id.betTextWithGradient)
                    if(it == k) {
                        b.setBackgroundResource(R.drawable.bet_selected)
                        b.findViewById<ImageView>(R.id.betDiamond).setImageResource(R.drawable.selected_diamond)
                        tw.visibility = View.INVISIBLE
                        twwg.visibility = View.VISIBLE
                    }
                    else {
                        b.setBackgroundResource(R.drawable.bet_not_selected)
                        b.findViewById<ImageView>(R.id.betDiamond).setImageResource(R.drawable.not_selected_diamond)
                        tw.visibility = View.VISIBLE
                        twwg.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun observeBalance() {
        vm.balance.observe(viewLifecycleOwner) {
            b?.let { b ->
                b.text = getString(R.string.balance_text, it)
            }
        }
    }

    private fun observeLastWin() {
        vm.lastWin.observe(viewLifecycleOwner) {
            lw?.let {lw ->
                lw.text = getString(R.string.last_win_text, it)
            }
        }
    }

    private fun getTextGradient(tw: TextView) = LinearGradient(0f, 0f, 0f, tw.textSize, intArrayOf(0xFFDA5ACD.toInt(), 0xFF7F31DF.toInt()), null, Shader.TileMode.CLAMP) as Shader
}