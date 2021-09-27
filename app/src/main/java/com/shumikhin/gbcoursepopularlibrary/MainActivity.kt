package com.shumikhin.gbcoursepopularlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shumikhin.gbcoursepopularlibrary.databinding.ActivityMainBinding
import com.shumikhin.gbcoursepopularlibrary.rx.Creation

class MainActivity : AppCompatActivity() {
    private var _vb: ActivityMainBinding? = null

    private val vb
        get() = _vb!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        Creation().init()
    }
}