package com.tyganeutronics.activator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.tyganeutronics.numbershortener.NumberShort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;

public class JavaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

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
        precisionSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, precisions.toArray()));
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

        findViewById(R.id.btn_privacy_policy).setOnClickListener(this);

        applyWindowInsets();

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

        ((AppCompatTextView) findViewById(R.id.txt_output)).setText(shorter);
    }

    @Override
    public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
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

    @Override
    public void onClick(View view) {
        if (view != null) {
            if (view.getId() == R.id.btn_privacy_policy) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.privacy_policy_url)));
                startActivity(intent);
            }
        }
    }

    public void applyWindowInsets( ) {

        // https://developer.android.com/develop/ui/views/layout/edge-to-edge#kotlin
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_container), new  OnApplyWindowInsetsListener(){
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat windowInsets) {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();

                params.leftMargin = insets.left;
                params.topMargin = insets.top;
                params.bottomMargin = insets.bottom;
                params.rightMargin = insets.right;

                v.setLayoutParams(params);

                return WindowInsetsCompat.CONSUMED;
            }
        });
    }
}