package com.mobileapp.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TimeSlotsAdapter extends RecyclerView.Adapter<TimeSlotsAdapter.TimeSlotViewHolder> {

    private List<TimeSlot> timeSlots;
    private int selectedPosition = -1; // Track the selected position

    public TimeSlotsAdapter(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_time_slot, parent, false);
        return new TimeSlotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        TimeSlot timeSlot = timeSlots.get(position);
        holder.bind(timeSlot, position);
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    public class TimeSlotViewHolder extends RecyclerView.ViewHolder {

        private TextView timeTextView;

        public TimeSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.timeTextView);

            timeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the position of the clicked time slot
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        // Update selected position
                        selectedPosition = position;

                        // Notify data set changed to apply the color change
                        notifyDataSetChanged();

                        // Notify the AddActivity of the selected time slot
                        String selectedTimeSlot = timeSlots.get(selectedPosition).getTime();
                        ((AddNewAppointment) v.getContext()).setSelectedTimeSlot(selectedTimeSlot);
                    }
                }
            });
        }

        public void bind(TimeSlot timeSlot, int position) {
            timeTextView.setText(timeSlot.getTime());

            // Check if the position is the selected one and change background color to grey
            if (position == selectedPosition) {
                timeTextView.setBackgroundColor(Color.GRAY);
            } else {
                timeTextView.setBackgroundColor(timeSlot.isOccupied() ?
                        Color.RED : Color.GREEN);
            }
        }
    }
}
