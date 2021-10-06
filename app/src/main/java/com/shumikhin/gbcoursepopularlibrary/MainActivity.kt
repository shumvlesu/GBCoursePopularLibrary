package com.shumikhin.gbcoursepopularlibrary

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.shumikhin.gbcoursepopularlibrary.databinding.ActivityMainBinding
import com.shumikhin.gbcoursepopularlibrary.rx.BackPressure
import com.shumikhin.gbcoursepopularlibrary.rx.Multithreading
import com.shumikhin.gbcoursepopularlibrary.rx.NetworkStatus
import com.shumikhin.gbcoursepopularlibrary.rx.Sources


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

        //Уже перегружен класс для многопоточности создал новый
        //Sources().init()

        //Пример с многопоточностью
        //Multithreading().init()

        //Пример использования Flowable
        BackPressure().execute()

        var statusText = ""
        NetworkStatus(this).status().subscribe(){
            statusText = "Доступна ли сеть: $it"
        }
        Toast.makeText(applicationContext, statusText, Toast.LENGTH_LONG).show()
        vb.textAndroid.text = statusText



    }
}