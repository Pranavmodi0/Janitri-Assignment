package com.only.janitriassignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.only.janitriassignment.data.entity.ColorEntity
import com.only.janitriassignment.data.repository.ColorRepository
import com.only.janitriassignment.domain.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorViewModel @Inject constructor(
    private val repository: ColorRepository,
    private val useCases: UseCases
) : ViewModel() {

    val colors: StateFlow<List<ColorEntity>> = repository.colors.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _unsyncedCount = MutableStateFlow(0)
    val unsyncedCount: StateFlow<Int> get() = _unsyncedCount

    private val _syncStatus = MutableStateFlow(false)
    val syncStatus: StateFlow<Boolean> = _syncStatus

    init {
        updateUnsyncedCount()
    }

    fun addColor() {
        viewModelScope.launch {
            val randomColor = String.format("#%06X", (0xFFFFFF and (Math.random() * 0xFFFFFF).toInt()))
            val timestamp = System.currentTimeMillis()
            val newColor = ColorEntity(color = randomColor, time = timestamp)
            useCases.addColor(newColor)
            updateUnsyncedCount()
        }
    }

    fun syncColors() {
        viewModelScope.launch {
            useCases.syncColors()
            updateUnsyncedCount()
            _syncStatus.emit(true)
        }
    }

    private fun updateUnsyncedCount() {
        viewModelScope.launch {
            val count = repository.colors.first().count { !it.isSynced }
            _unsyncedCount.value = count
        }
    }
}
