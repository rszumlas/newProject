// CHANGE PACKAGE NAME
package com.example.PROJECTNAME.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
  
  // IMPORT BY ALT+ENTER
  private var binding: ActivityMainBinding by autoRelease()
  
  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

}
