package com.example.myassssmentapplication

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Entity(
    @SerializedName("ticker")
    val property1: String,

    @SerializedName("assetType")
    val property2: String,

    @SerializedName("description")
    val description: String
) : Serializable
