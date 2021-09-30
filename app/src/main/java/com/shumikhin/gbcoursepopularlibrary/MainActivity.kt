package com.shumikhin.gbcoursepopularlibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shumikhin.gbcoursepopularlibrary.databinding.ActivityMainBinding
import com.shumikhin.gbcoursepopularlibrary.rx.CreationTeacherExample
import com.shumikhin.gbcoursepopularlibrary.rx.Sources
import moxy.MvpFacade.init

class MainActivity : AppCompatActivity() {
    private var _vb: ActivityMainBinding? = null

    private val vb
        get() = _vb!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        //3 урок комментим
        //CreationTeacherExample().init()

        Sources().init()

    }
}