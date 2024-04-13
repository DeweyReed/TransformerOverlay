@file:UnstableApi

package com.github.deweyreed.transformeroverlay

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.effect.BitmapOverlay
import androidx.media3.effect.OverlayEffect
import androidx.media3.effect.Presentation
import androidx.media3.transformer.Composition
import androidx.media3.transformer.EditedMediaItem
import androidx.media3.transformer.Effects
import androidx.media3.transformer.ExportException
import androidx.media3.transformer.ExportResult
import androidx.media3.transformer.Transformer
import com.google.common.collect.ImmutableList
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn).setOnClickListener {
            Transformer.Builder(this)
                .addListener(
                    object : Transformer.Listener {
                        override fun onCompleted(
                            composition: Composition,
                            exportResult: ExportResult
                        ) {
                            super.onCompleted(composition, exportResult)
                            Toast.makeText(this@MainActivity, "onCompleted", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onError(
                            composition: Composition,
                            exportResult: ExportResult,
                            exportException: ExportException
                        ) {
                            super.onError(composition, exportResult, exportException)
                            Toast.makeText(
                                this@MainActivity,
                                exportException.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
                .build()
                .start(
                    EditedMediaItem.Builder(
                        MediaItem.Builder()
                            .setUri(
                                Uri.Builder()
                                    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                                    .authority(packageName)
                                    .path(R.raw.video.toString())
                                    .build()
                            )
                            .build()
                    ).setEffects(
                        Effects(
                            emptyList(),
                            buildList {
                                val bitmap = checkNotNull(
                                    AppCompatResources.getDrawable(
                                        this@MainActivity,
                                        R.drawable.lut
                                    )
                                ).toBitmap()
                                this += Presentation.createForWidthAndHeight(
                                    bitmap.width,
                                    bitmap.height,
                                    Presentation.LAYOUT_SCALE_TO_FIT
                                )
                                this += OverlayEffect(
                                    ImmutableList.of(
                                        BitmapOverlay.createStaticBitmapOverlay(bitmap)
                                    )
                                )
                            },
                        )
                    ).build(),
                    File(filesDir, "output.mp4").canonicalPath
                )
        }
    }
}
