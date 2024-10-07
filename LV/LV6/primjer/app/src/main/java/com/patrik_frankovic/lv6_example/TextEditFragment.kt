package com.patrik_frankovic.lv6_example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class TextEditFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_text_edit, container, false)
        val textView = view.findViewById<TextView>(R.id.inputTextView)
        val editText = view.findViewById<TextView>(R.id.inputEditText)
        val button = view.findViewById<Button>(R.id.button)

        button.setOnClickListener{
            textView.text = editText.text
        }

        return view
    }
}