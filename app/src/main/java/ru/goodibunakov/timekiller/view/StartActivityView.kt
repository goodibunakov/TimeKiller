package ru.goodibunakov.timekiller.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface StartActivityView: MvpView {
    fun buttonClicked()
}
