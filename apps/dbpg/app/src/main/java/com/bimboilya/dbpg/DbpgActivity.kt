package com.bimboilya.dbpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.bimboilya.common.ui.theme.PogTheme
import com.bimboilya.dbpg.ui.DbpgScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DbpgActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PogTheme {
                Surface {
                    DbpgScreen()
                }
            }
        }
    }
}
