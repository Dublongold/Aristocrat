package com.game.aristocrat.helpful

import android.util.Log

object BalanceManager {
    const val DEFAULT_VALUE = 2000
    private val getBalanceResultIfNull = {
        Log.w("Get balance", "getBalanceFromFile is null")
        DEFAULT_VALUE
    }

    var getBalanceFromFile: (() -> Int)? = null
    var setBalanceInFile: ((Int) -> Unit)? = null

    fun getBalance(): Int {
        return getBalanceFromFile?.let {getBalanceFromFile ->
            getBalanceFromFile()
        } ?: getBalanceResultIfNull()
    }

    fun setBalance(value: Int) = setBalanceInFile?.invoke(value)
}