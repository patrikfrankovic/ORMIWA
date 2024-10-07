package com.patrik_frankovic.lv8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity(),
    PersonRecyclerAdapter.ContentListener {
    private val db = Firebase.firestore
    private lateinit var recyclerAdapter: PersonRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.personList)
        db.collection("persons")
            .get()
            .addOnSuccessListener { result ->
                val personList = ArrayList<Person>()
                for (data in result.documents) {
                    val person = data.toObject(Person::class.java)
                    if (person != null) {
                        person.id = data.id
                        personList.add(person)
                    }
                }
                recyclerAdapter = PersonRecyclerAdapter(personList,
                    this@MainActivity)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = recyclerAdapter
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.",
                    exception)
            }
        val btn =findViewById<Button>(R.id.saveButton)
        btn.setOnClickListener{
            val name = findViewById<EditText>(R.id.nameEditText).text.toString()
            val image = findViewById<EditText>(R.id.imageUrlEditText).text.toString()
            val description = findViewById<EditText>(R.id.descriptionEditText).text.toString()
            val personInfo=Person(image,name,description)
            db.collection("persons")
                .add(personInfo)
                .addOnSuccessListener {
                Toast.makeText(this@MainActivity,"Person successfully added!",Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{
                    Toast.makeText(this@MainActivity,"ERROR!",Toast.LENGTH_SHORT).show()
                }

            db.collection("persons")
                .get()
                .addOnSuccessListener { result ->
                    val personList = ArrayList<Person>()
                    for (data in result.documents) {
                        val person = data.toObject(Person::class.java)
                        if (person != null  ) {
                            person.id=data.id
                            personList.add(person)
                        }
                    }
                    recyclerAdapter = PersonRecyclerAdapter(personList,
                        this@MainActivity)
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = recyclerAdapter
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents.",
                        exception)
                }
        }
    }

    override fun onItemButtonClick(index: Int, person: Person, clickType:
    ItemClickType) {
        if (clickType == ItemClickType.EDIT) {
            db.collection("persons")
                .document(person.id)
                .set(person)
        }
        else if (clickType == ItemClickType.REMOVE) {
            recyclerAdapter.removeItem(index)
            db.collection("persons")
                .document(person.id)
                .delete()
        }
    }
}