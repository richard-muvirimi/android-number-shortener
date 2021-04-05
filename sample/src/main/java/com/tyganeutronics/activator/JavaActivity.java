package com.tyganeutronics.activator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

class JavaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> precisions = new ArrayList<>();
        //https://www.nist.gov/pml/weights-and-measures/metric-si-prefixes
        for (int i = 0; i <= 24; i++) {
            precisions.add(Integer.toString(i));
        }

        //precision
        AppCompatSpinner precisionSpinner = findViewById(R.id.as_precision);
        precisionSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, (String[]) precisions.toArray()));
        precisionSpinner.setSelection(getResources().getInteger(R.integer.default_precision));
        precisionSpinner.setOnItemSelectedListener(this);

        //Round
        AppCompatSpinner roundModeSpinner = findViewById(R.id.as_round);
        roundModeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.round)));
        roundModeSpinner.setOnItemSelectedListener(this);

        AppCompatCheckBox roundCheckBox = findViewById(R.id.cb_round);
        roundCheckBox.setOnCheckedChangeListener(this);

        Integer[] ids = new Integer[]{R.id.ed_suffix, R.id.ed_input};
        for (Integer id : ids) {
            TextInputEditText editText = findViewById(id);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    shorten();
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        shorten();
    }

    /**
     * The magic happens here
     */
    private void shorten() {
        String input = Objects.requireNonNull(((TextInputEditText) findViewById(R.id.ed_input)).getText()).toString();

        BigDecimal number = new BigDecimal(input.isEmpty() ? "0" : input);

        boolean round = ((AppCompatCheckBox) findViewById(R.id.cb_round)).isChecked();

        //precision
        int precision = Integer.parseInt(((AppCompatSpinner) findViewById(R.id.as_precision)).getSelectedItem().toString());

        //round
        int roundingMode = ((AppCompatSpinner) findViewById(R.id.as_round)).getSelectedItemPosition();

        //suffix
        String suffix = Objects.requireNonNull(((TextInputEditText) findViewById(R.id.ed_suffix)).getText()).toString();

        String shorter = NumberShort.INSTANCE.shorten(number, round, suffix, precision, RoundingMode.valueOf(roundingMode));

        ((TextInputEditText) findViewById(R.id.txt_output)).setText(shorter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        shorten();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        shorten();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        shorten();
    }
}