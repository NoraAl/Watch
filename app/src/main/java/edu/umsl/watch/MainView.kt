package edu.umsl.watch

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock

class MainView : Activity(), ButtonsView.Action {
    private var clockView: ClockView? = null
    private var buttonView: ButtonsView? = null
    private var ticks: Int = 0
    private var ticking: Boolean = false
    private var handler: Handler? = Handler()



    private var milliSeconds: Long = 0
    private var startTime: Long = 0
    private var TimeBuff: Long = 0
    private var totalPasuedTime: Long = 0
    private var total: Long = 0
    private var timePaused: Long = 0
    private var paused: Boolean = false


    private var seconds: Int = 0
    private var minutes: Int = 0
    private var millis: Int = 0
    private var text: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)

        clockView = fragmentManager.findFragmentById(R.id.clockFragment) as ClockView
        buttonView  = fragmentManager.findFragmentById(R.id.buttonsFragment) as ButtonsView

        buttonView?.delegate = this


    }

    private var runnable = object : Runnable {
        override fun run() {

            milliSeconds = SystemClock.uptimeMillis()

            total = milliSeconds - startTime - totalPasuedTime

            var temp = (total / 1000).toInt()

            minutes = temp / 60

            seconds = temp % 60

            millis = (total % 1000).toInt()

            text = minutes.toString()

            text += ":"
            text += seconds.toString()

            text += "." + millis.toString()
            clockView?.setClock(text)
            handler!!.post(this)

        }
    }

    override fun start( isFirstTime: Boolean) {

        if (isFirstTime)
            startTime = SystemClock.uptimeMillis()
        else {
            val currentTime = SystemClock.uptimeMillis()
            totalPasuedTime += ( currentTime - timePaused)
        }

        if (handler == null)
            handler = Handler()
        handler!!.post(runnable)

    }

    override fun pause() {
        handler!!.removeCallbacks(runnable)
        handler = null
        timePaused = SystemClock.uptimeMillis()
        paused = true
    }

    override fun lap() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reset() {
        handler?.removeCallbacks(runnable)
        handler = null
        paused = false
        total = 0
        timePaused = 0
        totalPasuedTime = 0
        clockView?.setClock("00:00.000")

    }

    override fun stop() {
        handler?.removeCallbacks(runnable)
        ticks = 0
        handler = null
    }
}
