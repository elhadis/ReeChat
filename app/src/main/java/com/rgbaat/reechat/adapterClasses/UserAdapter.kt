package com.rgbaat.reechat.adapterClasses

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.auth.User
import com.rgbaat.reechat.MainActivity
import com.rgbaat.reechat.MessageChatActivity
import com.rgbaat.reechat.ModelClasses.Users
import com.rgbaat.reechat.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.user_search_item.view.*

class UserAdapter(mContext: Context,mUser:List<Users>,isChatCheck:Boolean)
    : RecyclerView.Adapter<UserAdapter.ViewHolder?>() {

   private val mContext:Context
    private val mUser:List<Users>
    private val isChatCheck:Boolean
    init {
        this.mUser = mUser
        this.mContext = mContext
        this.isChatCheck = isChatCheck
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(mContext).inflate(R.layout.user_search_item,parent,false)
        return UserAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUser.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user:Users = mUser[position]
        holder.userNmaeTxt.text = user.getUsername()
        Picasso.get().load(user.getProfile()).placeholder(R.drawable.me).into(holder.profileImageViwe)

        holder.itemView.setOnClickListener {

            val option = arrayOf<CharSequence>(

                "Send Massege",
                "Visit Profile"
            )
            val builder :AlertDialog.Builder =
                AlertDialog.Builder(mContext)
            builder.setTitle("What Do You Want")
            builder.setItems(option,DialogInterface.OnClickListener { dialog, positions ->

                if (positions == 0){

                    val  intent = Intent(mContext, MessageChatActivity::class.java)
                   intent.putExtra("visit_id",user.getUid())
                    mContext.startActivity(intent)


                }
                if (positions == 1){


                }


            })
        }


    }


    class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){

        var userNmaeTxt:TextView
        var lastMessageTxt:TextView
        var profileImageViwe:CircleImageView
        var onlineImage:CircleImageView
        var offLineImage:CircleImageView

        init {


            userNmaeTxt = itemView.findViewById(R.id.username)
            lastMessageTxt = itemView.findViewById(R.id.message_last)
            profileImageViwe= itemView.findViewById(R.id.profile_image_search)
            onlineImage = itemView.findViewById(R.id.image_online)
            offLineImage = itemView.findViewById(R.id.image_offline)
        }

    }
}


