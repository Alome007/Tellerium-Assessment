package com.alome.tellerium.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alome.tellerium.Models.User
import com.alome.tellerium.R
import com.alome.tellerium.Utils.Helper
import com.gmail.samehadar.iosdialog.IOSDialog
import kotlinx.android.synthetic.main.login.*

class Login:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val email=email.text
        tool_bar.setNavigationOnClickListener{
            onBackPressed()
        }
        val password=password.text
        val helper= Helper(this)
        login.setOnClickListener{
            if (verify()){
                val iosdialog = IOSDialog.Builder(this@Login)
                        .setCancelable(false)
                        .setMessageContent("Please Wait")
                        .show()
                val handler = Handler()
                handler.postDelayed({
                    if (email.equals("test@tellerium.io")&&password.equals("password")){
                        val  User=User("test","test");
                        helper.loggedInUser=User
                        startActivity(Intent(this@Login, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"Invalid Details Provided",Toast.LENGTH_LONG).show()
                    }
                    iosdialog.dismiss()
                }, 3000)
            }
        }
    }



    private fun verify(): Boolean {
        val isVerified:Boolean
        if (email.text.isEmpty()){
            isVerified=false
            email.setError("Required")
        }else if (password.text.isEmpty()){
            isVerified=false
            email.setError("Required")
        }else{
            isVerified=true
        }
        return isVerified
    }


}
