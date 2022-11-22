package com.batuhandemirbas.planty.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "plants_table")
data class PlantsEntity(
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "types") val types: ArrayList<String>?,
    @ColumnInfo(name = "info") val info: String?,
    @ColumnInfo(name = "water") val water: Map<String, Int>?,
    @ColumnInfo(name = "humidity") val humidity: Map<String, Int>?,
    @ColumnInfo(name = "temperature") val temperature: Map<String, Int>?,
    @ColumnInfo(name = "light") val light: String?
)