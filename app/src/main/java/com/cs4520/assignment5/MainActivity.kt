package com.cs4520.assignment5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as ProductDBApp).appContainer
        application?.applicationContext?.let {
            appContainer.createCacheDatabase(it)
        }
        application?.applicationContext?.let {
            appContainer.createRepository(it)
        }
        setContent {
            AppNavigator()
        }
    }
}
