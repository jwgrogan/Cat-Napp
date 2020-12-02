package edu.utap.catnapp.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import java.io.File

// Store files in firebase storage
object Storage {
    private val photoStorage: StorageReference = FirebaseStorage.getInstance().reference.child("cats")

    fun uploadImage(localFile: File, uuid: String, uploadSuccess:()->Unit) {
        // XXX Write me
        val file = Uri.fromFile(localFile)
        val uuidRef = photoStorage.child(uuid)
        val metadata = StorageMetadata.Builder()
            .setContentType("image/jpg")
            .build()
        val uploadTask = uuidRef.putFile(file, metadata)
        // Register observers to listen for when the download is done or if it fails
        uploadTask
            .addOnFailureListener {
                // Handle unsuccessful uploads
                if(localFile.delete()) {
                    Log.d(javaClass.simpleName, "Upload FAILED $uuid, file deleted")
                } else {
                    Log.d(javaClass.simpleName, "Upload FAILED $uuid, file delete FAILED")
                }
            }
            .addOnSuccessListener {
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                uploadSuccess()
                if(localFile.delete()) {
                    Log.d(javaClass.simpleName, "Upload succeeded $uuid, file deleted")
                } else {
                    Log.d(javaClass.simpleName, "Upload succeeded $uuid, file delete FAILED")
                }
            }
    }

    fun deleteImage(pictureUUID: String) {
        // Delete the file
        // XXX Write me
        // https://firebase.google.com/docs/storage/android/delete-files

        // Create a reference to the file to delete
        val imageRef = photoStorage.child(pictureUUID)
        // Delete the file
        imageRef.delete().addOnSuccessListener {
            // File deleted successfully
        }.addOnFailureListener {
            // Uh-oh, an error occurred!
        }
    }
    fun uuid2StorageReference(pictureUUID: String): StorageReference {
        return photoStorage.child(pictureUUID)
    }
}