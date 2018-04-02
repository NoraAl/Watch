package edu.umsl.watch

import android.app.Activity
import android.os.Bundle

class MainView : Activity() {
    private var clockView: ClockView? = null
    private var buttonView: ButtonsView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)

        clockView = fragmentManager.findFragmentById(R.id.clockFragment) as ClockView
        buttonView  = fragmentManager.findFragmentById(R.id.buttonsFragment) as ButtonsView
    }
}
