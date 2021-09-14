package com.shumikhin.gbcoursepopularlibrary

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shumikhin.gbcoursepopularlibrary.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

//class MainActivity : AppCompatActivity(), MainView {
//Заменяем AppCompatActivity(), подключая активити к мокси MvpAppCompatActivity()
class MainActivity : MvpAppCompatActivity(), MainView {

    private var vb: ActivityMainBinding? = null

    //Подключаем презентр в активити к мокси тоже
    //val presenter = MainPresenter(this)
    //Теперь при перестроении активити (повороте экрана например) презентер снова присоеденится к активити или фрагменту
    private val presenter by moxyPresenter {
        MainPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)

//Как Вариант с индексом
//        vb?.btnCounter1?.setOnClickListener{presenter.counterClick(0)}
//        vb?.btnCounter2?.setOnClickListener{presenter.counterClick(1)}
//        vb?.btnCounter3?.setOnClickListener{presenter.counterClick(2)}

        vb?.btnCounter1?.setOnClickListener {presenter.counterClick0()}
        vb?.btnCounter2?.setOnClickListener {presenter.counterClick1()}
        vb?.btnCounter3?.setOnClickListener {presenter.counterClick2()}

    }

    //Как Вариант с индексом
    override fun setButtonText(index: Int, text: String) {
        when(index){
            0 -> vb?.btnCounter1?.text = text
            1 -> vb?.btnCounter2?.text = text
            2 -> vb?.btnCounter3?.text = text
        }

    }

    override fun setButtonTextC0(text: String) {
        vb?.btnCounter1?.text = text
    }

    override fun setButtonTextC1(text: String) {
        vb?.btnCounter2?.text = text
    }

    override fun setButtonTextC2(text: String) {
        vb?.btnCounter3?.text = text
    }

}