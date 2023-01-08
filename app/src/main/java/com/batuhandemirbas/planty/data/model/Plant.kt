package com.batuhandemirbas.planty.data.model

data class Plant(
    val name: String? = null,
    val photo: String? = null,
    val date: String? = null,
    val humidity: String? = null,
    val info: String? = null,
    val light: String? = null,
    val temperature: String? = null,
    val type: String? = null,
    val water: Water? = null
)

data class Water(
    val amount: String? = null,
    val frequency: String? = null,
    val humidity: String? = null
)

