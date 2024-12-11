package net.rafgpereira.transpoapp.data.network

import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject

inline fun <reified T> ResponseBody.getJsonObject(): T {
    val gson = Gson()
    val jsonObject = JSONObject(charStream().readText())
    return gson.fromJson(jsonObject.toString(), T::class.java)
}