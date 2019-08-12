package io.github.owuor91.kotnots

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.mainActivityFab

class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onStart() {
    super.onStart()
    mainActivityFab.setOnClickListener {
      startActivity(Intent(this, AddNoteActivity::class.java))
    }
  }
}
