package com.example.movieinformation1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputBinding
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.movieinformation1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        const val TOKEN = "3a145e20"

    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSearch.setOnClickListener {
                if (etSearch.text.toString().isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        "please fill this  input ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    cardMovie.visibility = View.INVISIBLE
                    val queue = Volley.newRequestQueue(this@MainActivity)
                    val url =
                        "https://omdbapi.com/?apikey=" + TOKEN + "&t=" + etSearch.text.toString()

                    val jsonObjectRequest = JsonObjectRequest(url, { response ->
                        cardMovie.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        val title = response.getString("Title")
                        val Plot = response.getString("Plot")
                        val Poster = response.getString("Poster")

                        Glide.with(this@MainActivity)
                            .load(Poster)
                            .into(movieCover)

                        movieTitle.text = title.toString()
                        movieDescription.text = Plot.toString()
                    }, { error ->
                        progress.visibility = View.GONE
                        cardMovie.visibility = View.INVISIBLE
                        Toast.makeText(
                            this@MainActivity,
                            "error is : " + error.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                    queue.add(jsonObjectRequest)


                }
            }
        }
    }
}