package com.th.android.replace_instrumentation

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.th.android.replace_instrumentation.ui.theme.ReplaceinstrumentationTheme
import dalvik.system.DexFile

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dexFile = DexFile(packageCodePath)
        val entries = dexFile.entries()
        while (entries.hasMoreElements()) {
            val className = entries.nextElement()
            if (className.contains("SplashScreen")) {
                Log.i("class-debug1", className)
            }
        }

        enableEdgeToEdge()
        setContent {
            ReplaceinstrumentationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Button(onClick = {
                            startActivity(Intent(this@MainActivity, MainActivity1::class.java))
                        }) {
                            Text(text = "打开Activity1")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReplaceinstrumentationTheme {
        Greeting("Android")
    }
}
