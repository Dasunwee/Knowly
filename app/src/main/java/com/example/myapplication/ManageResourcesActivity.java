package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageResourcesActivity extends AppCompatActivity {

    private EditText etResourceName, etResourceLink;
    private Button btnAddResource;
    private ListView listViewResources;
    private ResourceAdapter resourceAdapter;
    private ArrayList<Resource> resourceList;

    private FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_resources);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI elements
        etResourceName = findViewById(R.id.etResourceName);
        etResourceLink = findViewById(R.id.etResourceLink);
        btnAddResource = findViewById(R.id.btnAddResource);
        listViewResources = findViewById(R.id.listViewResources);

        // Initialize resource list and adapter
        resourceList = new ArrayList<>();
        resourceAdapter = new ResourceAdapter(this, resourceList);
        listViewResources.setAdapter(resourceAdapter);

        // Load resources from Firestore
        loadResources();

        // Add resource button click listener
        btnAddResource.setOnClickListener(v -> addResource());
    }

    private void loadResources() {
        firestore.collection("resources")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        resourceList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String name = document.getString("name");
                            String link = document.getString("link");
                            String icon = document.getString("icon"); // You can store icon URLs in Firestore
                            resourceList.add(new Resource(id, name, link, icon));
                        }
                        resourceAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Failed to load resources.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addResource() {
        String name = etResourceName.getText().toString().trim();
        String link = etResourceLink.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Resource name is required.", Toast.LENGTH_SHORT).show();
        } else if (link.isEmpty()) {
            Toast.makeText(this, "Resource link is required.", Toast.LENGTH_SHORT).show();
        } else {
            // Create a new resource object
            Map<String, Object> resource = new HashMap<>();
            resource.put("name", name);
            resource.put("link", link);
            resource.put("icon", "default_icon_url"); // Replace with actual icon URL or logic

            // Add resource to Firestore
            firestore.collection("resources")
                    .add(resource)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Resource added successfully!", Toast.LENGTH_SHORT).show();
                        etResourceName.setText("");
                        etResourceLink.setText("");
                        loadResources(); // Refresh the list
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to add resource: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    public void deleteResource(String resourceId) {
        firestore.collection("resources")
                .document(resourceId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Resource deleted successfully!", Toast.LENGTH_SHORT).show();
                    loadResources(); // Refresh the list
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete resource: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}