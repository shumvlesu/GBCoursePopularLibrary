package com.shumikhin.gbcoursepopularlibrary.view.ui.images


import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class GlideImageLoader : IImageLoader<ImageView> {
    override fun loadInto(url: String, container: ImageView) {
        //https://guides.codepath.com/android/Displaying-Images-with-the-Glide-Library
        val radius = 16
        val margin = 3
        val multi = MultiTransformation<Bitmap>(
            // BlurTransformation(25),
            RoundedCornersTransformation(
                radius,
                margin,
                RoundedCornersTransformation.CornerType.ALL
            )
        )
        Glide.with(container.context)
            .asBitmap()
            .load(url)
            .apply(RequestOptions.bitmapTransform(multi))
            .listener(object : RequestListener<Bitmap?> {
                //Можем обработать ошибку загрузки
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    //Обработка провала загрузки
                    return false
                }
                //А если загрузилась то как то ее дальше обработать
                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    //Делаем что-то с bitmap
                    return false
                }

            })
            .into(container)
    }
}
