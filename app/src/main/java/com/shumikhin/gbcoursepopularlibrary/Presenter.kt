package com.shumikhin.gbcoursepopularlibrary

class Presenter(model: Model) {

    var view: GreetingView? = null
    var myModel = model

    fun attach(view: GreetingView){
        this.view = view
    }

    fun buttonClick(){
        view?.let{
            val greeting = myModel.getGreeting()
            it.setGreeting(greeting)
        }
    }

}