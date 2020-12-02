package com.cowa.covidwatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
    }

    fun signupClick(view: View) {
        var intent: Intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
