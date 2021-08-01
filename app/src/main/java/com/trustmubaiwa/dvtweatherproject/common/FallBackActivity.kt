package com.trustmubaiwa.dvtweatherproject.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.trustmubaiwa.dvtweatherproject.view.MainActivity

class FallBackActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        startActivity(Intent(this, MainActivity::class.java))
    }
}