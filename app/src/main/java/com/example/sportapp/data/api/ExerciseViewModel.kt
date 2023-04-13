package com.example.sportapp.data.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseViewModel: ViewModel() {

    private val exerciseRepo: ExerciseRepo = ExerciseRepo()

    private val _state = MutableStateFlow(emptyList<Exercise>())
    val state: StateFlow<List<Exercise>>
        get() = _state

    init {
        viewModelScope.launch {
            val exercises = exerciseRepo.getExercises("")
            _state.value = exercises
        }
    }

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val exercises = searchText
        .debounce(500L)
        .distinctUntilChanged()
        .filter { it.length >= 3 }
        .mapLatest { query ->
            _isSearching.update { true }
            delay(1000L)
            exerciseRepo.getExercises(query).onEach {
                _isSearching.update { false }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        viewModelScope.launch {
            val exercises = exerciseRepo.getExercises(text)
            _state.value = exercises
        }
    }

}
