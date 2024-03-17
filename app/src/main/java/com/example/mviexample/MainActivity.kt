package com.example.mviexample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mviexample.ui.main.intent.MainIntent
import com.example.mviexample.ui.main.viewmodel.PostViewmodel
import com.example.mviexample.ui.main.viewstate.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: PostViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        lifecycleScope.launch {
             viewModel.mainIntent.send(MainIntent.GetPosts)
        }

    }

    private fun initView() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                when (it) {
                    is MainViewState.Error -> {
                        println("GetPost : Error ${it.message}")
                    }

                    MainViewState.Idle -> {
                        println("GetPost : Idle")

                    }

                    MainViewState.Loading -> {
                        println("GetPost : Loading")
                    }

                    is MainViewState.Success -> {
                        println("GetPost : Success ${it.data.size}")
                    }
                }
            }
        }
    }
}
