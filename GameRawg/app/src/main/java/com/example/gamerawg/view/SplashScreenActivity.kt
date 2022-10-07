package com.example.gamerawg.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.gamerawg.databinding.ActivitySplashScreenBinding
import com.example.gamerawg.viewmodel.SplashViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observe()
        viewModel.getPlatforms()
        viewModel.getFirstList()
    }

    fun observe() {
        viewModel.nextByList.observe(this, Observer {
            if (it && viewModel.nextByPlatform.value == true) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("nextPageUrl", viewModel.nextUrl.value)
                intent.putExtra("games", Gson().toJson(viewModel.platform.value))
                intent.putExtra("firstList", Gson().toJson(viewModel.list.value))
                startActivity(intent)
                finish()
            }
        })

        viewModel.nextByPlatform.observe(this, Observer {
            it?.let {
                if (it && viewModel.nextByList.value == true) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("nextPageUrl", viewModel.nextUrl.value)
                    intent.putExtra("platforms", Gson().toJson(viewModel.platform.value))
                    intent.putExtra("firstList", Gson().toJson(viewModel.list.value))
                    startActivity(intent)
                    finish()
                }
            }
        })
    }
}