package edu.umsl.watch

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock

val START_TIME = "START_TIME"
val TOTAL = "TOTAL"
val PAUSED_TIME = "PAUSED_TIME"
val LAPS_START_TIME = "LAPS_START_TIME"
val PAUSED_FLAG = "PAUSED_FLAG"
val TIME_PAUSED = "TIME_PAUSED"


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
    private var currentLap: Int = 0
    private var lapStartTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)


        if (savedInstanceState != null) {
            startTime = savedInstanceState.getLong(START_TIME)
            totalPasuedTime = savedInstanceState.getLong(PAUSED_TIME)
            lapStartTime = savedInstanceState.getLong(LAPS_START_TIME)
            total = savedInstanceState.getLong(TOTAL)
            pausedFlag = savedInstanceState?.getBoolean(PAUSED_FLAG)
            timePaused = savedInstanceState?.getLong(TIME_PAUSED)

            clockView = fragmentManager.findFragmentById(R.id.clockFragment) as ClockView
            lapsView = fragmentManager.findFragmentById(R.id.lapsFragment) as LapsView
            buttonView = fragmentManager.findFragmentById(R.id.buttonsFragment) as ButtonsView
            buttonView?.delegate = this

            buttonView?.savedState()
            handler = Handler()

        } else {
            clockView = fragmentManager.findFragmentById(R.id.clockFragment) as ClockView
            lapsView = fragmentManager.findFragmentById(R.id.lapsFragment) as LapsView
            buttonView = fragmentManager.findFragmentById(R.id.buttonsFragment) as ButtonsView
            buttonView?.delegate = this
        }


    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        handler?.removeCallbacks(runnable)
        handler = null
        outState?.putLong(START_TIME, startTime)
        outState?.putLong(TOTAL, total)
        outState?.putLong(PAUSED_TIME, totalPasuedTime)
        outState?.putLong(LAPS_START_TIME, lapStartTime)
        outState?.putBoolean(PAUSED_FLAG, pausedFlag)
        outState?.putLong(TIME_PAUSED, timePaused)
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

            text += ":" + seconds.toString()
            text += "." + millis.toString()

            clockView?.setClock(text)
            handler!!.post(this)
        }
    }

    override fun start(isFirstTime: Boolean) {
        if (isFirstTime)
            startTime = SystemClock.uptimeMillis()
        else {
            val currentTime = SystemClock.uptimeMillis()
            if (timePaused > 0) // not from rotation
                totalPasuedTime += (currentTime - timePaused)
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
        if (lapStartTime.toInt() == 0) {// first time lap
            lapStartTime = startTime
        }
        currentLap = (SystemClock.uptimeMillis() - lapStartTime).toInt()


        var temp = (currentLap / 1000).toInt()
        val minutes = temp / 60
        val seconds = temp % 60
        val millis = (currentLap % 1000).toInt()

        var text = minutes.toString()

        text += ":" + seconds.toString()
        text += "." + millis.toString()

        lapsView?.addItem(text)

        lapStartTime = SystemClock.uptimeMillis()

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
