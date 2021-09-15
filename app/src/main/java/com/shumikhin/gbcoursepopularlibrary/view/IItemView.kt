package com.shumikhin.gbcoursepopularlibrary.view

//Мы создали ещё один интерфейс — IItemView, куда вынесли позицию элемента списка. Она
//понадобится для элемента любого списка в нашем приложении
interface IItemView {
    var pos: Int
}