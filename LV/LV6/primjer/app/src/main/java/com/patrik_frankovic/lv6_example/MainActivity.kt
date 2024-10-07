package com.patrik_frankovic.lv6_example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var imageFragment: ImageFragment
    private lateinit var textEditFragment: TextEditFragment
    private var isSwitched: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFrags()

        val switchFragsBtn = findViewById<Button>(R.id.switchFragsButton)
        val switchActivityBtn = findViewById<Button>(R.id.switchActivityButton)

        switchFragsBtn.setOnClickListener{
            isSwitched = !isSwitched
            switchFrags()
        }

        switchActivityBtn.setOnClickListener{
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun setupFrags(){
        val transaction = supportFragmentManager.beginTransaction()
        imageFragment = ImageFragment()
        textEditFragment = TextEditFragment()
        transaction.replace(R.id.fragmentContainer1,textEditFragment)
        transaction.replace(R.id.fragmentContainer2, imageFragment)
        transaction.commit()
    }

    private  fun switchFrags(){
        val transaction = supportFragmentManager.beginTransaction()
        imageFragment = ImageFragment()
        textEditFragment = TextEditFragment()

        if(isSwitched){
            transaction.replace(R.id.fragmentContainer2,textEditFragment)
            transaction.replace(R.id.fragmentContainer1, imageFragment)
            transaction.commit()
        }
        else{
            transaction.replace(R.id.fragmentContainer1,textEditFragment)
            transaction.replace(R.id.fragmentContainer2, imageFragment)
            transaction.commit()
        }
    }
}