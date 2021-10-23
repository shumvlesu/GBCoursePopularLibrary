package com.shumikhin.gbcoursepopularlibrary.view.ui.detailsuser

interface IListPresenter<V: IRepoItemView> {
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}

interface IRepoListPresenter: IListPresenter<RepoItemView>