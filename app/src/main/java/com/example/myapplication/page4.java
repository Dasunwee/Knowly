package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class page4 extends AppCompatActivity {
    private Button login1;
    private Button createAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);

        // Login button action
        login1 = findViewById(R.id.btn_login);
        login1.setOnClickListener(v -> {
            Intent intent = new Intent(page4.this, page5.class);
            startActivity(intent);
        });

        // Create account button action
        createAcc = findViewById(R.id.btn_create_account);
        createAcc.setOnClickListener(v -> {
            Intent intent = new Intent(page4.this, page2.class);
            startActivity(intent);
        });
    }
}
