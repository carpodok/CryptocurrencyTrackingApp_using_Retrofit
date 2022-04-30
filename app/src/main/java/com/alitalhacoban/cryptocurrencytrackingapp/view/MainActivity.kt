package com.alitalhacoban.cryptocurrencytrackingapp.view

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alitalhacoban.cryptocurrencytrackingapp.R
import com.alitalhacoban.cryptocurrencytrackingapp.adapter.RecyclerViewAdapter
import com.alitalhacoban.cryptocurrencytrackingapp.databinding.ActivityMainBinding
import com.alitalhacoban.cryptocurrencytrackingapp.model.CryptoModel
import com.alitalhacoban.cryptocurrencytrackingapp.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val BASE_URL = "https://api.nomics.com/v1/"
    private var models: ArrayList<CryptoModel>? = null

    private var compositeDisposable : CompositeDisposable? = null


    private lateinit var recyclerView : RecyclerView

    lateinit var adapter : RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compositeDisposable = CompositeDisposable()


        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)

        binding.recyclerView.layoutManager = layoutManager



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
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)


        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

    }


    private fun handleResponse(cryptoList : List<CryptoModel>){
        models = ArrayList(cryptoList)

        models?.let {
            adapter = RecyclerViewAdapter(it)
            recyclerView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}