package com.example.examengenin.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

fun Any.toJson(): JSONObject {
    val gson = Gson()
    return JSONObject(gson.toJson(this))
}

fun <T> String.toAny(objectClass: Class<T>): Any? {
    val gson = GsonBuilder().create()
    var nObj: Any? = null
    try {
        nObj = gson.fromJson(this, objectClass) as Any
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return nObj
}


// To get a ArrayList from String
inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)