package com.shumikhin.gbcoursepopularlibrary.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// Аннотация @Parcelize говорит о
//генерировании всего boilerplate-кода, который требуется для работы Parcelable, что автоматически
//избавляет от написания его вручную.
@Parcelize
data class GithubUser(

    // @Expose - для сериализации GSONa
    @Expose
    val id: String? = null,

    @Expose
    val login: String? = null,
    //@SerializedName - если не хотим называть переменную аналогично полю что прилетит в json теле
    //avatar_url - это имя поля в теле json.
    // Благодаря @SerializedName программа поймет что avatar_url это avatarUrl и в обратную сторону
    @Expose
    @SerializedName("avatar_url") val avatarUrl: String? = null

) : Parcelable

