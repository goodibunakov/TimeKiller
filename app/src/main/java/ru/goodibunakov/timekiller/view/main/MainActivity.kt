package ru.goodibunakov.timekiller.view.main

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.goodibunakov.timekiller.R
import ru.goodibunakov.timekiller.presenter.MainActivityPresenter
import ru.goodibunakov.timekiller.util.MyToast

class MainActivity : MvpAppCompatActivity(R.layout.activity_main),
    MainActivityView {

    private var countDownTimer: CountDownTimer? = null

    @InjectPresenter
    lateinit var mainActivityPresenter: MainActivityPresenter

    @ProvidePresenter
    fun providePresenter(): MainActivityPresenter {
        return MainActivityPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        restart.setOnClickListener { mainActivityPresenter.restartButtonClicked() }

        click.setOnClickListener {
            it.startAnimation(bounceAnim)
            mainActivityPresenter.clickButtonClicked()
        }
    }

    override fun createCountDownTimer(initCountDown: Int, countDownInterval: Long) {
        countDownTimer = object : CountDownTimer(initCountDown.toLong(), countDownInterval) {
            override fun onFinish() {
                mainActivityPresenter.endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                mainActivityPresenter.setTimeLeftOnTimer(millisUntilFinished)
            }
        }
    }

    override fun updateTimeLeftTextView(timeLeft: Int) {
        timeLeftTextView.text = getString(R.string.time_left, timeLeft.toString())
    }

    override fun startCountDown() {
        countDownTimer?.start()
    }

    override fun showToast(score: Int) {
        MyToast().make(
            this,
            getString(R.string.game_over, score.toString()),
            Toast.LENGTH_LONG,
            "#" + Integer.toHexString(
                ContextCompat.getColor(this, R.color.colorAccent)
            ),
            "#" + Integer.toHexString(ContextCompat.getColor(this, android.R.color.white)),
            R.drawable.love_time_icon_time_png_1600_1600
        ).show()
    }

    override fun updateGameScoreTextView(score: String) {
        gameScoreTextView.text = getString(R.string.your_score, score)
    }

    override fun onStop() {
        mainActivityPresenter.onStop()
        super.onStop()
    }

    override fun onStopTrigger() {
        countDownTimer?.cancel()
    }
}