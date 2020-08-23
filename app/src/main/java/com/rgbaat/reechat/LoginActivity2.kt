package com.rgbaat.reechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.activity_segister2.*

class    LoginActivity2 : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        mAuth = FirebaseAuth.getInstance()

        val toolbar: Toolbar = findViewById(R.id.toolbar_login)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val  intent = Intent(this@LoginActivity2 ,WelcomeActivity2::class.java)
            startActivity(intent)

            finish()
        }


        login_btn.setOnClickListener {

            loginUser()
        }
    }

    private fun loginUser() {

        var email:String = email_login.text.toString()
        var password:String = password_login.text.toString()

        if (email == ""){

            Toast.makeText(this,"please Write Email", Toast.LENGTH_LONG).show()

        }

        else if (password == ""){
            Toast.makeText(this,"please Write Password", Toast.LENGTH_LONG).show()

        }else{

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->

                if (task.isSuccessful){

                    val  intent = Intent(this@LoginActivity2,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                else{

                    Toast.makeText(this,"Error Message "+ task.exception!!.message,Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}