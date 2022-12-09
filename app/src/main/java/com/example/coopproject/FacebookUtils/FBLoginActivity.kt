package com.example.coopproject.FacebookUtils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class FBLoginActivity : ComponentActivity() {

    private val callbackManager: CallbackManager by lazy {
        CallbackManager.Factory.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLogin()
        doLogin()
    }

    private fun doLogin() {
        Log.d("DEBUGMSG","DOING LOGIN")
        LoginManager.getInstance().logInWithReadPermissions(
            this,
            listOf("public_profile")
        )
    }

    private fun setupLogin() {
        Log.d("DEBUGMSG", "Setting up login")
        LoginManager.getInstance()
            .registerCallback(callbackManager, callback = object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Log.d("TAG++", "facebook login cancelled")
                    setResult(RESULT_CANCELED)
                    finish()
                }

                override fun onError(error: FacebookException) {
                    Log.d("TAG++", "facebook login error")
                    setResult(RESULT_ERROR)
                    finish()
                }

                override fun onSuccess(result: LoginResult) {
                    Log.d("TAG++", "facebook login success")
                    val intent = Intent().apply {
                        putExtra(EXTRA_DATA_FB, result.accessToken)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val RESULT_ERROR = 102
        const val EXTRA_DATA_FB = "extraDataFb"
        fun getInstance(context: Context): Intent {
            return Intent(context, FBLoginActivity::class.java)
        }
    }
}