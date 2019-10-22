package ru.goodibunakov.timekiller.view.main

import moxy.MvpView
import moxy.viewstate.strategy.*

@StateStrategyType(value = AddToEndStrategy::class)
interface MainActivityView : MvpView {
    fun updateGameScoreTextView(score: String)
    fun startCountDown()
    fun createCountDownTimer(initCountDown: Int, countDownInterval: Long)
    @StateStrategyType(value = SkipStrategy::class)
    fun showToast(score: Int)
    fun updateTimeLeftTextView(timeLeft: Int)
    fun onStopTrigger()
}
