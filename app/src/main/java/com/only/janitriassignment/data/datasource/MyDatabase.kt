package com.only.janitriassignment.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.only.janitriassignment.data.entity.ColorEntity

@Database(entities = [ColorEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao
}
