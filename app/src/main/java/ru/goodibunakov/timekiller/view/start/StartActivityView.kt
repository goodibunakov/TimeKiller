package ru.goodibunakov.timekiller.view.start

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndStrategy::class)
interface StartActivityView: MvpView {
    fun startNewActivity()
}
