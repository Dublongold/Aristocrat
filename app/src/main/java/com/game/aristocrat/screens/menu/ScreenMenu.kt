package com.game.aristocrat.screens.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.game.aristocrat.R
import com.game.aristocrat.helpful.BalanceManager

class ScreenMenu: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.screen_menu, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.balanceText).text = getString(R.string.balance_text, BalanceManager.getBalance())
        view.findViewById<ImageButton>(R.id.openSlotsButton).setOnClickListener {
            findNavController().navigate(R.id.openSlots)
        }
        view.findViewById<ImageButton>(R.id.openRouletteButton).setOnClickListener {
            findNavController().navigate(R.id.openRoulette)
        }
        view.findViewById<ImageButton>(R.id.openPrivacyPolicyButton).setOnClickListener {
            findNavController().navigate(R.id.openPrivacyPolicy)
        }
    }
}