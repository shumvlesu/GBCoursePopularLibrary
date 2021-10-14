package com.shumikhin.gbcoursepopularlibrary.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiHolder {

    val apiService: IDataSource by lazy {

        val gson = GsonBuilder()
            //setFieldNamingPolicy говорит как преобразовывать имена json' вских полей в формат полей класса
            //например my_name в myName.
            //Закоментил потому что в GithubUser уже проставлены анатации, делающие нечто похожее
            //.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)

            //Если в GithubUser есть поле переменной которое не имеет аннотацию  @Expose
            //И мы не хотим ее сериализовать то если прилетит поле в json с таким же совпадающим именем поля,
            //то он в переменную которая в GithubUser
            //не запишит случайно свои значения.
            .excludeFieldsWithoutExposeAnnotation()
            .create()

         Retrofit.Builder()
            .baseUrl("https://api.github.com") //Указываем базовый адресс апи
            //RxJava2CallAdapterFactory обеспечивает преобразование
            //вызовов в источники RxJava. Именно он позволяет возвращать Single результата вызова.
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            //GsonConverterFactory обеспечивает преобразования ответа сервера из формата json в готовые
            //объекты, в нашем случае — в экземпляр класса GithubUser
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IDataSource::class.java)
        //Теперь у нас есть такая заготовка в виде переменной retrofit
        //и мы можем подставлять свои разные запросы, как здесь, подставляется IDataSource с его запросом "/users"
        //retrofit.create(IDataSource::class.java)

    }

}

