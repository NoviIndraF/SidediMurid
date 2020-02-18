package com.nifcompany.testopini2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Test extends AppCompatActivity {

    TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvHasil = findViewById(R.id.TvTest);

        tvHasil.setText(getIntent().getStringExtra("1"));
    }
}
