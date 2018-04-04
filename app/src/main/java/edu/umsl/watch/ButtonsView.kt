package edu.umsl.watch


import android.os.Bundle
import android.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

enum class State{
    NEW,
    TICKING,
    PAUSED,
    RESUMED,
    LAP,
    STOP,
    RESET
}

class ButtonsView : Fragment() {


    var startButton: Button? = null
    var lapButton: Button? = null
    var state = State.NEW
    var delegate: Action? = null


    interface Action{
        fun start(isFirstTime :Boolean)
        fun pause()
        fun lap()
        fun reset()
        fun stop()
    }

    private fun startTimer(isFirstTime: Boolean){
        delegate?.start(isFirstTime)
        state = State.TICKING

    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_buttons_view, container, false)

        startButton = view?.findViewById(R.id.startButton)
        lapButton = view?.findViewById(R.id.lapButton)
        lapButton?.isEnabled = false

        startButton?.setOnClickListener {

            when (state){
                State.NEW -> {
                    startButton?.text = "Pause"
                    lapButton?.isEnabled = true
                    startTimer(true)
                }
                State.TICKING ->{ //ticking but pause is pressed
                    state = State.PAUSED
                    startButton?.text = "Restart"
                    lapButton?.text = "Reset"
                    delegate?.pause()
                }
                State.PAUSED -> {
                    startButton?.text = "Pause"
                    startTimer(false)
                }
            }
        }

        lapButton?.setOnClickListener {
            when (state){
                State.TICKING ->{ //ticking but lap is pressed
                    //TODO: lap
                }
                State.PAUSED -> { // Reset is pressed
                    delegate?.reset()
                    state = State.NEW
                    lapButton?.text = "Lap"
                    lapButton?.isEnabled = false
                    startButton?.text = "Start"


                }
            }
        }

        return view
    }

}
