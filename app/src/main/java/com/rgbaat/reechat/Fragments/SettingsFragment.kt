package com.rgbaat.reechat.Fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.rgbaat.reechat.ModelClasses.Users
import com.rgbaat.reechat.R
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import java.lang.StringBuilder

class SettingsFragment : Fragment() {

   var userReference:DatabaseReference?= null
    var firebaseUser:FirebaseUser? = null
    var RequsetCode = 438
    private var imageUri: Uri? = null
    private var coverImage:ImageView? = null
    private var profileImage:CircleImageView? = null
    private var coverChecker:String? = null
    private var socialChecker:String? = null

    private var storageRef:StorageReference? = null
    private var facebook:ImageView? = null
    private var instagram:ImageView? = null
    private var wibsit:ImageView? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        coverImage = view.findViewById(R.id.conver_image_settings)
        profileImage = view.findViewById(R.id.profil_image_settings)
        facebook =view.findViewById(R.id.set_facebook)
        instagram =view.findViewById(R.id.set_instagram)
        wibsit =view.findViewById(R.id.set_wibsite)



        firebaseUser = FirebaseAuth.getInstance().currentUser
        storageRef = FirebaseStorage.getInstance().reference.child("User Images")

        userReference =
            FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)

        userReference!!.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(datasnapshot: DataSnapshot) {

                if (datasnapshot.exists()) {

                    val user: Users? = datasnapshot.getValue(Users::class.java)

                    if (context != null) {
                        view.username_settings.text = user!!.getUsername()
                        Picasso.get().load(user.getProfile()).into(view.profil_image_settings)
                        Picasso.get().load(user.getCover()).into(view.conver_image_settings)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })



        coverImage!!.setOnClickListener {
            coverChecker = "cover"

           pickImage()
        }
        profileImage!!.setOnClickListener {

            pickImage()
        }


        wibsit!!.setOnClickListener {

            socialChecker = "wibsite"
            setSocialLink()

        }



        facebook!!.setOnClickListener {


            socialChecker = "facebook"
            setSocialLink()

        }


        instagram!!.setOnClickListener {

            socialChecker = "instagram"
            setSocialLink()

        }


        return view
    }

    private fun setSocialLink() {

        var builder:AlertDialog.Builder =
            AlertDialog.Builder(context,R.style.Theme_AppCompat_DayNight_Dialog_Alert)

        if (socialChecker == "wibsite"){

            builder.setTitle("Write URL : ")



        }
        else{

            builder.setTitle("Write User Name : ")
        }

        val  editText =   EditText(context)

        if (socialChecker == "wibsite"){

            editText.setHint("e.g ww.google.com ")


        }
        else{

            editText.setHint("e.g Elhadi Ahmed ")
        }
        builder.setView(editText)
        builder.setPositiveButton("Create",DialogInterface.OnClickListener{

            dialogInterface, which ->
            val str = editText.text.toString()
            if (str == ""){

                Toast.makeText(context,"Please Write Something",Toast.LENGTH_LONG).show()

            }
            else{

                saveSocialLink(str)
            }
        })


        builder.setNegativeButton("Cancel",DialogInterface.OnClickListener{

                dialogInterface, which ->
            dialogInterface.cancel()



            })

        builder.show()

    }

    private fun saveSocialLink(str: String) {

        val hasSocial = HashMap<String,Any>()

        when(socialChecker){

            "facebook"->
            {

                hasSocial["fasecbook"] = "https://m.facebook.com/$str"
            }

            "instagram"->
            {

                hasSocial["instagram"] = "https://m.instagram.com/$str"
            }


            "wibsite"->
            {

                hasSocial["instagram"] = "https://$str"
            }
        }
        userReference!!.updateChildren(hasSocial).addOnCompleteListener { task ->

            if (task.isSuccessful){

                Toast.makeText(context,"Saved Successfully",Toast.LENGTH_LONG).show()

            }
        }

    }

    private fun pickImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,RequsetCode)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequsetCode && resultCode == Activity.RESULT_OK && data!!.data != null){

           imageUri = data.data
            Toast.makeText(context,"Uploading",Toast.LENGTH_LONG).show()

            uploadImageToDatabase()


        }
    }

    private fun uploadImageToDatabase() {

        val prgressBarr = ProgressDialog(context)
        prgressBarr.setMessage("Image is Uploading Please Waite....")
        prgressBarr.show()

        if (imageUri != null){

            val fileRef = storageRef!!.child(System.currentTimeMillis().toString() +".jpg")

            var uploadTask:StorageTask<*>
            uploadTask = fileRef.putFile(imageUri!!)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot,Task<Uri>> {task ->

                if (!task.isSuccessful) {
                    task.exception.let {
                        throw it!!
                    }
                }
                return@Continuation fileRef.downloadUrl

            }).addOnCompleteListener { task ->

                if (task.isSuccessful){

                    val downloadUrl = task.result
                    val url = downloadUrl.toString()

                    if (coverChecker == "cover"){

                        val hashMap = HashMap<String,Any>()
                        hashMap["cover"] = url
                        userReference!!.updateChildren(hashMap)
                        coverChecker = ""
                    }
                    else{



                            val hashMapProfilImage = HashMap<String,Any>()
                            hashMapProfilImage ["profile"] = url
                            userReference!!.updateChildren(hashMapProfilImage )
                            coverChecker = ""
                        }
                    prgressBarr.dismiss()
                    }

                }
            }
        }
    }


