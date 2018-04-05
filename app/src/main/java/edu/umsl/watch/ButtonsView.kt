package edu.umsl.watch


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


val STATE = "STATE"

enum class State{
    NEW,
    TICKING,
    PAUSED,
}

class ButtonsView : Fragment() {
    var startButton: Button? = null
    var lapButton: Button? = null
    var state :State ? = null
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


        startButton?.setOnClickListener {

            when (state){
                State.NEW -> {
                    startButton?.text = "Pause"
                    lapButton?.isEnabled = true
                    startTimer(true)
                }
                State.TICKING ->{ //ticking but pause is pressed
                    state = State.PAUSED
                    startButton?.text = "Start"
                    lapButton?.text = "Reset"
                    delegate?.pause()
                }
                State.PAUSED -> {
                    startButton?.text = "Pause"
                    lapButton?.text = "Lap"
                    startTimer(false)
                }
            }
        }

        lapButton?.setOnClickListener {
            when (state){
                State.TICKING ->{ //ticking but lap is pressed
                    delegate?.lap()
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

        if (savedInstanceState == null){
                lapButton?.isEnabled = false
                state  = State.NEW

        } else {
            state = savedInstanceState?.getSerializable(STATE) as State
        }



        return view
    }



    fun savedState(){

        when (state){
            State.TICKING ->{ //ticking but pause is pressed
                startButton?.text = "Pause"
                lapButton?.text = "Lap"
                startTimer(false)
            }
            State.PAUSED -> {
                state = State.PAUSED
                startButton?.text = "Start"
                lapButton?.text = "Reset"
            }
            else -> lapButton?.isEnabled = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(STATE, state)
    }

}
