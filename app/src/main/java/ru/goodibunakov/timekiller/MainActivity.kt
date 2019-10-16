package ru.goodibunakov.timekiller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var score = 0
    private val time = 30
    private var gameStarted = false
    private lateinit var countDownTimer: CountDownTimer
    private val initCountDown = 15000L
    private val countDownInterval = 1000L
    private var timeLeftOnTimer = 15000L

    companion object {
        private const val SCORE_KEY = "score_key"
        private const val TIME_LEFT_KEY = "time_left_key"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameScoreTextView.text = getString(R.string.your_score, score.toString())
        timeLeftTextView.text = getString(R.string.time_left, time.toString())
        again.setOnClickListener { if (!gameStarted) resetGame() }

        if (savedInstanceState != null && savedInstanceState.containsKey(SCORE_KEY) && savedInstanceState.containsKey(TIME_LEFT_KEY)){
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        } else {
            resetGame()
        }

        button.setOnClickListener {
            val bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
            it.startAnimation(bounceAnim)
            incrementScore() }
    }

    private fun restoreGame() {
        gameScoreTextView.text = getString(R.string.your_score, score.toString())
        val restoredTime = timeLeftOnTimer / 1000
        timeLeftTextView.text = getString(R.string.time_left, restoredTime.toString())

        if (timeLeftOnTimer != 0L) {
            countDownTimer = object : CountDownTimer(timeLeftOnTimer, countDownInterval){
                override fun onFinish() {
                    endGame()
                }

                override fun onTick(millisUntilFinished: Long) {
                    timeLeftOnTimer = millisUntilFinished
                    val timeLeft = millisUntilFinished / 1000
                    timeLeftTextView.text = getString(R.string.time_left, timeLeft.toString())
                }
            }
            countDownTimer.start()
            gameStarted = true
        }
    }

    private fun resetGame() {
        button.setOnClickListener { incrementScore() }
        score = 0
        gameScoreTextView.text = getString(R.string.your_score, score.toString())
        timeLeftTextView.text = getString(R.string.time_left, (initCountDown / 1000).toString())

        countDownTimer = object : CountDownTimer(initCountDown, countDownInterval) {
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.time_left, timeLeft.toString())
            }

        }
        gameStarted = false
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        button.setOnClickListener(null)
        timeLeftOnTimer = 0
        MyToast().make(
            this,
            getString(R.string.game_over, score.toString()),
            Toast.LENGTH_LONG,
            "#" + Integer.toHexString(ContextCompat.getColor(this, R.color.colorAccent)),
            "#" + Integer.toHexString(ContextCompat.getColor(this, android.R.color.white)),
            R.drawable.love_time_icon_time_png_1600_1600
        ).show()
        gameStarted = false
    }

    private fun incrementScore() {
        if (!gameStarted) startGame()
        score += 1
        val newScore = getString(R.string.your_score, score.toString())
        gameScoreTextView.text = newScore
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!gameStarted) return

        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer?.cancel()
    }
}