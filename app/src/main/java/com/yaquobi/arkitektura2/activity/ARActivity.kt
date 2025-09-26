package com.yaquobi.arkitektura2.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.yaquobi.arkitektura2.screen.ARScreen
import com.yaquobi.arkitektura2.screen.CCISModelScreen
import com.yaquobi.arkitektura2.util.Utils

class ARActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the model name passed from Java Fragment
        val modelKey = intent.getStringExtra("MODEL_KEY") ?: "CCIS"

        setContent {
            val navController = rememberNavController()
            CCISModelScreen(navController = navController, modelKey = modelKey)
        }
    }
}
