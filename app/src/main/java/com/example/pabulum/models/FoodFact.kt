package com.example.pabulum.models


import com.google.gson.annotations.SerializedName

data class FoodFact(
    @SerializedName("text")
    val text: String
)