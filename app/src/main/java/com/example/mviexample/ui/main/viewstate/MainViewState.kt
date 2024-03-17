package com.example.mviexample.ui.main.viewstate

import com.example.mviexample.data.model.FakeDTO

sealed class MainViewState {

    data object Idle : MainViewState()
    data object Loading : MainViewState()
    class Error(val message: String) : MainViewState()
    class Success(val data: List<FakeDTO>) : MainViewState()
}