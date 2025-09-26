package com.example.lab_week_05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.lab_week_05.api.CatApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {

    // 1. Inisialisasi Retrofit secara "lazy" (dibuat saat pertama kali dibutuhkan)
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/") // Base URL dari API
            .addConverterFactory(ScalarsConverterFactory.create()) // Converter untuk respons String
            .build()
    }

    // 2. Buat instance dari service interface kita
    private val catApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }

    // 3. Dapatkan referensi ke TextView dari layout
    private val apiResponseView: TextView by lazy {
        findViewById(R.id.api_response)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 4. Panggil fungsi untuk mengambil data saat activity dibuat
        getCatImageResponse()
    }

    // 5. Fungsi untuk melakukan panggilan API
    private fun getCatImageResponse() {
        val call = catApiService.searchImages(1, "full")

        // 6. Jalankan panggilan secara asynchronous (di background)
        call.enqueue(object : Callback<String> {
            // 7. Callback jika GAGAL
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(MAIN_ACTIVITY, "Failed to get response", t)
            }

            // 8. Callback jika BERHASIL
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    // Tampilkan body respons ke TextView
                    apiResponseView.text = response.body()
                } else {
                    Log.e(MAIN_ACTIVITY, "Failed to get response\n" +
                            response.errorBody()?.string().orEmpty())
                }
            }
        })
    }

    // 9. Companion object untuk konstanta (logging tag)
    companion object {
        const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    }
}