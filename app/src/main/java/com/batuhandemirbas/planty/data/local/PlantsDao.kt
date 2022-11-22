package com.batuhandemirbas.planty.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantsDao {
    @Query("SELECT * FROM plants_table")
    fun getAll(): List<PlantsEntity>

    @Query("SELECT * FROM plants_table WHERE type IN (:plantType)")
    fun loadAllByIds(plantType: String): List<PlantsEntity>

    @Query("SELECT type FROM plants_table")
    fun loadAllPlantsName(): Array<String>

    @Query("SELECT * FROM plants_table WHERE type")
    fun loadAllPlantsSlug(): Array<PlantsEntity>

    @Query("SELECT * FROM plants_table WHERE type LIKE :plant AND " +
            "light LIKE :light LIMIT 1")
    fun findByName(plant: String, light: String): PlantsEntity

    @Query("SELECT COUNT(type) FROM plants_table")
    fun getDataCount(): Int

    @Insert
    fun insert( plants: PlantsEntity)

    @Insert
    fun insertAll(vararg cities: PlantsEntity)

    @Delete
    fun delete(plant: PlantsEntity)

}
