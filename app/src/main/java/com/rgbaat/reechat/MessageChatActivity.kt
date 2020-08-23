package com.rgbaat.reechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_message_chat.*

class MessageChatActivity : AppCompatActivity() {

    private var  userIdVisit :String? = null
    private var  firebaseUser:FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_chat)

        firebaseUser = FirebaseAuth.getInstance().currentUser

        intent = intent

        userIdVisit = intent.getStringExtra("visit_id")

        send_message_btn.setOnClickListener {


            val message = text_message.text.toString()

            if (message == null){

                Toast.makeText(this,"Write Message first.....",Toast.LENGTH_LONG).show()
            }
            else{


                sendMessageToUser(firebaseUser!!.uid,userIdVisit,message)
            }


        }
    }

    //////// for send and receive message
    private fun sendMessageToUser(senderId: String, receiverId: String?, message: String) {


        val  reference = FirebaseDatabase.getInstance().reference
        val messageKey = reference.push().key
        val messageHashMap = HashMap<String,Any>()

        messageHashMap["sender"] = senderId
        messageHashMap["receiver"] = receiverId.toString()
        messageHashMap["message"] = message
        messageHashMap["isseen"] = false
        messageHashMap["url"] = ""
        messageHashMap["messageid"] = messageKey.toString()

        reference.child("Chats").child(messageKey!!).setValue(messageHashMap)


    }
}