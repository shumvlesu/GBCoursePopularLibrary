package com.shumikhin.gbcoursepopularlibrary.view.ui.detailsuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shumikhin.gbcoursepopularlibrary.App
import com.shumikhin.gbcoursepopularlibrary.databinding.FragmentUserDetailsBinding
import com.shumikhin.gbcoursepopularlibrary.model.GithubUser
import com.shumikhin.gbcoursepopularlibrary.presentation.detailsuser.UserDetailsPresenter
import com.shumikhin.gbcoursepopularlibrary.view.ui.BackButtonListener
import com.shumikhin.gbcoursepopularlibrary.view.ui.UserDetailsView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserDetailsFragment(private val user: GithubUser = GithubUser("DefaultUserLogin")) :
    MvpAppCompatFragment(),
    BackButtonListener, UserDetailsView {

    private val presenter by moxyPresenter { UserDetailsPresenter(App.instance.router, user) } //?
    private var vb: FragmentUserDetailsBinding? = null

    companion object {
        @JvmStatic
        fun newInstance(user: GithubUser) = UserDetailsFragment(user)
    }

    override fun backPressed() = presenter.backPressed()

    //Указываем имя
    override fun setUserName(name: String) {
        vb?.detailsName?.text = name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentUserDetailsBinding.inflate(inflater, container, false).also {
            vb = it
            vb?.back?.setOnClickListener { backPressed() }
            presenter.setUserData()
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

}