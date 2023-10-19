package com.example.ventilatormonitoring.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ventilatormonitoring.R;

public class Settings extends AppCompatActivity {

    private CheckBox checkBoxNotifications;
    private CheckBox checkBoxDarkMode;
    private Button btnSaveSettings;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);


        checkBoxNotifications = findViewById(R.id.checkBoxNotifications);
        checkBoxDarkMode = findViewById(R.id.checkBoxDarkMode);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);



        checkBoxNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle checkbox state change
        });

        checkBoxDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Handle checkbox state change
        });

        btnSaveSettings.setOnClickListener(v -> {
            // Handle save button click
            String email = editTextEmail.getText().toString();
            // Save settings
        });


    }
}
