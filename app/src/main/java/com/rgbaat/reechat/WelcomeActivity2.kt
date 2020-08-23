package com.rgbaat.reechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_welcome2.*

class WelcomeActivity2 : AppCompatActivity() {

    private var firebaseUser:FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome2)

        segister_welcome_btn.setOnClickListener {

            val  intent = Intent(this@WelcomeActivity2,SegisterActivity2::class.java)
            startActivity(intent)
            finish()

        }


        login_welcome_btn.setOnClickListener {

            val  intent = Intent(this@WelcomeActivity2,LoginActivity2::class.java)
            startActivity(intent)
            finish()

        }


    }

    override fun onStart() {
        super.onStart()

        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null){

          val  intent = Intent(this@WelcomeActivity2,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}