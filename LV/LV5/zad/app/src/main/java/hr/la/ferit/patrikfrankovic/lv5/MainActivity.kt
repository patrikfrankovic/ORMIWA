package hr.la.ferit.patrikfrankovic.lv5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameText =findViewById<TextView>(R.id.nameText)
        val lastNameText =findViewById<TextView>(R.id.lastNameText)
        val inputName =findViewById<TextView>(R.id.editName)
        val inputLastName =findViewById<TextView>(R.id.editLastName)
        val inputBtn = findViewById<Button>(R.id.button)

        inputBtn.setOnClickListener {
            nameText.text = inputName.text
            lastNameText.text = inputLastName.text
            Toast.makeText(this, "${inputName.text} ${inputLastName.text}", Toast.LENGTH_LONG).show()
        }


    }
}