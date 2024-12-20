package com.only.janitriassignment.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ColorItem(
    val color: String = "",
    val time: Long = 0L,
    var isSynced: Boolean = false
)

@Entity(tableName = "colors")
data class ColorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val color: String = "",
    val time: Long = 0L,
    val isSynced: Boolean = false
)
