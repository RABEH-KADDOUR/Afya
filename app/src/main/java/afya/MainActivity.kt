package com.example.afya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.afya.ui.theme.AfyaTheme
import afya.view.MainScreen
import com.example.afya.viewmodel.DrugViewModel
import com.example.afya.viewmodel.PostViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AfyaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val postViewModel = viewModel<PostViewModel>()
                    val drugViewModel = viewModel<DrugViewModel>()

                    MainScreen(
                        postViewModel,
                        drugViewModel
                    )
                }
            }
        }
    }
}