package com.shumikhin.gbcoursepopularlibrary.view.ui.detailsuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shumikhin.gbcoursepopularlibrary.databinding.ItemRepoBinding
import com.shumikhin.gbcoursepopularlibrary.presentation.IListPresenter
import com.shumikhin.gbcoursepopularlibrary.presentation.IUserListPresenter

class ReposRVAdapter (val presenter: IRepoListPresenter): RecyclerView.Adapter<ReposRVAdapter.ViewHolderRepo>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRepo {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolderRepo(binding)
        viewHolder.itemView.setOnClickListener { presenter.itemClickListener?.invoke(viewHolder)}
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolderRepo, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    override fun getItemCount(): Int {
        return presenter.getCount()
    }

    inner class ViewHolderRepo(private val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root),
        RepoItemView {
        override var pos = -1
        override fun setRepoName(text: String) {
            binding.repoName.text = text
        }
    }
}