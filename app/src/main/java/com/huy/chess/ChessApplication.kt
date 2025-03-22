package com.huy.chess

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import androidx.compose.ui.res.painterResource
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.bitmapConfig
import coil3.request.crossfade
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChessApplication : Application(), SingletonImageLoader.Factory {

    override fun newImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .crossfade(true)
            .build()
    }
}
