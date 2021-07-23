package com.loco.movie.list.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.loco.movie.list.R
import com.loco.movie.list.ui.viewmodels.MovieListViewModel
import dagger.android.support.DaggerAppCompatActivity
import java.lang.ref.WeakReference
import javax.inject.Inject

class MovieListActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        fun startActivity(activityWeakReference: WeakReference<Activity>) {
            val intent = Intent(activityWeakReference.get(), MovieListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            activityWeakReference.get()?.startActivity(intent)
        }
    }

}