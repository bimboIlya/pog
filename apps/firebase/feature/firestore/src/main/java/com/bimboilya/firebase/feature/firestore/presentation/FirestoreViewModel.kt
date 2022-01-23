package com.bimboilya.firebase.feature.firestore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimboilya.common.ktx.jvm.combineState
import com.bimboilya.firebase.feature.firestore.data.FirestoreDataSource
import com.bimboilya.firebase.feature.firestore.data.Test
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    private val dataSource: FirestoreDataSource,
) : ViewModel() {

    private val loading = MutableStateFlow(State.Initial.loading)
    private val data = MutableStateFlow(State.Initial.data)
    val state = viewModelScope.combineState(loading, data, ::State)

    fun load() {
        viewModelScope.launch {
            loading.emit(true)
            data.emit(dataSource.get())
            loading.emit(false)
        }
    }

    fun addRandom() {
        viewModelScope.launch {
            loading.emit(true)
            dataSource.add(createData())
            loading.emit(false)
        }
    }

    private fun createData(): Test {
        val random = Random(System.currentTimeMillis())
        val first = random.nextInt().toString()
        val second = if (random.nextBoolean()) {
            random.nextInt().toString()
        } else null

        return Test(first, second)
    }
}

data class State(
    val loading: Boolean,
    val data: List<Test>,
) {

    companion object {
        val Initial = State(
            loading = false,
            data = emptyList(),
        )
    }
}