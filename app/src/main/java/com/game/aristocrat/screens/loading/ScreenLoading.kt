package com.game.aristocrat.screens.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.game.aristocrat.screens.main.MainActivity
import com.game.aristocrat.R

class ScreenLoading: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.screen_loading, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            if(activity is MainActivity) {
                activity.goToMenuCallback = {
                    val navController = findNavController()
                    navController.navigate(R.id.openMenu)
                }
            }
        }
    }
}