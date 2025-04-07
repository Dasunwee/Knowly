package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    private String editingResourceId = null; // Track currently editing resource

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

        // Add or Update button logic
        btnAddResource.setOnClickListener(v -> {
            if (editingResourceId != null) {
                updateResource();
            } else {
                addResource();
            }
        });
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
                            String icon = document.getString("icon"); // Optional
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
            Map<String, Object> resource = new HashMap<>();
            resource.put("name", name);
            resource.put("link", link);

            firestore.collection("resources")
                    .add(resource)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Resource added successfully!", Toast.LENGTH_SHORT).show();
                        resetForm();
                        loadResources();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to add resource: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void updateResource() {
        String updatedName = etResourceName.getText().toString().trim();
        String updatedLink = etResourceLink.getText().toString().trim();

        if (updatedName.isEmpty()) {
            Toast.makeText(this, "Resource name is required.", Toast.LENGTH_SHORT).show();
        } else if (updatedLink.isEmpty()) {
            Toast.makeText(this, "Resource link is required.", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("name", updatedName);
            updatedData.put("link", updatedLink);

            firestore.collection("resources")
                    .document(editingResourceId)
                    .update(updatedData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Resource updated successfully!", Toast.LENGTH_SHORT).show();
                        resetForm();
                        loadResources();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    public void deleteResource(String resourceId) {
        firestore.collection("resources")
                .document(resourceId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Resource deleted successfully!", Toast.LENGTH_SHORT).show();
                    loadResources();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete resource: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void editResource(Resource resource) {
        editingResourceId = resource.getId();
        etResourceName.setText(resource.getName());
        etResourceLink.setText(resource.getLink());
        btnAddResource.setText("Update Resource");
        etResourceName.requestFocus();
    }

    private void resetForm() {
        editingResourceId = null;
        etResourceName.setText("");
        etResourceLink.setText("");
        btnAddResource.setText("Add Resource");
    }
}
