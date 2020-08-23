package com.rgbaat.reechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_segister2.*

class SegisterActivity2 : AppCompatActivity() {
    
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUser: FirebaseDatabase
    private var firebaseUserID:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segister2)
        
        val toolbar:Toolbar = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAuth = FirebaseAuth.getInstance()

        toolbar.setNavigationOnClickListener {

            val  intent = Intent(this@SegisterActivity2,WelcomeActivity2::class.java)
            startActivity(intent)
            finish()
        }
        
        
        segister_btn.setOnClickListener { 
            
            RegisterUser()
        }
    }

    private fun RegisterUser() {
        
        var username:String = useuname_segister.text.toString()
        var email:String = email_segister.text.toString()
        var password:String = password_segister.text.toString()
        
        if (username == ""){
            
            Toast.makeText(this,"please Write UserName",Toast.LENGTH_LONG).show()
        }
        
        else if (email == ""){

            Toast.makeText(this,"please Write Email",Toast.LENGTH_LONG).show()

        }

        else if (password == ""){
            Toast.makeText(this,"please Write Password",Toast.LENGTH_LONG).show()

        }
        else{

            mAuth.createUserWithEmailAndPassword(email,password) .addOnCompleteListener { task ->

               if (task.isSuccessful){

                   firebaseUserID = mAuth.currentUser!!.uid

                     val refUser = FirebaseDatabase.getInstance().reference.child("Users")
                         .child(firebaseUserID!!)

                   val userHashMap = HashMap<String,Any>()
                   userHashMap["uid"] = firebaseUserID.toString()
                   userHashMap["username"] = username
                   userHashMap["profile"] = "https://firebasestorage.googleapis.com/v0/b/reechat-30e41.appspot.com/o/me.jpg?alt=media&token=4beaaa7f-2f39-4a9b-8b42-26cbf530c7d0"
                   userHashMap["cover"] = "https://firebasestorage.googleapis.com/v0/b/reechat-30e41.appspot.com/o/cover.Jpeg?alt=media&token=776d81c6-725e-416f-9d4a-ea2adb1d28ee"
                   userHashMap["status"] = "offline"
                   userHashMap["search"] = username.toLowerCase()
                   userHashMap["facebook"] = "https://m.facebook.com"
                   userHashMap["instagram"] = "https://m.instagram.com"
                   userHashMap["wibsite"] ="https://www.google.com"

                   refUser.updateChildren(userHashMap).addOnCompleteListener { task ->

                       if (task.isSuccessful){

                           val  intent = Intent(this@SegisterActivity2,MainActivity::class.java)
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                           startActivity(intent)
                           finish()

                       }
                   }


               }else{

                   Toast.makeText(this,"Error Message "+ task.exception!!.message,Toast.LENGTH_LONG).show()

               }



            }

            

            
        }
    }
}