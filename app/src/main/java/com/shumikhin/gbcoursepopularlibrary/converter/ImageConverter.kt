package com.shumikhin.gbcoursepopularlibrary.converter

import android.net.Uri
import io.reactivex.Single
import java.io.File


interface ImageConverter {

    /**
     * @param uriTargetImage Uri исходного изображения
     * @param toFile файл, в который записывается конвертированная картинка
     * @return Uri результирующего изображения
     */
    fun jpegToPng(uriTargetImage: Uri, toFile: File): Single<Uri>
}