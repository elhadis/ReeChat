package com.rgbaat.reechat.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rgbaat.reechat.ModelClasses.Users
import com.rgbaat.reechat.R
import com.rgbaat.reechat.adapterClasses.UserAdapter
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private var userAdapter:UserAdapter? = null
    private  var mUsers:List<Users>? = null
    private  var recyclerView:RecyclerView? = null
    private var searchEditText:EditText? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     val view :View = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView = view.findViewById(R.id.searh_list)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        searchEditText = view.findViewById(R.id.searchusersEt)

        mUsers  = ArrayList()


        retrieveAllUsers()

        searchEditText!!.addTextChangedListener(object :TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {

                SearchForUsers(cs.toString().toLowerCase())


            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })



        return view
    }

    private fun retrieveAllUsers() {

        var firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val refUsers =FirebaseDatabase.getInstance().reference.child("Users")


            refUsers.addValueEventListener(object :ValueEventListener{

                override fun onDataChange(datasnapshot: DataSnapshot) {

                    (mUsers as ArrayList<Users>).clear()

                    if (searchEditText!!.text.toString() == ""){


                        for (snapshot in datasnapshot.children){

                            val  users:Users? = snapshot.getValue(Users::class.java)

                            /////for not display your account if you search
                            if (!(users!!.getUid()).equals(firebaseUserID)){

                                (mUsers as ArrayList<Users>).add(users)
                            }
                        }
                        userAdapter = UserAdapter(context!!,mUsers!!,false)
                        recyclerView!!.adapter = userAdapter

                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
    private fun SearchForUsers(str:String){

        var firebaseUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val queryUsers =FirebaseDatabase.getInstance().reference.child("Users").orderByChild("search")
            .startAt(str)
            .endAt(str + "\uf8ff")
        queryUsers.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(datasnapshot: DataSnapshot) {


                (mUsers as ArrayList<Users>).clear()


                for (snapshot in datasnapshot.children){

                    val  users:Users? = snapshot.getValue(Users::class.java)

                    /////for not display your account if you search
                    if (!(users!!.getUid()).equals(firebaseUserID)){

                        (mUsers as ArrayList<Users>).add(users)
                    }
                }
                userAdapter = UserAdapter(context!!,mUsers!!,false)
                recyclerView!!.adapter = userAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}

