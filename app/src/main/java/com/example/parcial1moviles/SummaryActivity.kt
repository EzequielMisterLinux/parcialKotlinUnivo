package com.example.parcial1moviles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.TextView

class SummaryActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var tvResumen: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        toolbar = findViewById(R.id.toolbar)
        tvResumen = findViewById(R.id.tvResumen)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Resumen del Pedido"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val resumen = intent.getStringExtra("resumen")
        tvResumen.text = resumen
    }
}
