package com.cowa.covidwatch


import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cowa.covidwatch.model.Token
import com.cowa.covidwatch.model.TokenAnswer
import com.cowa.covidwatch.service.DataService
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class LoginActivity : AppCompatActivity() {

    var callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getkeyhash()
        loginButton.setReadPermissions("email")
        if(AccessToken.getCurrentAccessToken() == null) {
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
             var t = Token("facebook",
                 "FEqMPOyqzHI3FkrvS5GmduZa3OFy8plFL5sEBuHd",
                 "BwDAYCbgvIUJO4bjd1cLq0iVPMFe4mRP3AIExj8tVSGWQs5QeDvwlPPl1ARerU4kq4fUc6HUqOTbksnHlmsNfWulDWFuVg5G4I15KLYsSVm8KYgsbVZkM0gLRddMcHum",
                 loginResult?.accessToken?.token,
                 "convert_token")

                authetify(t)
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
              //  intent.putExtra("ytvideo",ytvideo) we should send user profile
                startActivity(intent)
            }

            override fun onCancel() {
                Toast.makeText(this@LoginActivity,"user disconnected",Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: FacebookException) {

            }
        })}
        else{
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            //  intent.putExtra("ytvideo",ytvideo) we should send user profile
            startActivity(intent)
        }


    }



    fun getkeyhash(){
        try {
            val info = packageManager.getPackageInfo(
                "com.cowa.covidwatch",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }




    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }



    fun authetify(t : Token) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://corona-watch-esi.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val service = retrofit.create(DataService::class.java)

        val commentRequest = service.facbookauthen(t)


        commentRequest?.enqueue(object : Callback<TokenAnswer?> {
            override fun onFailure(call: Call<TokenAnswer?>, t: Throwable) {
                var k = t
                Toast.makeText(this@LoginActivity,"authentifing error",Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<TokenAnswer?>, response: Response<TokenAnswer?>) {
                Toast.makeText(this@LoginActivity,"token sent",Toast.LENGTH_SHORT).show()
                var t= response.body()


            }
        })
    }


    fun disconnectfromfb(){
        val accessToken = AccessToken.getCurrentAccessToken()
        val request = GraphRequest.newDeleteObjectRequest(accessToken, "me/permissions") {
            LoginManager.getInstance().logOut()
        }
        request.executeAsync()
    }

}
