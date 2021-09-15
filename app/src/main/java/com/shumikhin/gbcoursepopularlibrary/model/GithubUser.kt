package com.shumikhin.gbcoursepopularlibrary.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Аннотация @Parcelize говорит о
//генерировании всего boilerplate-кода, который требуется для работы Parcelable, что автоматически
//избавляет от написания его вручную.
@Parcelize
data class GithubUser(val login: String) : Parcelable

