package com.example.dermatology_mobile_app.appointment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newnoteapplication.R;
import com.example.newnoteapplication.db.AppointmentsDatabaseHelper;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {

    private List<String> appointmentsList;
    private AppointmentsDatabaseHelper dbHelper;
    private Context context;
    private TextView totalCostTextView;

    public AppointmentsAdapter(Context context, List<String> appointmentsList, TextView totalCostTextView) {
        this.appointmentsList = appointmentsList;
        this.dbHelper = new AppointmentsDatabaseHelper(context);
        this.context = context;
        this.totalCostTextView = totalCostTextView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String service = appointmentsList.get(position);
        int cost = dbHelper.getServiceCost(service);
        holder.tvServiceName.setText(service);
        holder.tvServiceCost.setText(cost + " lei");
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteAppointment(service);
                appointmentsList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, appointmentsList.size());
                updateTotalCost();
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvServiceName;
        public TextView tvServiceCost;
        public Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            tvServiceCost = itemView.findViewById(R.id.tvServiceCost);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    private void updateTotalCost() {
        Cursor cursor = dbHelper.getAllAppointments();
        int totalCost = 0;
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int cost = cursor.getInt(cursor.getColumnIndex(AppointmentsDatabaseHelper.COLUMN_COST));
            totalCost += cost;
        }
        cursor.close();
        totalCostTextView.setText("Costul serviciilor: " + totalCost + " lei");
    }
}
