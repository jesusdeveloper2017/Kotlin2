package com.project.dev.app.kotlin2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.project.dev.app.kotlin2.model.Person
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener, SearchView.OnQueryTextListener {

    //Atributos de la clase
    var tilAge: TextInputLayout? = null
    var tilName: TextInputLayout? = null
    var radioButtonM: RadioButton? = null
    var radioButtonF: RadioButton? = null

    var buttonEjecutar:Button? = null

    //Se usa el modificador internal para atributos publicos en el paquete
    internal var listView: ListView? = null

    //Atributos del objeto Person
    var name = ""
    var age = ""
    var genre = ""

    var people: ArrayList<Person> = ArrayList()
    var names: ArrayList<String> = ArrayList()

    //ArrayAdapter del ListView
    var adapter: ArrayAdapter<String>? = null

    //Guarda el valor de la posición del elemento que se escoge de la lista

    var pos = -1

    //Caja de texto que aparece encima del Toolbar
    var search:SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Referenciar componentes del xml dentro de la clase Java
        findComponents()

        buttonEjecutar!!.setOnClickListener(this)
        listView!!.onItemClickListener = this
        listView!!.onItemLongClickListener = this

        //Se inicializa el ArrayAdapter para el ListView con el Array de String vacía
        adapter =
            ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, names)

        //Se setea el adapter del ListView con el adapter que se acaba de crear
        listView!!.adapter = adapter!!
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        search = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView

        search!!.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }
    private fun findComponents() {
        tilAge = til_age
        tilName = til_name
        radioButtonM = radio_button_m
        radioButtonF = radio_button_f
        buttonEjecutar = button_ejecutar
        listView = list_view
    }

    //implementación de la funcion onClick
    override fun onClick(v: View?) {

        //Condicional que verifica si el view presionado es el botón de ejecutar
        if(v?.id == R.id.button_ejecutar){

             operacion()
        }
    }

    //Registrar o editar datos de la persona
    private fun operacion() {

        //Se obtiene el contenido de ambas cajas de texto y se guardan en los atributos
        age = tilAge!!.editText!!.text.toString()

        name = tilName!!.editText!!.text.toString()

        if(age.isEmpty()){

          //Se cambia el mensaje de error del TextInputLayout que contiene al EditText de la edad
          //indicando que se ingrese la edad
          tilAge!!.error = "Ingrese la edad"

          return
        }
        else{
            //Se borra el mensaje de error del TextInputLayout que contiene al EditText de la edad
            tilAge!!.error = ""
        }

        if(name.isEmpty()){

            //Se cambia el mensaje de error del TextInputLayout que contiene al EditText del nombre
            //indicando que se ingrese el nombre
            tilName!!.error = "Ingrese el nombre"

            return
        }
        else{
            //Se borra el mensaje de error del TextInputLayout que contiene al EditText del nombre
            tilName!!.error = ""
        }

        //Estructura condicional when
        when{

            //Se revisa que el radioButton Masculino esté seleccionado
            //Si es así entonces se le asigna M al género de la persona
            radioButtonM!!.isChecked -> genre = "M"

            //Sino se le asigna F al género de la persona
            else -> genre = "F"
        }

        //Se convierte a Int la edad capturada del EditText
        val ageInt = Integer.valueOf(age)

        //Se crea un objeto de tipo Person y se le pasan los datos capturados del formulario
        val person = Person(name, ageInt, genre)

/*        val adapter =
            ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, names)*/

        //Si pos es igual a -1
        if(pos == -1){

            //Se guarda el objeto de tipo Person dentro del ArrayList de tipo Person
            people.add(person)

            //Se guarda solo el nombre de la persona, dentro del ArrayList de tipo String
            names.add(name)

            //Se muestra el mensaje que la persona se agregó
            Toast.makeText(this@MainActivity, "Persona agregada!", Toast.LENGTH_LONG).show()
        }
        //Si pos es diferente de -1
        else{

            //Se reemplaza el objeto Person que se encuentre en la posición pos
            //en el ArrayList de tipo Person, con los datos del objeto Person que se creó antes
            people.set(pos, person)

            //Se reemplaza el nombre que se encuentre en la posición pos
            //en el ArrayList de tipo String, con el nombre que se capturó antes
            names.set(pos, name)

            //Se le asigna -1 a pos, para que la próxima vez guarde en ambos ArrayList y no
            //actualice
            pos = -1

            //Se muestra el mensaje que la persona se actualizó
            Toast.makeText(this@MainActivity, "Persona actualizada!", Toast.LENGTH_LONG).show()
        }

        //Se guardan los cambios que se le hayan hecho al ArrayList String de nombres que se le pasó
        //al adapter que se le pasó al ListView
        adapter!!.notifyDataSetChanged()

        //Se limpian los datos de ambas cajas de texto
        tilAge!!.editText!!.setText("")
        tilName!!.editText!!.setText("")

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        //tilAge!!.editText!!.setText(people!!.get(position).age.toString())
        tilAge!!.editText!!.setText(people!!.get(position).getAge().toString())
        //tilName!!.editText!!.setText(people!!.get(position).name)
        tilName!!.editText!!.setText(people!!.get(position).getName())

        //Estructura condicional when
        when{

            //people.get(position).genre.equals("M") -> {
            people.get(position).getGenre().equals("M") -> {
                radioButtonM!!.isChecked = true
            }

            else -> {
                radioButtonF!!.isChecked = true
            }
        }

        pos = position

    }

    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long):
            Boolean {

        //var name = people[position].name
        var name = people[position].getName()
        alertDialog(position, name)

        return true
    }

    private fun alertDialog(position:Int, name:String?){

        AlertDialog.Builder(this@MainActivity)
        .setIcon(R.mipmap.ic_launcher)
             .setTitle(resources.getString(R.string.eliminar_registro))
            .setMessage("Está seguro de eliminar a: $name")
            .setPositiveButton("Eliminar"){dialog, which ->

                people.remove(people[position])

                names.remove(names[position])

                adapter!!.notifyDataSetChanged()

                Toast.makeText(this@MainActivity, "Persona eliminada!", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        //MutableList es una interface que cumple una función parecida a la clase ArrayAdapter
        //para crear listas dinámicas
        val tempNames: MutableList<String> = ArrayList()
        tempNames.clear()

        if(!newText.equals("")){

            for (i in 0 until names.size){

                if(names[i].startsWith(newText.toString())){

                    tempNames.add(names[i])

                    val adapter =
                        ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, tempNames)

                    listView!!.adapter = adapter

                }

            }

        }
        else {

         val adapter =
                ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, names)

            listView!!.adapter = adapter
        }
        return false
    }
}
