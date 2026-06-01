package com.chainnet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chainnet.ui.ChainNetRoot
import com.chainnet.ui.theme.ChainNetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChainNetTheme {
                ChainNetRoot()
            }
        }
    }
}
