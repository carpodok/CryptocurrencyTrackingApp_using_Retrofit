package com.alitalhacoban.cryptocurrencytrackingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alitalhacoban.cryptocurrencytrackingapp.R
import com.alitalhacoban.cryptocurrencytrackingapp.adapter.RecyclerViewAdapter
import com.alitalhacoban.cryptocurrencytrackingapp.databinding.ActivityMainBinding
import com.alitalhacoban.cryptocurrencytrackingapp.model.CryptoModel
import com.alitalhacoban.cryptocurrencytrackingapp.service.CryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val BASE_URL = "https://api.nomics.com/v1/"
    private var models: ArrayList<CryptoModel>? = null

    private lateinit var recyclerView : RecyclerView

    lateinit var adapter : RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recycleView)


        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager

        loadData()

        binding.searchView.setOnQueryTextListener (object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue( object : Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                response.body()?.let {
                    models = ArrayList(it)

                    models?.let {
                        adapter = RecyclerViewAdapter(it)
                        recyclerView.adapter = adapter
                    }

                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }


            })
    }
}