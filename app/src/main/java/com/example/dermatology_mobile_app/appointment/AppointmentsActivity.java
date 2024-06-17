package com.example.dermatology_mobile_app.appointment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnoteapplication.R;
import com.example.newnoteapplication.db.AppointmentsDatabaseHelper;
import com.example.newnoteapplication.services.DermatologyServicesActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppointmentsActivity extends AppCompatActivity {

    private AppointmentsDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private AppointmentsAdapter adapter;
    private List<String> appointmentsList;
    private TextView totalCostTextView;
    private int totalCost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        dbHelper = new AppointmentsDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalCostTextView = findViewById(R.id.totalCostTextView);

        Button btnChangeColor = findViewById(R.id.btnChangeColor);
        btnChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackgroundColor();
            }
        });

        Button btnServices = findViewById(R.id.btnServices);
        Button btnAppointments = findViewById(R.id.btnAppointments);

        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentsActivity.this, DermatologyServicesActivity.class);
                startActivity(intent);
            }
        });

        btnAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Already on Appointments, so do nothing or refresh the list if needed
            }
        });

        loadAppointments();

        adapter = new AppointmentsAdapter(this, appointmentsList, totalCostTextView);
        recyclerView.setAdapter(adapter);

        calculateTotalCost();
    }

    private void loadAppointments() {
        Cursor cursor = dbHelper.getAllAppointments();
        appointmentsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String service = cursor.getString(cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_SERVICE));
            appointmentsList.add(service);
        }
        cursor.close();
    }

    private void calculateTotalCost() {
        Cursor cursor = dbHelper.getAllAppointments();
        totalCost = 0;
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int cost = cursor.getInt(cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_COST));
            totalCost += cost;
        }
        cursor.close();
        totalCostTextView.setText("Total: " + totalCost + " lei");
    }

    private void changeBackgroundColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        findViewById(R.id.activity_appointments).setBackgroundColor(color);
    }
}
