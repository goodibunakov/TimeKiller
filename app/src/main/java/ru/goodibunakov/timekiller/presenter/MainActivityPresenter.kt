package ru.goodibunakov.timekiller.presenter

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.goodibunakov.timekiller.view.main.MainActivityView

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainActivityView>() {

    private var score = 0
    private val initCountDown = 15000
    private var timeLeftOnTimer = 15000
    private val countDownInterval = 1000L
    private var gameStarted = false


    private fun updateTextViews(score: Int, time: Int) {
        viewState.updateGameScoreTextView(score.toString())
        viewState.updateTimeLeftTextView(time)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        updateTextViews(score, initCountDown / 1000)
        resetGame()
    }

    override fun attachView(view: MainActivityView?) {
        super.attachView(view)
        if (gameStarted)
            restoreGame()
    }

    fun restartButtonClicked() {
        if (!gameStarted) resetGame()
    }

    private fun resetGame() {
        score = 0
        timeLeftOnTimer = initCountDown
        updateTextViews(score, initCountDown / 1000)
        viewState.createCountDownTimer(initCountDown, countDownInterval)
        gameStarted = false
    }

    private fun restoreGame() {
        updateTextViews(score, timeLeftOnTimer / 1000)
        if (timeLeftOnTimer != 0 && gameStarted) {
            viewState.createCountDownTimer(timeLeftOnTimer, countDownInterval)
            viewState.startCountDown()
            gameStarted = true
        }
    }

    fun clickButtonClicked() {
        if (timeLeftOnTimer == 0)
            return

        if (!gameStarted)
            startGame()

        score += 1
        viewState.updateGameScoreTextView(score.toString())
    }

    private fun startGame() {
        gameStarted = true
        viewState.startCountDown()
    }

    fun endGame() {
        timeLeftOnTimer = 0
        viewState.showToast(score)
        gameStarted = false
    }

    fun setTimeLeftOnTimer(millisUntilFinished: Long) {
        timeLeftOnTimer = millisUntilFinished.toInt()
        val timeLeft = millisUntilFinished / 1000
        viewState.updateTimeLeftTextView(timeLeft.toInt())
    }

    fun onStop() {
        viewState.onStopTrigger()
    }
}