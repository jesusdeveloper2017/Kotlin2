package com.project.dev.app.kotlin2.model


class Person{

    private var name: String? = null
    private var age: Int? = null
    private var genre: String? = null

    //Constructor por defecto
    constructor()

    constructor(name: String?, age: Int?, genre: String?) {
        this.name = name
        this.age = age
        this.genre = genre
    }

    fun getName():String?{
        return this.name
    }

    fun getAge():Int?{

        return this.age
    }

    fun getGenre():String?{

        return this.genre
    }

    fun setName(name: String?){
        this.name = name
    }

    fun setAge(age: Int?){

        this.age = age
    }

    fun setGenre(genre: String?){

        this.genre = genre
    }

    override fun toString(): String {
        return "Person(name=$name, age=$age, genre=$genre)"
    }


//Forma 1 de crear una clase modelo, con varios constructores, y
//sin métodos get y set, solo se acceden a los atributos
//de forma directa

    //class Person (var name: String?, var age: Int?, var genre:String?) {
/*    *//*second constructor*//*
    constructor(name: String): this(name, null, null)

    *//*third constructor*//*
    constructor(age: Int): this(null, age, null)

    *//*forth constructor*//*
    constructor(name: String, age: Int): this(name, age, null)*/
}