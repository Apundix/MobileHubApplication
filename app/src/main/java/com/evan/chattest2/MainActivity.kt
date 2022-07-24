package com.evan.chattest2

import android.bluetooth.BluetoothClass
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            supportActionBar?.setTitle("")


        val UsersPage = findViewById<RelativeLayout>(R.id.UsersPage)
        UsersPage.setOnClickListener {
            val Intent = Intent(this, SecondActivity::class.java)
            startActivity(Intent)
        }

        //Todo: This needs to be tied to another page, rather than the second activity. The page will be tied to the devices page.
        val DevicePage = findViewById<RelativeLayout>(R.id.DevicePage)
        DevicePage.setOnClickListener {
            val Intent = Intent(this, Device::class.java)
            startActivity(Intent)
        }

        val MoreheadPage = findViewById<RelativeLayout>(R.id.MoreheadPage)
        MoreheadPage.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.moreheadstate.edu"))
            startActivity(intent)
        }

        val InfoPage2 = findViewById<RelativeLayout>(R.id.InfoPage)
        InfoPage2.setOnClickListener {
            val Intent = Intent(this, InfoPage::class.java)
            startActivity(Intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //Write login for logout
        if(item.itemId == R.id.logout){

            mAuth.signOut()
            val intent = Intent(this@MainActivity, Login::class.java)
            finish()
            startActivity(intent)
            return true
        }

        return true

    }

}