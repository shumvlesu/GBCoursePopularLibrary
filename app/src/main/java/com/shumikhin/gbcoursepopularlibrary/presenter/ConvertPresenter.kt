package com.shumikhin.gbcoursepopularlibrary.presenter

import android.net.Uri
import android.os.Environment
import com.shumikhin.gbcoursepopularlibrary.converter.ImageConverter
import com.shumikhin.gbcoursepopularlibrary.scheduler.Schedulers
import com.shumikhin.gbcoursepopularlibrary.view.ConvertView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

import moxy.MvpPresenter

import java.io.File

class ConvertPresenter(
    private val imageConverter: ImageConverter,
    private val schedulers: Schedulers
) : MvpPresenter<ConvertView>() {

    companion object {
        private const val DEF_FILE_NAME = "saved.png"
        private const val IMAGE_NOT_SELECTED = "Не выбран рисунок"
        private const val SUCCESS_CONVERT = "Изображение успешно сконвертировано в PNG формат"
        private const val CANCEL_CONVERT = "Отмена конвертирования"
    }

    private var uriSelected: Uri? = null
    private val disposables = CompositeDisposable()

    /**
     * Выбрать изображение
     */
    fun selectImage() {
        viewState.chooseImage()
    }

    /**
     * Изображение выбрано
     */
    fun onImageSelected(targetUri: Uri) {
        uriSelected = targetUri
        viewState.showSelectedImage(targetUri)
        viewState.useConvertButtons(true)
    }

    override fun onDestroy() = disposables.clear()

    /**
     * Отменить конвертирование, удалить подписки
     */
    fun convertStop() {
        with(viewState) {
            showSelectedImage(null)
            showResultImage(null)
            useConvertButtons(false)
            showLoading(false)
            showMessage(CANCEL_CONVERT)
        }
        uriSelected = null
        disposables.clear()
    }

    /**
     * Начать конвертирование
     */
    fun convertStart() {
        viewState.showLoading(true)
        val toSaveFile = File(Environment.getExternalStorageDirectory().toString(), DEF_FILE_NAME)

        when (uriSelected) {
            null -> {
                viewState.showLoading(false)
                viewState.showMessage(IMAGE_NOT_SELECTED)
            }
            else -> {
                uriSelected?.let { uri ->
                    imageConverter
                        .jpegToPng(uri, toSaveFile)
                        .observeOn(schedulers.main())
                        .subscribeOn(schedulers.computation())
                        .subscribe(
                            {
                                with(viewState) {
                                    showResultImage(it)
                                    showLoading(false)
                                    showMessage(SUCCESS_CONVERT)
                                }
                            },
                            {
                                with(viewState) {
                                    showLoading(false)
                                    showMessage(it.message.toString())
                                    useConvertButtons(false)
                                    showSelectedImage(null)
                                    showResultImage(null)
                                }
                                uriSelected = null
                            }
                        ).addTo(disposables)
                }
            }
        }
    }
}