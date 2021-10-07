package com.shumikhin.gbcoursepopularlibrary.converter

import android.content.Context

object ImageConverterFactory {
    fun create(context: Context): ImageConverter = ImageConverterImpl(context)
}