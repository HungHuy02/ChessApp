package com.huy.chess

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.bitmapConfig
import coil3.request.crossfade
import com.huy.chess.data.network.RetrofitClient
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChessApplication : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        RetrofitClient.createClient(this)
    }

    override fun newImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .crossfade(true)
            .build()
    }
}
