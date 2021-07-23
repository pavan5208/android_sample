package com.loco.movie.list.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loco.movie.list.R
import com.loco.movie.list.utils.setStatusBarColor
import java.lang.ref.WeakReference


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupViews()
    }

    fun setupViews() {
        setStatusBarColor(R.color.black)
        val m_runnable_thread = Runnable {
            try {
                Thread.sleep(1500)
                navigateToMovieList()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        try {
            val t = Thread(null, m_runnable_thread)
            t.start()
        } catch (e: Exception) {
        }
    }


    private fun navigateToMovieList() {
        MovieListActivity.startActivity(WeakReference(this))
        finish()
    }


}