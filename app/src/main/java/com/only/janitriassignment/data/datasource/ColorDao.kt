package com.only.janitriassignment.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.only.janitriassignment.data.entity.ColorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: ColorEntity)

    @Query("SELECT * FROM colors WHERE isSynced = 0")
    suspend fun getUnsyncedColors(): List<ColorEntity>

    @Query("SELECT * FROM colors ORDER BY time DESC")
    fun getAllColors(): Flow<List<ColorEntity>>

    @Update
    suspend fun updateColor(color: ColorEntity)

    @Update
    suspend fun updateColors(colors: List<ColorEntity>)
}
