package edu.umsl.watch


import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView

import android.widget.TextView


class LapsView : Fragment() {
    private var lapsList: ListView? = null


    //var model: LapsModel? = null

    var laps: ArrayList<String>? = null
    var adapter: LapsAdapter ? = null

    private var textView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_laps_view, container, false)
        lapsList = view?.findViewById<ListView>(R.id.listView)

        laps = ArrayList<String>(  0)

        //adapter = ArrayAdapter(view?.context, android.R.layout.simple_list_item_1, laps)
        adapter = LapsAdapter(view?.context!!, laps!!)
        lapsList?.adapter = adapter

        return view
    }


    fun addItem(item: String){
        adapter?.add(item)
    }

    fun clear(){
        laps = ArrayList<String>(  0)
        adapter?.clear()
    }


    //-----------

    inner class LapsAdapter : BaseAdapter {

        private var lapsList : ArrayList<String>? = null
        private var context: Context? = null

        constructor(context: Context, notesList: ArrayList<String>) : super() {
            this.context = context
            this.lapsList = notesList
        }

        override fun getItem(position: Int): String? {
            return lapsList?.get(position)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val holder: LapsHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.layout, parent, false)
                holder = LapsHolder(view)
                view.tag = holder

            } else {
                view = convertView
                holder = view.tag as LapsHolder
            }

            holder.idView.text = "Lap "+(position+1)
            holder.timeView.text = lapsList?.get(position)

            return view
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return lapsList?.size ?: 0
        }

        fun add (lap: String){
            lapsList?.add(lap)
            notifyDataSetChanged()
        }

        fun clear(){
            lapsList?.clear()
            notifyDataSetChanged()
        }
    }

    private class LapsHolder(view: View?) {
        val idView: TextView = view?.findViewById(R.id.lapsId) as TextView
        val timeView: TextView = view?.findViewById(R.id.timeString) as TextView

    }
}
