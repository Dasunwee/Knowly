package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.util.Log;  // <-- Add this for debugging

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class page8 extends AppCompatActivity {
    private ImageButton profile;
    private Button resourcedetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page8); // Make sure this layout contains cyberSecurityButton

        // Find the Cyber Security Button
        resourcedetails = findViewById(R.id.cyberSecurityButton);

        if (resourcedetails != null) {
            resourcedetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(page8.this, page10.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.e("page8", "❌ Error: cyberSecurityButton not found in activity_page8.xml");
        }
    }
}
