package com.example.dermatology_mobile_app.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnoteapplication.R;
import com.example.newnoteapplication.db.AppointmentsDatabaseHelper;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private List<String> servicesList;
    private List<Integer> servicesCosts;
    private Context context;
    private AppointmentsDatabaseHelper dbHelper;

    public ServicesAdapter(Context context, List<String> servicesList, List<Integer> servicesCosts) {
        this.servicesList = servicesList;
        this.servicesCosts = servicesCosts;
        this.context = context;
        this.dbHelper = new AppointmentsDatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String service = servicesList.get(position);
        int cost = servicesCosts.get(position);
        holder.serviceName.setText(service);
        holder.serviceCost.setText(cost + " lei");
        holder.btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = dbHelper.addAppointment(service, cost);
                if (result == -1) {
                    Toast.makeText(context, "This service has already been booked.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Appointment booked successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceName;
        public TextView serviceCost;
        public Button btnBookAppointment;
        public Button btnIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            serviceCost = itemView.findViewById(R.id.serviceCost);
            btnBookAppointment = itemView.findViewById(R.id.btnBookAppointment);
            btnIcon = itemView.findViewById(R.id.btnIcon);
        }
    }
}
