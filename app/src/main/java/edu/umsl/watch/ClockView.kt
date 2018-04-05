package edu.umsl.watch

import android.app.Fragment
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

val TEXT = "TEXT"

class ClockView : Fragment() {


    private var clock: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_clock_view, container, false)
        clock = view?.findViewById(R.id.clockView)

        if (savedInstanceState != null){
            clock?.text = savedInstanceState?.getString(TEXT)
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(TEXT, clock?.text?.toString())
    }

    fun setClock(tick: String){
        clock?.text = tick
    }

}
