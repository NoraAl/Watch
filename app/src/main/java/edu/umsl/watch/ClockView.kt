package edu.umsl.watch

import android.app.Fragment
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import org.json.JSONArray

class ClockView : Fragment() {


    private var clock: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_clock_view, container, false)
        clock = view?.findViewById(R.id.clockView)

        clock?.setOnClickListener {
            clock?.text = "clicked"
            //delegate?.fetchCarData()
        }

        return view
    }

    fun setClock(tick: String){
        clock?.text = tick//.toString()
    }

}
