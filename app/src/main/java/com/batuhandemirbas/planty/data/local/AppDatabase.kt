package com.batuhandemirbas.planty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlantsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun plantsDao(): PlantsDao
}