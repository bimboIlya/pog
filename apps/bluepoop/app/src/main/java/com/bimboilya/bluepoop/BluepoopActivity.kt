package com.bimboilya.bluepoop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.bimboilya.common.ui.theme.PogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BluepoopActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PogTheme {
                Surface {

                }
            }
        }
    }
}
