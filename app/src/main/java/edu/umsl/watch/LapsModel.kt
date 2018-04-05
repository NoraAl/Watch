package edu.umsl.watch


class LapsModel{
    private var laps: ArrayList<String>? = null

    init {
        laps = ArrayList(0)
    }

    fun getlapsCount(): Int{

        return laps?.size!!
    }

    fun getLapList(): ArrayList<String>?{
        return laps
    }

    fun addLap(lap: String){
        laps?.add(lap)
    }

    fun getLapAt(index: Int): String?{
        return laps?.get(index)
    }

    operator fun get(position: Int): Any {
        return laps?.get(position) as Any
    }


}
