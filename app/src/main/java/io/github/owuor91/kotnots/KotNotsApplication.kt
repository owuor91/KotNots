package io.github.owuor91.kotnots

import android.app.Application
import com.facebook.stetho.Stetho

class KotNotsApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    Stetho.initializeWithDefaults(this)
  }
  
}