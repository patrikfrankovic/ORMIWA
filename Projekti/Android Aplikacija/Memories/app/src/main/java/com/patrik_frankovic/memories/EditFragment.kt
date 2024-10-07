package com.patrik_frankovic.memories

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.InputStream
import java.net.URL


class EditFragment : Fragment() {
    private val db = Firebase.firestore
    private val storageRef = FirebaseStorage.getInstance().reference
    private lateinit var progressDialog: ProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_image, container, false)
        val imageInfo = arguments?.getParcelable<ImageInfo>("key")
        val dateText = view.findViewById<TextView>(R.id.dateText)
        dateText.setText(imageInfo?.date)
        val formatText = view.findViewById<TextView>(R.id.formatText)
        formatText.setText(imageInfo?.format)
        val nameText = view.findViewById<EditText>(R.id.nameText)
        nameText.setText(imageInfo?.name)
        val descText = view.findViewById<EditText>(R.id.descText)
        descText.setText(imageInfo?.description)
        val discardButton = view.findViewById<Button>(R.id.discardButton)
        discardButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            progressDialog = ProgressDialog(this@EditFragment.requireActivity())
            progressDialog.setMessage("Molimo pričekajte, promjene se primjenjuju")
            val name = nameText.text
            val desc = descText.text
            if ((desc.toString() != imageInfo?.description.toString()) && (name.toString() == imageInfo?.name.toString())) {
                progressDialog.show()
                db.collection("pictures")
                    .whereEqualTo("name", imageInfo?.name)
                    .get()
                    .addOnSuccessListener { result ->
                        for (data in result) {
                            val id = data.id
                            db.collection("pictures").document(id)
                                .update("description", desc.toString())
                                .addOnSuccessListener {
                                    progressDialog.dismiss()
                                    Log.w(TAG, "Image edit success")
                                    Toast.makeText(
                                        this@EditFragment.requireActivity(),
                                        "Podatci slike uređeni!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    backToMain()
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Image edit fail", e)
                                    backToMain()
                                }
                        }
                    }
            }
            if (name.toString() != imageInfo?.name.toString()) {
                val policy = ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)
                val input: InputStream = URL(imageInfo?.imageUrl.toString()).openStream()
                storageRef.child("pictures/${name}.jpg").downloadUrl.addOnSuccessListener {
                    Toast.makeText(
                        this@EditFragment.requireActivity(),
                        "Naziv već u upotrebi!",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener {
                    progressDialog.show()
                    storageRef.child("pictures/${name}.jpg").putStream(input).addOnSuccessListener {
                        storageRef.child("pictures/${name}.jpg").downloadUrl.addOnSuccessListener {
                            val url = it.toString()
                            db.collection("pictures")
                                .whereEqualTo("name", imageInfo?.name)
                                .get()
                                .addOnSuccessListener { result ->
                                    for (data in result) {
                                        val id = data.id
                                        db.collection("pictures").document(id)
                                            .update("name", name.toString())
                                            .addOnSuccessListener {
                                                db.collection("pictures").document(id)
                                                    .update("description", desc.toString())
                                                    .addOnSuccessListener {
                                                        db.collection("pictures").document(id)
                                                            .update("imageUrl", url)
                                                            .addOnSuccessListener {
                                                                storageRef.child("pictures/${imageInfo?.name}.jpg")
                                                                    .delete().addOnSuccessListener {
                                                                        progressDialog.dismiss()
                                                                        Toast.makeText(
                                                                            this@EditFragment.requireActivity(),
                                                                            "Podatci slike uređeni!",
                                                                            Toast.LENGTH_SHORT
                                                                        ).show()
                                                                    backToMain()
                                                                }.addOnFailureListener { e ->
                                                                    Log.w(TAG, "Image edit fail", e)
                                                                        progressDialog.dismiss()
                                                                    backToMain()
                                                                }
                                                            }.addOnFailureListener { e ->
                                                                Log.w(TAG, "Image edit fail", e)
                                                                progressDialog.dismiss()
                                                                backToMain()
                                                            }
                                                    }.addOnFailureListener { e ->
                                                        Log.w(TAG, "Image edit fail", e)
                                                        progressDialog.dismiss()
                                                        backToMain()
                                                    }
                                            }.addOnFailureListener { e ->
                                                Log.w(TAG, "Image edit fail", e)
                                                progressDialog.dismiss()
                                                backToMain()
                                            }
                                    }
                                }
                        }.addOnFailureListener { e ->
                            Log.e(TAG, "Image edit fail", e)
                            progressDialog.dismiss()
                            backToMain()
                        }
                    }.addOnFailureListener { e ->
                        Log.e(TAG, "Image edit fail", e)
                        progressDialog.dismiss()
                        backToMain()
                    }
                }
            }
        }
        return view

    }
    private fun backToMain(){
        activity?.supportFragmentManager?.popBackStack()
        activity?.supportFragmentManager?.popBackStack()
    }
}



