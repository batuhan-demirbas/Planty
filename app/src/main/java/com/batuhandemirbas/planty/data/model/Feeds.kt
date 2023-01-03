package com.batuhandemirbas.planty.data.model

data class Feeds(
    val feeds : ArrayList<PlantData>,
)

data class PlantData (

    val created_at : String,
    val entry_id : String,
    val field1 : String,
    val field2 : String,
    val field3 : String,
    val field4 : String,
    val field5 : String
)
