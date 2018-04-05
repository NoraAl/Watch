package edu.umsl.watch

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock


class MainView : Activity(), ButtonsView.Action {
    // three main views
    private var clockView: ClockView? = null
    private var buttonView: ButtonsView? = null
    private var lapsView: LapsView? = null

    private var handler: Handler? = Handler()

    //private var milliSeconds: Long = 0
    private var startTime: Long = 0
    private var totalPasuedTime: Long = 0
    private var total: Long = 0
    private var timePaused: Long = 0
    private var pausedFlag: Boolean = false

    //private var model: LapsModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)

        clockView = fragmentManager.findFragmentById(R.id.clockFragment) as ClockView
        buttonView  = fragmentManager.findFragmentById(R.id.buttonsFragment) as ButtonsView
        lapsView = fragmentManager.findFragmentById(R.id.lapsFragment) as LapsView

        buttonView?.delegate = this

    }

    private var runnable = object : Runnable {
        override fun run() {
            val milliSeconds = SystemClock.uptimeMillis()

            total = milliSeconds - startTime - totalPasuedTime

            var temp = (total / 1000).toInt()
            val minutes = temp / 60
            val seconds = temp % 60
            val millis = (total % 1000).toInt()

            var text = minutes.toString()

            text += ":" +seconds.toString()
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
        pausedFlag = true
    }

    override fun lap() {
        //lapsView?.addItem("pppp")

    }

    override fun reset() {
        handler?.removeCallbacks(runnable)
        handler = null
        pausedFlag = false
        total = 0
        timePaused = 0
        totalPasuedTime = 0
        lapsView?.clear()
        clockView?.setClock("00:00.000")
    }

    override fun stop() {

        handler?.removeCallbacks(runnable)
        handler = null
    }
}
