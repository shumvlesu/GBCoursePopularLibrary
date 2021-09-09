package com.shumikhin.gbcoursepopularlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shumikhin.gbcoursepopularlibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), GreetingView {
    private val presenter = Presenter(Model())
    private var vb: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vb?.button?.setOnClickListener { presenter.buttonClick() }
    }

    override fun setGreeting(greeting: String){
        vb?.tvGreeting?.text?: = greeting
    }

}