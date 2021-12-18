package com.bimboilya.navsample.voyager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bimboilya.common.ui.theme.PogTheme

class VoyagerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PogTheme {
                Surface {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Text(text = "Voyager")
                    }
                }
            }
        }
    }
}