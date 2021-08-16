package com.example.pawsome.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.widget.Toast
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pawsome.PhotoRetriever
import com.example.pawsome.PhotoUploader
import com.example.pawsome.R
import com.example.pawsome.RecyclerAdapter
import com.example.pawsome.responses.UploadResponse
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.coroutines.*
import java.io.File
import java.net.URI
import android.provider.MediaStore




class UploadFragment : Fragment(){

    var imageURI: Uri? = null
    private lateinit var array: UploadResponse
    private var file: File? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uploadImage.minimumHeight = 600
        uploadImage.maxHeight = 1000

        selectButton.setOnClickListener{
            if (activity?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, 1001)
            } else pickImage()
        }
        uploadButton.setOnClickListener {
            putImages()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1001 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickImage()
                else
                    Toast.makeText(activity,"Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun pickImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == 1){
            uploadImage.setImageURI(data?.data)
            imageURI = data?.data
            val path = getPathFromURI(imageURI)
            if(path!=null) file = File(path)
        }
    }
    private fun putImages(){
        val uploadFragmentJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this.context).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
        val coroutineScope = CoroutineScope(uploadFragmentJob+ Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            if(file != null){
                array = PhotoUploader().postImage(file!!)
                Log.d("Upload Fragment","Hey: $array")
            }else Toast.makeText(activity,"No Image Selected:(", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getPathFromURI(contentUri: Uri?): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            contentUri?.let { activity?.contentResolver?.query(it, proj, null, null, null) }
        if (cursor!!.moveToFirst()) {
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(columnIndex)
        }
        cursor.close()
        return res
    }
}