package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ResourceAdapter extends ArrayAdapter<Resource> {

    private Context context;
    private List<Resource> resourceList;

    public ResourceAdapter(Context context, List<Resource> resourceList) {
        super(context, 0, resourceList);
        this.context = context;
        this.resourceList = resourceList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_resource, parent, false);
        }

        Resource resource = resourceList.get(position);

        TextView tvResourceName = convertView.findViewById(R.id.tvResourceName);
        TextView tvResourceLink = convertView.findViewById(R.id.tvResourceLink);
        Button btnDeleteResource = convertView.findViewById(R.id.btnDeleteResource);

        tvResourceName.setText(resource.getName());
        tvResourceLink.setText(resource.getLink());

        // Delete button click listener
        btnDeleteResource.setOnClickListener(v -> {
            ((ManageResourcesActivity) context).deleteResource(resource.getId());
        });

        return convertView;
    }
}