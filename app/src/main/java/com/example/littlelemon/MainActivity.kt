package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.example.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    private val tipMenuLiveDatafield = MutableLiveData<Boolean>()
    private val sharedPreferences by lazy { getSharedPreferences("LittleLemon", MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var tipMenuLiveDatavalue = sharedPreferences.getBoolean("Tip", false)
            LittleLemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text(text = "Add Tip?")
                        tipMenuLiveDatavalue = tipMenuLiveDatafield.observeAsState(tipMenuLiveDatavalue).value
                        var tipEnabled by remember { mutableStateOf(false) }
                        Switch(
                            checked = tipEnabled,
                            onCheckedChange = { newSwitchState ->
                                tipEnabled = newSwitchState
                                // Save the switch state to shared preferences
                                sharedPreferences.edit {
                                    putBoolean("Tip", tipEnabled)
                                }
                                runOnUiThread {
                                    tipMenuLiveDatafield.value = tipEnabled
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

