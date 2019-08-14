package io.github.owuor91.kotnots.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onStart() {
    super.onStart()
  }

  override fun onResume() {
    super.onResume()
  }

  fun setupToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowTitleEnabled(false)
  }
}