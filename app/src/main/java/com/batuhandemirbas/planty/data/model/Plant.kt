package com.batuhandemirbas.planty.domain.model

data class Plant(
    val date: String? = null,
    val humidity: String? = null,
    val info: String? = null,
    val light: String? = null,
    val temperature: String? = null,
    val types: ArrayList<String>? = null,
    val water: Water? = null
)

data class Water(
    val amount: String? = null,
    val frequency: String? = null,
    val humidity: String? = null
)

