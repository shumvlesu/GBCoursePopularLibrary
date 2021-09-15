package com.shumikhin.gbcoursepopularlibrary.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shumikhin.gbcoursepopularlibrary.databinding.ItemUserBinding
import com.shumikhin.gbcoursepopularlibrary.presentation.IUserListPresenter
import com.shumikhin.gbcoursepopularlibrary.view.UserItemView


//Таким образом, адаптер не имеет ссылок на данные и полностью делегирует процесс наполнения
//View в Presenter, так как ViewHolder реализует интерфейс UserItemView и передаётся в функцию
//bindView в качестве этого интерфейса.
//Для вызова itemClickListener используется функция invoke(), так как он может быть равен null. А
//также это связано с синтаксическими ограничениями, не позволяющими иначе осуществить вызов
//nullable-значения функционального типа. Эта функция есть у любого значения функционального типа,
//и её вызов вызывает саму функцию, которая и считается этим значением. Проще говоря,
//presenter.itemClickListener?.invoke(holder) вызовет itemClickListener, если он не равен null.
class UsersRVAdapter(val presenter: IUserListPresenter): RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    ).apply {
        itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
    }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = presenter.bindView(holder.apply { pos = position })

    inner class ViewHolder(val vb: ItemUserBinding): RecyclerView.ViewHolder(vb.root), UserItemView {

        override var pos = -1

        override fun setLogin(text: String) = with(vb) {
            tvLogin.text = text
        }

    }

}
