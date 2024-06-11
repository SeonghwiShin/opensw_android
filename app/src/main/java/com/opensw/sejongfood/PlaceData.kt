package com.opensw.sejongfood

data class PlaceData(
    val index: Int = 0,
    val review: MutableList<Review> = mutableListOf(),
    val reviewCount: Int = 0,
    val rating: Float = 0.0f,
    val title: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)

data class Review(
    val rating: Float = 0.0f,
    val contents: String = "",
    val recommendMenu: String = "",
)
