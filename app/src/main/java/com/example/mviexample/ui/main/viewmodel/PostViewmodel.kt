package com.example.mviexample.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.data.repository.GetPostRepository
import com.example.mviexample.ui.main.intent.MainIntent
import com.example.mviexample.ui.main.viewstate.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewmodel @Inject constructor(private val getPostRepository: GetPostRepository) :
    ViewModel() {

    val mainIntent: Channel<MainIntent> = Channel(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState> get() = _state

    init {
        handleIntent()
    }

    fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect {
                when (it) {
                    MainIntent.GetPosts -> fetchPosts()
                }
            }
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _state.value = MainViewState.Loading
            try {
                val data = getPostRepository.getposts()
                _state.value = MainViewState.Success(data = data)
            } catch (e: Exception) {
                _state.value = MainViewState.Error(
                    message = e.localizedMessage?.toString() ?: "Something went wrong"
                )
            }
        }
    }
}