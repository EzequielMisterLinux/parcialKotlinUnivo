package com.example.parcial1moviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var rgTamanoCafe: RadioGroup
    private lateinit var cbCremaExtra: CheckBox
    private lateinit var cbAzucarExtra: CheckBox
    private lateinit var cbSinCafeina: CheckBox
    private lateinit var btnOrdenar: Button
    private lateinit var tvResumenPedido: TextView
    private lateinit var tvPrecio: TextView

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_main)
            initializeViews()
            setupToolbar()
            setupListeners()
            handleEdgeToEdgeLayout()
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate: ${e.message}", e)
            showToast("Error initializing app: ${e.message}")
            finish()
        }
    }

    private fun initializeViews() {
        try {
            toolbar = findViewById(R.id.toolbar)
            rgTamanoCafe = findViewById(R.id.rgTamanoCafe)
            cbCremaExtra = findViewById(R.id.cbCremaExtra)
            cbAzucarExtra = findViewById(R.id.cbAzucarExtra)
            cbSinCafeina = findViewById(R.id.cbSinCafeina)
            btnOrdenar = findViewById(R.id.btnOrdenar)
            tvResumenPedido = findViewById(R.id.tvResumenPedido)
            tvPrecio = findViewById(R.id.tvPrecio)
        } catch (e: Exception) {
            Log.e(TAG, "Error in initializeViews: ${e.message}", e)
            throw e
        }
    }

    private fun setupToolbar() {
        try {
            setSupportActionBar(toolbar)
            supportActionBar?.title = "Café App"
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setLogo(R.drawable.logo)
            supportActionBar?.setDisplayUseLogoEnabled(true)
        } catch (e: Exception) {
            Log.e(TAG, "Error in setupToolbar: ${e.message}", e)
            throw e
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupListeners() {
        try {
            btnOrdenar.setOnClickListener {
                if (validateInput()) {
                    processOrder()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in setupListeners: ${e.message}", e)
            showToast("Error setting up listeners: ${e.message}")
        }
    }

    private fun validateInput(): Boolean {
        return when {
            rgTamanoCafe.checkedRadioButtonId == -1 -> {
                showToast("Por favor, seleccione un tamaño de café")
                false
            }
            else -> true
        }
    }

    private fun processOrder() {
        try {
            val tamanoCafe = when (rgTamanoCafe.checkedRadioButtonId) {
                R.id.rbPequeno -> "Pequeño"
                R.id.rbMediano -> "Mediano"
                R.id.rbGrande -> "Grande"
                else -> "Desconocido"
            }

            val extras = mutableListOf<String>()
            if (cbCremaExtra.isChecked) extras.add("Crema extra")
            if (cbAzucarExtra.isChecked) extras.add("Azúcar extra")
            if (cbSinCafeina.isChecked) extras.add("Sin cafeína")

            if (tamanoCafe == "Desconocido") {
                showToast("Por favor, seleccione un tamaño de café")
                return
            }

            val precio = calculatePrice(tamanoCafe, extras)
            val precioFormateado = String.format("%.2f", precio)

            val resumen = buildString {
                append("Resumen del pedido:\n")
                append("Tamaño: $tamanoCafe\n")
                if (extras.isNotEmpty()) {
                    append("Extras: ${extras.joinToString(", ")}\n")
                } else {
                    append("Sin extras\n")
                }
                append("Precio: $$precioFormateado")
            }

            val intent = Intent(this, SummaryActivity::class.java)
            intent.putExtra("resumen", resumen)
            startActivity(intent)

        } catch (e: Exception) {
            Log.e(TAG, "Error in processOrder: ${e.message}", e)
            showToast("Error processing order: ${e.message}")
        }
    }




    private fun calculatePrice(tamano: String, extras: List<String>): Double {
        var basePrice = when (tamano) {
            "Pequeño" -> 2.50
            "Mediano" -> 3.00
            "Grande" -> 3.50
            else -> 0.0
        }


        if ("Crema extra" in extras) basePrice += 0.50
        if ("Azúcar extra" in extras) basePrice += 0.30
        if ("Sin cafeína" in extras) basePrice += 0.40

        return basePrice
    }




    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun handleEdgeToEdgeLayout() {
        try {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
                val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom)
                insets
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error in handleEdgeToEdgeLayout: ${e.message}", e)
        }
    }
}
