package com.example.retrofitapiapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.retrofitapiapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//https://meme-api.com/gimme

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()

        binding.button.setOnClickListener {
            getData()
        }
    }

    private fun getData() {

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading Please Wait")
        progressDialog.show()

        RetrofitInstance.apiInterface.getData().enqueue(object : Callback<ResponseDataClass?> {
            override fun onResponse(
                call: Call<ResponseDataClass?>,
                response: Response<ResponseDataClass?>
            ) {

                binding.Title.text=response.body()?.title
                Glide.with(this@MainActivity).load(response.body()?.url).into(binding.imageView);

                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseDataClass?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        })
    }
}