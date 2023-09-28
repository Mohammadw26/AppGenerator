package com.mobileapp.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobileapp.myapplication.models.Appointment;
import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.AppointmentViewHolder> {

    private List<Appointment> appointmentList;

    public AppointmentsAdapter(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.bind(appointment);
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder {

        private TextView appointmentDateTextView;
        private TextView appointmentTimeTextView;
        private TextView appointmentDescriptionTextView;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            appointmentDateTextView = itemView.findViewById(R.id.appointmentDateTextView);
            appointmentTimeTextView = itemView.findViewById(R.id.appointmentTimeTextView);
            appointmentDescriptionTextView = itemView.findViewById(R.id.appointmentDescriptionTextView);
        }

        public void bind(Appointment appointment) {
            // Bind appointment data to UI elements
            appointmentDateTextView.setText(appointment.getDate());
            appointmentTimeTextView.setText(appointment.getTime());
            appointmentDescriptionTextView.setText(appointment.getDescription());
        }
    }
}
