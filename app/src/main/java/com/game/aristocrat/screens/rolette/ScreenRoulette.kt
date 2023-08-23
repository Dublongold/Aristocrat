package com.game.aristocrat.screens.rolette

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.game.aristocrat.R
import com.game.aristocrat.screens.game.ScreenGame
import com.game.aristocrat.viewModels.rolette.ViewModelRoulette
import kotlinx.coroutines.launch

class ScreenRoulette() : ScreenGame() {
    override val vm: ViewModelRoulette by viewModels()
    private var rb: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.screen_roulette, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rb = view.findViewById(R.id.rouletteBody)
        view.findViewById<ImageButton>(R.id.spinButton).setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                it.isEnabled = false
                rb?.let { rb ->
                    vm.spin(rb)
                }
                it.isEnabled = true
            }
        }
    }
}