package com.mobileapp.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobileapp.myapplication.models.Appointment;
import com.mobileapp.myapplication.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNewAppointment extends AppCompatActivity {

    private DatePicker datePicker;
    private RecyclerView recyclerViewTimeSlots;
    private Button saveButton;

    private List<TimeSlot> timeSlots;
    private TimeSlotsAdapter adapter;

    private String selectedTimeSlot = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        datePicker = findViewById(R.id.datePicker);
        recyclerViewTimeSlots = findViewById(R.id.recyclerViewTimeSlots);
        saveButton = findViewById(R.id.bookButton);

        // Initialize the list of time slots
        timeSlots = new ArrayList<>();

        // Set up the RecyclerView and its adapter
        adapter = new TimeSlotsAdapter(timeSlots);
        recyclerViewTimeSlots.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTimeSlots.setAdapter(adapter);

        // Set a default date on the DatePicker
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle date selection and update time slots based on the selected date
                        updateTimeSlots(year, monthOfYear, dayOfMonth);
                    }
                });

        // Initial update of time slots with the current date
        updateTimeSlots(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTimeSlot != null) {
                    // A time slot is selected, print the date and time
                    String selectedDate = datePicker.getDayOfMonth() + "/" +
                            (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                    String message = "Adding new appointment at: " + selectedDate + " " + selectedTimeSlot;
                    Toast.makeText(AddNewAppointment.this, message, Toast.LENGTH_SHORT).show();
                    DocumentReference requestRef = FirebaseFirestore.getInstance().collection(getResources().getString(R.string.myreminderss_collection)).document();
                    Appointment appointment = new Appointment(Utils.getPref(AddNewAppointment.this, "user_id", ""),
                                                selectedDate, selectedTimeSlot);
                    requestRef.set(appointment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Utils.dismissProgressDialog();
                            if(task.isSuccessful()){
                                Utils.showToast(AddNewAppointment.this, "My reminder added successfully");
                                onBackPressed();
                            }else{
                                Utils.showToast(AddNewAppointment.this, task.getException().getLocalizedMessage());
                            }

                        }
                    });

                } else {
                    // No time slot selected, show an error message
                    Toast.makeText(AddNewAppointment.this, "Please select a time slot", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void updateTimeSlots(int year, int month, int day) {
        // Clear the existing time slots
        timeSlots.clear();

        // TODO: Query your data to determine occupied time slots for the selected date

        // Simulated example: Add some sample time slots
        for (int hour = 8; hour <= 16; hour++) {
            timeSlots.add(new TimeSlot(hour + ":00", false)); // Assume all time slots are available initially
        }

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }

    public void setSelectedTimeSlot(String timeSlot) {
        selectedTimeSlot = timeSlot;
    }
}
