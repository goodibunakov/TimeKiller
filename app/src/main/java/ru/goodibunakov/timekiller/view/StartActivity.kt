package ru.goodibunakov.timekiller.view

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_start.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.goodibunakov.timekiller.MainActivity
import ru.goodibunakov.timekiller.R
import ru.goodibunakov.timekiller.presenter.StartActivityPresenter

class StartActivity : MvpAppCompatActivity(), StartActivityView {

    @InjectPresenter
    lateinit var startActivityPresenter: StartActivityPresenter

    @ProvidePresenter
    fun provideStartActivityPresenter(): StartActivityPresenter {
        return StartActivityPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        but.setOnClickListener {
            startActivityPresenter.buttonClicked()
        }
    }

    override fun buttonClicked() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
