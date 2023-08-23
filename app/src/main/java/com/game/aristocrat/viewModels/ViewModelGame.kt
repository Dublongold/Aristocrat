package com.game.aristocrat.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

interface ViewModelGame {
    val selectedBet: MutableLiveData<Int>
    val balance: MutableLiveData<Int>
    val lastWin: MutableLiveData<Int>
}