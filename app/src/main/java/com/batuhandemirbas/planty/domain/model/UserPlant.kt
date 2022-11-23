package com.batuhandemirbas.planty.domain.model

data class UserPlant(
    val humidity: Int? = null,

    val image: String? = null,

    val name: String? = null,

    val plant: String? = null,

    val temperature: Int? = null,

    val type: String? = null,

    val moisture: ArrayList<String>? = null,

    val waterLevel: ArrayList<String>? = null

)
