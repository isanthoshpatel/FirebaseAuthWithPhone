package com.example.firebaseauthwithphone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_phone_auth.*
import java.util.concurrent.TimeUnit

class PhoneAuth : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    var savedVerificationId: String? = null
    var savedToken: PhoneAuthProvider.ForceResendingToken? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)

        auth = FirebaseAuth.getInstance()

        bt_verify.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            verify()
        }
        bt_auth.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            auth()
        }

    }

    fun verify() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            et_phonenumber.text.toString(),
            60L,
            TimeUnit.SECONDS,
            this,
            callbacks()
        )
    }

    fun callbacks(): PhoneAuthProvider.OnVerificationStateChangedCallbacks {

        return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credentials: PhoneAuthCredential) {
                progressBar.visibility = View.INVISIBLE
                signIn(credentials)
            }

            override fun onVerificationFailed(p0: FirebaseException) {}

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                savedVerificationId = p0
                savedToken = p1
            }

        }
    }

    fun auth() {
        PhoneAuthProvider.getCredential(savedVerificationId!!, et_code.text.toString()).let {
            signIn(it)
        }
    }

    fun signIn(credentials: PhoneAuthCredential) {
        auth?.signInWithCredential(credentials)
            ?.addOnCompleteListener {
                if (it.isSuccessful)
                    Toast.makeText(this, "Successful SignIn", Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.INVISIBLE
                    startActivity(Intent(this,MainActivity::class.java))
               // var  user = it.result?.user
            }
    }
}
