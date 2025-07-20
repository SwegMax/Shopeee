package com.example.shopeee

import android.app.Application
import android.util.Log
import com.example.shopeee.repository.Constants.FLUTTER_SEARCH_ENGINE_ID
import dagger.hilt.android.HiltAndroidApp
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.plugin.common.MethodChannel

@HiltAndroidApp
class ShopeeeApplication : Application() {

    //Flutter extension
    private lateinit var flutterEngine: FlutterEngine
    private val flutterSearchEngineId = FLUTTER_SEARCH_ENGINE_ID

    override fun onCreate() {
        super.onCreate()

        flutterEngine = FlutterEngine(this)

        GeneratedPluginRegistrant.registerWith(flutterEngine)

        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint("flutter_assets", "searchEntryPoint")
        )

        FlutterEngineCache.getInstance().put(flutterSearchEngineId, flutterEngine)

        //initialise flutterEngine here (done)
        //Method Channels put in Fragment (done)
        //Method logic put in ViewModel (done)
        //testing
        //commit and push first
        //then viewModelScope optimization: see Medium Article

        //performance, payment (1st)
    }

}