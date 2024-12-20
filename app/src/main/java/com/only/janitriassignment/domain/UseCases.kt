package com.only.janitriassignment.domain

import com.only.janitriassignment.data.entity.ColorEntity
import com.only.janitriassignment.data.repository.ColorRepository
import javax.inject.Inject

class UseCases @Inject constructor(
    private val repository: ColorRepository
) {
    suspend fun addColor(color: ColorEntity) {
        repository.addColor(color)
    }

    suspend fun syncColors() {
        repository.syncColors()
    }
}