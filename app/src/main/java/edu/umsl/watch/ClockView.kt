package edu.umsl.watch

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

class ClockView : Fragment() {


    var clock: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_clock_view, container, false)
        clock = view?.findViewById(R.id.clockView)

        clock?.setOnClickListener {
            Log.e("FRAGMENT", "Clicked the button!")
            //delegate?.fetchCarData()
        }

        return view
    }

}
