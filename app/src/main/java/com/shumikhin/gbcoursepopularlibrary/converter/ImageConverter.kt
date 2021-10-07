package com.shumikhin.gbcoursepopularlibrary.converter

import android.net.Uri
import io.reactivex.Single
import java.io.File

interface ImageConverter {
    //uriTargetImage Uri исходного изображения
    //toFile файл, в который записывается конвертированная картинка
    //Uri результирующего изображения
    fun jpegToPng(uriTargetImage: Uri, toFile: File): Single<Uri>
}