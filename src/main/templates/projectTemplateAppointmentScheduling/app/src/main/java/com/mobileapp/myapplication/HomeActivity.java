package com.mobileapp.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mobileapp.myapplication.utils.PermissionsManager;
import com.mobileapp.myapplication.utils.Utils;


public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseFirestore.getInstance().collection(getResources().getString(R.string.users_collection))
                .document(Utils.getPref(HomeActivity.this, "user_id", "")).addSnapshotListener(
                        new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value != null && value.exists()) {
                                    if((boolean) value.getData().get("request_noti")){
                                        findViewById(R.id.cv_requestsnoti_home).setVisibility(View.VISIBLE);
                                    }else{
                                        findViewById(R.id.cv_requestsnoti_home).setVisibility(View.GONE);

                                    }
                                    if((boolean) value.getData().get("chat_noti")){
                                        findViewById(R.id.cv_mychatsnoti_home).setVisibility(View.VISIBLE);
                                    }else{
                                        findViewById(R.id.cv_mychatsnoti_home).setVisibility(View.GONE);

                                    }
                                    if((boolean) value.getData().get("myrequest_noti")){
                                        findViewById(R.id.cv_myrequestsnoti_home).setVisibility(View.VISIBLE);
                                    }else{
                                        findViewById(R.id.cv_myrequestsnoti_home).setVisibility(View.GONE);

                                    }
                                }
                            }
                        }
                );


        findViewById(R.id.home_btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        findViewById(R.id.home_btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PermissionsManager.isLocationPermissionsEnabled(HomeActivity.this)){
                    if(Utils.isLocationEnabled(HomeActivity.this)){
                        startActivity(new Intent(HomeActivity.this, AddNewAppointment.class));
                    }else{
                        Utils.showToast(HomeActivity.this, "Enable location from the settings");
                    }
                }else {
                    requestPermissions(PermissionsManager.LOCATION_PERMISSIONS, PermissionsManager.LOCATION_CODE);
                }

            }
        });


        findViewById(R.id.home_btn_myreminders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MyAppointmentsActivity.class));
            }
        });


    }

}