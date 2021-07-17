package com.informatika.databarang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.informatika.databarang.network.koneksi
import com.informatika.databarang.service.SessionPreferences
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionPreferences: SessionPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            windowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        btn_submit.setOnClickListener {
            val userName = et_username.text.toString()
            val password = et_password.text.toString()

            if (userName.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Form Tidak Boleh Kosong!", Toast.LENGTH_LONG).show()
            }else{
                actionData(userName,password)
            }
        }
        btn_clean.setOnClickListener {
            formClear()
        }
        tv_disini.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    fun actionData(username: String, password : String){
        koneksi.service.logIn(username, password).enqueue(object : Callback<ResponseAdmin>){
            if(response.isSuccessful){
                val resbody = response.body()
                val resStatus = resbody?.status
                val resUserName = resbody?.data?.get(0)?.username
                Log.d("pesan", resUserName.toString())
                if(resStatus == true){
                    sessionPreferences = SessionPreferences(this@LoginActivity)
                    sessionPreferences.actionLogin(resUserName.toString())
                    val i = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }else if(resStatus == false){
                    Toast.makeText(this@LoginActivity, "Username atau Password Anda Salah!", Toast.LENGTH_LONG).show()
                }


            }
        }
    }
}

fun formClear(){
    et_username.text.clear()
    et_password.text.clear()
}