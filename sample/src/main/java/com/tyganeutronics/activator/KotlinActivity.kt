package com.tyganeutronics.activator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.tyganeutronics.numbershortener.shorten
import java.math.RoundingMode

class KotlinActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Precision
        findViewById<AppCompatSpinner>(R.id.as_precision).apply {
            adapter = ArrayAdapter(
                this@KotlinActivity,
                android.R.layout.simple_list_item_1,
                //https://www.nist.gov/pml/weights-and-measures/metric-si-prefixes
                (0..24).map { it.toString() })
            setSelection(resources.getInteger(R.integer.default_precision))
            onItemSelectedListener = this@KotlinActivity
        }

        //Round
        findViewById<AppCompatSpinner>(R.id.as_round).apply {
            adapter = ArrayAdapter(
                this@KotlinActivity,
                android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.round)
            )
            onItemSelectedListener = this@KotlinActivity
        }

        findViewById<AppCompatCheckBox>(R.id.cb_round).apply {
            setOnCheckedChangeListener(this@KotlinActivity)
        }

        listOf(R.id.ed_suffix, R.id.ed_input).forEach { id ->
            findViewById<TextInputEditText>(id).apply {
                addTextChangedListener { shorten() }
            }
        }

        findViewById<AppCompatButton>(R.id.btn_privacy_policy).setOnClickListener(this)


        applyWindowInsets()
    }

    override fun onStart() {
        super.onStart()

        shorten()
    }

    /**
     * The magic happens here
     */
    private fun shorten() {
        val number = findViewById<TextInputEditText>(R.id.ed_input)
            .text
            .toString()
            .ifEmpty { "0" }
            .toBigDecimal()

        val round = findViewById<AppCompatCheckBox>(R.id.cb_round)
            .isChecked

        //precision
        val precision = findViewById<AppCompatSpinner>(R.id.as_precision)
            .selectedItem
            .toString()
            .toInt()

        //round
        val roundingMode = findViewById<AppCompatSpinner>(R.id.as_round)
            .selectedItemPosition

        //suffix
        val suffix = findViewById<TextInputEditText>(R.id.ed_suffix)
            .text
            .toString()

        findViewById<AppCompatTextView>(R.id.txt_output).text = number
            .shorten(
                round = round,
                precision = precision,
                suffix = suffix,
                roundMode = RoundingMode.valueOf(roundingMode)
            )
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        shorten()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        shorten()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_privacy_policy -> {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = getString(R.string.privacy_policy_url).toUri()
                    startActivity(intent)
                }
            }
        }
    }

    fun applyWindowInsets() {

        // https://developer.android.com/develop/ui/views/layout/edge-to-edge#kotlin
        ViewCompat.setOnApplyWindowInsetsListener(findViewById<ScrollView>(R.id.layout_container)) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                topMargin = insets.top
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }

            WindowInsetsCompat.CONSUMED
        }
    }

}