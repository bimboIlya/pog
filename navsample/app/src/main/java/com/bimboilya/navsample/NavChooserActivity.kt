package com.bimboilya.navsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bimboilya.common.ktx.android.startActivity
import com.bimboilya.common.ui.theme.PogTheme
import com.bimboilya.navsample.jetpack.JetpackActivity
import com.bimboilya.navsample.voyager.VoyagerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavChooserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PogTheme {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(onClick = { startActivity<JetpackActivity>() }) {
                        Text(text = "Jetpack Navigation")
                    }
                    Button(onClick = { startActivity<VoyagerActivity>() }) {
                        Text(text = "Voyager")
                    }
                }
            }
        }
    }
}