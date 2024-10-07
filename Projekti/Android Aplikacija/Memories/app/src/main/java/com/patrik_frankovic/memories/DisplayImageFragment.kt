package com.patrik_frankovic.memories

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class DisplayImageFragment : Fragment() {
    private val db = Firebase.firestore
    private val storageRef = FirebaseStorage.getInstance().reference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.display_image, container, false)
        val imageInfo = arguments?.getParcelable<ImageInfo>("key")
        val imageName = view.findViewById<TextView>(R.id.imageName)
        imageName.setText(imageInfo?.name)
        val fullImage = view.findViewById<ImageView>(R.id.fullImage)
        Glide.with(this).load(imageInfo?.imageUrl).into(fullImage)
        val backButton = view.findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
            }

        val deleteButton = view.findViewById<ImageButton>(R.id.deleteButton)
        deleteButton.setOnClickListener{
            val dialogBinding = layoutInflater.inflate(R.layout.delete_dialog,null)
            val dialog = Dialog(this@DisplayImageFragment.requireActivity())
            dialog.setContentView(dialogBinding)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            val ybtn = dialogBinding.findViewById<Button>(R.id.ybtn)
            ybtn.setOnClickListener {
                db.collection("pictures")
                    .whereEqualTo("name", imageInfo?.name)
                    .get()
                    .addOnSuccessListener { result ->
                        for (data in result) {
                            val id = data.id
                            db.collection("pictures").document(id)
                                .delete()
                                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
                                    storageRef.child("pictures/${imageInfo?.name}.${imageInfo?.format}").delete()
                                        .addOnSuccessListener {
                                            Log.d(ContentValues.TAG, "Image successfully deleted!")
                                            Toast.makeText(this@DisplayImageFragment.requireActivity(),"Slika izbrisana!",Toast.LENGTH_SHORT).show()
                                            activity?.supportFragmentManager?.popBackStack()
                                        }.addOnFailureListener {
                                                e -> Log.w(ContentValues.TAG, "Error deleting image", e)
                                        }
                                }.addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
                        }
                        dialog.hide()
                        }
                    }

            val nbtn = dialogBinding.findViewById<Button>(R.id.nbtn)
            nbtn.setOnClickListener{
                dialog.hide()
            }
        }
        val editButton = view.findViewById<ImageButton>(R.id.editButton)
        editButton.setOnClickListener {
            val editFragment = EditFragment()
            val bundle = Bundle()
            bundle.putParcelable("key",imageInfo)
            editFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(
                    R.id.displayFragmentLayout,
                    editFragment
                ).addToBackStack(null)
                commit()
            }
        }
        return view
    }

    interface Holder {
        fun leave()
    }

    private var mListener: Holder? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Holder
    }

    override fun onDetach() {
        super.onDetach()
        mListener?.leave()
    }

}

