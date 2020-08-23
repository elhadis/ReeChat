package com.rgbaat.reechat

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TableLayout
import androidx.appcompat.widget.DialogTitle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rgbaat.reechat.Fragments.ChatFragment
import com.rgbaat.reechat.Fragments.SearchFragment
import com.rgbaat.reechat.Fragments.SettingsFragment
import com.rgbaat.reechat.ModelClasses.Users
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var userRef : FirebaseDatabase? = null
    var firebaseUser:FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar_main))


        firebaseUser = FirebaseAuth.getInstance().currentUser
      var  userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)

        val toolbar:Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        val tabLayout:TabLayout = findViewById(R.id.tab_layout)
        val viewPager:ViewPager = findViewById(R.id.viewPager)


        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ChatFragment(),"Chats")
        viewPagerAdapter.addFragment(SearchFragment(),"Search")
        viewPagerAdapter.addFragment(SettingsFragment(),"Settings")


        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)



        /////display userName and profile Picture

        userRef!!.addValueEventListener(object :ValueEventListener{


            override fun onDataChange(datasnapshot: DataSnapshot) {

                if (datasnapshot.exists()){

                    val user :Users? = datasnapshot.getValue(Users::class.java)

                    user_name.text = user!!.getUsername()
                    Picasso.get().load(user.getProfile()).placeholder(R.drawable.me).into(profile_image)

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      when (item.itemId) {

            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()

                val  intent = Intent(this@MainActivity,WelcomeActivity2::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                return true
            }

        }
        return false
    }
    internal class ViewPagerAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager){

         private val fragments:ArrayList<Fragment>
         private val titles:ArrayList<String>

        init {

            fragments = ArrayList()
            titles = ArrayList()
        }

        override fun getItem(position: Int): Fragment {

            return fragments[position]
        }

        override fun getCount(): Int {

            return fragments.size
        }

        fun addFragment(fragment: Fragment,title: String){

            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

    }
}