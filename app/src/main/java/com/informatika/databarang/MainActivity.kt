package com.informatika.databarang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.informatika.databarang.adapter.ListContent
import com.informatika.databarang.model.ResponseBarang
import com.informatika.databarang.model.ResponseUserItem
import com.informatika.databarang.network.koneksi
import com.informatika.databarang.service.SessionPreferences
import kotlinx.android.synthetic.main.activity_update_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
private lateinit var sessionPreferences : SessionPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionPreferences = SessionPreferences(this)
        cekSession()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val i = Intent(this, InsertDataActivity :: class.java)
            startActivity(i)
        }
        getData()
    }
    fun cekSession(){
        sessionPreferences = SessionPreferences(this)
        val userName = sessionPreferences.getUserName()
        if (userName == null){
            val i = Intent(this,LoginActivity :: class.java)
            startActivity(i)
            finish()
        }
    }

    public fun getData() {
        koneksi.service.getBarang().enqueue(object : Callback<ResponseBarang> {
            override fun onFailure(call: Call<ResponseBarang>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseBarang>,
                response: Response<ResponseBarang>
            ) {
                if (response.isSuccessful) {
                    val dataBody = response.body()
                    val datacontent = dataBody!!.data

                    val rvAdapter = ListContent(datacontent, this@MainActivity)
                    rvAdapter.notifyDataSetChanged()

                    rv_data_barang.apply {
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the Menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return true

    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_settings -> true
                else -> super.onOptionsItemSelected(item)
            }
        }
    }