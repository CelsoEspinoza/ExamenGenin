package com.example.examengenin.data.entity

import com.google.gson.annotations.SerializedName

data class Task(
        @SerializedName("id")
        val id: Int = -1,
        @SerializedName("title")
        val title: String = ""
)