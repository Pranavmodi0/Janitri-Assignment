package com.only.janitriassignment.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.only.janitriassignment.data.datasource.ColorDao
import com.only.janitriassignment.data.entity.ColorEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ColorRepository @Inject constructor(
    private val colorDao: ColorDao,
    private val firestore: FirebaseFirestore
) {
    val colors: Flow<List<ColorEntity>> = colorDao.getAllColors()

    suspend fun addColor(color: ColorEntity) {
        colorDao.insertColor(color)
    }

    suspend fun syncColors() {
        val unsyncedColors = colorDao.getUnsyncedColors()

        val batch = firestore.batch()

        unsyncedColors.forEach { color ->
            try {
                val colorDocument = firestore.collection("colors").document()

                batch.set(colorDocument, mapOf(
                    "color" to color.color,
                    "time" to color.time,
                    "isSynced" to true
                ))
                colorDao.updateColor(color.copy(isSynced = true))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        try {
            batch.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
