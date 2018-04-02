package edu.umsl.watch


import android.os.Bundle
//import android.support.v4.app.Fragment
import android.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class ButtonsView : Fragment() {


    var startButton: Button? = null
    var lapButton: Button? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_buttons_view, container, false)

        startButton = view?.findViewById(R.id.startButton)
        lapButton = view?.findViewById(R.id.lapButton)

        startButton?.setOnClickListener {
            Log.e("FRAGMENT", "Clicked the button!")
            //delegate?.fetchCarData()
        }

        return view
    }

}// Required empty public constructor
