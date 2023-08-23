package com.game.aristocrat.screens.slots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.game.aristocrat.R
import com.game.aristocrat.screens.game.ScreenGame
import com.game.aristocrat.viewModels.slots.ViewModelSlots
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ScreenSlots: ScreenGame() {
    override val vm: ViewModelSlots by viewModels()

    private var s1: ImageView? =  null
    private var s2: ImageView? =  null
    private var s3: ImageView? =  null
    private var s4: ImageView? =  null
    private var s5: ImageView? =  null
    private var s6: ImageView? =  null
    private var s7: ImageView? =  null
    private var s8: ImageView? =  null
    private var s9: ImageView? =  null
    private var s10: ImageView? =  null
    private var s11: ImageView? =  null
    private var s12: ImageView? =  null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.screen_slots, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val images = listOf(
            R.drawable.slot_item_01,
            R.drawable.slot_item_02,
            R.drawable.slot_item_03,
            R.drawable.slot_item_04,
            R.drawable.slot_item_05,
            R.drawable.slot_item_06,
            R.drawable.slot_item_07,
            R.drawable.slot_item_08,
            R.drawable.slot_item_09,
            R.drawable.slot_item_10,
            R.drawable.slot_item_11,
            R.drawable.slot_item_12,
        )
        view.apply {
            s1 = findViewById(R.id.slot1)
            s2 = findViewById(R.id.slot2)
            s3 = findViewById(R.id.slot3)
            s4 = findViewById(R.id.slot4)
            s5 = findViewById(R.id.slot5)
            s6 = findViewById(R.id.slot6)
            s7 = findViewById(R.id.slot7)
            s8 = findViewById(R.id.slot8)
            s9 = findViewById(R.id.slot9)
            s10 = findViewById(R.id.slot10)
            s11 = findViewById(R.id.slot11)
            s12 = findViewById(R.id.slot12)
            findViewById<ImageButton>(R.id.spinButton).setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    it.isEnabled = false
                    vm.spin(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, images)
                    it.isEnabled = true
                }
            }
        }
    }
}