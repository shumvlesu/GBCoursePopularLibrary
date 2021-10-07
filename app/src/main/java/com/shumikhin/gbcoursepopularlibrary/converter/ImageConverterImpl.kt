package com.shumikhin.gbcoursepopularlibrary.converter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import io.reactivex.Single
import java.io.File

class ImageConverterImpl(private val context: Context) : ImageConverter {
    companion object {
        /**
         * DEF_QUALITY - Степень сжатия. Для PNG практически бесполезна,
         * но для JPEG очень хорошо сжимает
         */
        private const val DEF_QUALITY = 100

        /**
         * DEF_DELAY - Задержка, для симуляции долгого процесса,
         * иначе конвертирование происходит очень быстро. Хотя может я просто маленькие файлы выбирал.
         */
        private const val DEF_DELAY = 3000L
        private const val ERROR_CONVERT = "При конвертировании произошла ошибка."
    }

    override fun jpegToPng(uriTargetImage: Uri, toFile: File): Single<Uri> =
        Single.create { emitter ->
            try {
                Thread.sleep(DEF_DELAY)
                val bitmap =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uriTargetImage)
                val result =
                    bitmap.compress(Bitmap.CompressFormat.PNG, DEF_QUALITY, toFile.outputStream())
                if (!emitter.isDisposed) {
                    if (result) {
                        emitter.onSuccess(Uri.fromFile(toFile))
                    } else {
                        emitter.onError(Exception(ERROR_CONVERT))
                    }
                }
            } catch (e: Throwable) {
                if (!emitter.isDisposed) {
                    emitter.onError(e)
                }
            }
        }
}