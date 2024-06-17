package com.example.dermatology_mobile_app.services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnoteapplication.R;
import com.example.newnoteapplication.appointment.AppointmentsActivity;

import java.util.ArrayList;
import java.util.List;

public class DermatologyServicesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServicesAdapter adapter;
    private List<String> servicesList;
    private List<Integer> servicesCosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dermatology_services);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        servicesList = new ArrayList<>();
        servicesCosts = new ArrayList<>();

        servicesList.add("Consultation");
        servicesCosts.add(200);

        servicesList.add("Skin Biopsy");
        servicesCosts.add(500);

        servicesList.add("Laser Treatment");
        servicesCosts.add(1000);

        servicesList.add("Acne Treatment");
        servicesCosts.add(300);

        servicesList.add("Eczema Treatment");
        servicesCosts.add(400);

        servicesList.add("Extraction");
        servicesCosts.add(600);

        servicesList.add("Facial");
        servicesCosts.add(900);

        adapter = new ServicesAdapter(this, servicesList, servicesCosts);
        recyclerView.setAdapter(adapter);

        Button btnServices = findViewById(R.id.btnServices);
        Button btnAppointments = findViewById(R.id.btnAppointments);

        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Este deja in aceasta activitate
            }
        });

        btnAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DermatologyServicesActivity.this, AppointmentsActivity.class);
                startActivity(intent);
            }
        });
    }
}