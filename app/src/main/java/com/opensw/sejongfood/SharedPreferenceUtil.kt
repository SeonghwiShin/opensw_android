package com.opensw.sejongfood

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferenceUtil(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("PlaceDataPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private fun savePlaceDataList(placeDataList: List<PlaceData>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(placeDataList)
        editor.putString("PlaceDataList", json)
        editor.apply()
    }

    fun getPlaceDataList(): List<PlaceData> {
        val json = sharedPreferences.getString("PlaceDataList", null) ?: return emptyList()
        val type = object : TypeToken<List<PlaceData>>() {}.type
        return gson.fromJson(json, type)
    }

    fun addPlaceData(placeData: PlaceData) {
        val placeDataList = getPlaceDataList().toMutableList()
        placeDataList.add(placeData)
        savePlaceDataList(placeDataList)
    }

    fun removePlaceData(index: Int) {
        val placeDataList = getPlaceDataList().toMutableList()
        val iterator = placeDataList.iterator()
        while (iterator.hasNext()) {
            val placeData = iterator.next()
            if (placeData.index == index) {
                iterator.remove()
                break
            }
        }
        savePlaceDataList(placeDataList)
    }

    fun isPlaceDataIndexExists(index: Int): Boolean {
        val placeDataList = getPlaceDataList()
        return placeDataList.any { it.index == index }
    }
}
