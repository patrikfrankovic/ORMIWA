package com.patrik_frankovic.lv6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction

class BackFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_back, container,false)
        val inputText = view.findViewById<TextView>(R.id.inputText)
        val input=arguments?.getString("BUTTON")
        inputText.text = input.toString()
        val btn = view.findViewById<Button>(R.id.backButton)
        btn.setOnClickListener {
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.mainFragmentLayout, FrontFragment())
            fragmentTransaction?.commit()
        }
        return view
    }
}