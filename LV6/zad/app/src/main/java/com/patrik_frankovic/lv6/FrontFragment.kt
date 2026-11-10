package com.patrik_frankovic.lv6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

class FrontFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_front, container, false)
        val btn = view.findViewById<Button>(R.id.saveButton)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        btn.setOnClickListener {
            if (radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(activity, getString(R.string.toast), Toast.LENGTH_SHORT).show()
            } else {
                val radioButton = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                val inputText = radioButton.text.toString()
                val bundle = Bundle()
                val backFragment = BackFragment()
                bundle.putString("BUTTON", inputText)
                backFragment.arguments = bundle
                val fragmentTransaction: FragmentTransaction? =
                    activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.mainFragmentLayout, backFragment)
                fragmentTransaction?.commit()
            }
        }
        return view
    }
}