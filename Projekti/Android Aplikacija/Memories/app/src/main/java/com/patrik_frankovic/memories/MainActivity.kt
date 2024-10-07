package com.patrik_frankovic.memories

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity(), DisplayImageFragment.Holder {
    private var code: Int = 123
    private lateinit var currentPhotoPath: String
    private val storageRef = FirebaseStorage.getInstance().reference
    private lateinit var progressDialog: ProgressDialog
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cameraButton = findViewById<ImageButton>(R.id.cameraButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        pullImages(recyclerView)
        cameraButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var photoFile = createImageFile()
            if (photoFile != null) {
                val photoUri = FileProvider.getUriForFile(
                    this,
                    "com.example.android.fileprovider",
                    photoFile
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(cameraIntent, code)
            } else {
                Toast.makeText(this, "Image not found!", Toast.LENGTH_LONG)
            }
        }
    }

    private fun createImageFile(): File? {
        val fileName = "pic"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            fileName,  // prefix
            ".jpg",  // suffix
            storageDir // directory
        )

        currentPhotoPath = image.absolutePath
        return image
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == code && resultCode == RESULT_OK) {
            val imageUri: Uri = Uri.fromFile(File(currentPhotoPath))
            uploadImage(imageUri, recyclerView)
        }
    }

    private fun uploadImage(uri: Uri, recyclerView: RecyclerView) {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Molimo pričekajte, slika se sprema!")
        progressDialog.show()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss")
        val date = LocalDateTime.now().format(formatter)
        val name = "IMG.${date}"
        val uploadTask = storageRef.child("pictures/${name}.jpg").putFile(uri)
        uploadTask.addOnSuccessListener {
            Log.w(TAG, "Image Upload success")
            val uploadedURL = storageRef.child("pictures/${name}.jpg").downloadUrl
            Log.w(TAG, "Uploaded $uploadedURL")
            uploadedURL.addOnSuccessListener {
                val imageInfo = ImageInfo(it.toString(), name, "", date, "jpg")
                db.collection("pictures").document(name)
                    .set(imageInfo)
                    .addOnSuccessListener {
                        Log.w(TAG, "Document successfully written!")
                        progressDialog.dismiss()
                        pullImages(recyclerView)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error writing document", e)
                        progressDialog.dismiss()
                    }
            }
        }.addOnFailureListener {
            Log.e(TAG, "Image Upload fail")
            progressDialog.dismiss()
        }
    }

    private fun pullImages(recyclerView: RecyclerView) {
        var imageInfoList: ArrayList<ImageInfo> = ArrayList()
        db.collection("pictures")
            .get()
            .addOnSuccessListener { result ->
                for (data in result.documents) {
                    val image = data.toObject<ImageInfo>()
                    if (image != null) {
                        imageInfoList.add(image)
                    }
                }
                var adapter = ImageRecyclerAdapter(imageInfoList)
                recyclerView.adapter = adapter
                recyclerView.layoutManager =
                    GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
                adapter.setOnItemClick(object : ImageRecyclerAdapter.onClickListener {
                    override fun onClickListener(position: Int) {
                        val displayImageFragment = DisplayImageFragment()
                        val bundle = Bundle()
                        bundle.putParcelable("key", imageInfoList[position])
                        displayImageFragment.arguments = bundle
                        supportFragmentManager.beginTransaction().apply {
                            replace(
                                R.id.mainFragmentLayout,
                                displayImageFragment
                            ).addToBackStack(null)
                            commit()
                        }
                    }
                })
            }.addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
    override fun leave() {
        pullImages(recyclerView)
    }

}



