package com.example.firebaseauthwithphone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var auth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        bt_signout.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            auth?.signOut()
            startActivity(Intent(this,PhoneAuth::class.java))

        }

    }

    override fun onStart() {
        super.onStart()

        if(auth?.currentUser == null){
            progressBar.visibility = View.VISIBLE
            startActivity(Intent(this,PhoneAuth::class.java))
        }
    }
}
