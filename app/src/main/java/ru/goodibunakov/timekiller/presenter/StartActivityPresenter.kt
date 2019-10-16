package ru.goodibunakov.timekiller.presenter

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.goodibunakov.timekiller.view.StartActivityView

@InjectViewState
class StartActivityPresenter : MvpPresenter<StartActivityView>() {

    fun buttonClicked(){
        viewState.buttonClicked()
    }
}