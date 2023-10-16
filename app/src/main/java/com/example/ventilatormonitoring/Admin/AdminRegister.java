package com.example.ventilatormonitoring.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ventilatormonitoring.Admin.AdminLoginActivity;
import com.example.ventilatormonitoring.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.regex.Pattern;

public class AdminRegister extends AppCompatActivity {

    EditText email, pass1, pass2, et_username, et_phone;
    Button btn_Register;
    TextView tv_loginBtn;

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    Pattern pat = Pattern.compile(emailRegex);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        tv_loginBtn = findViewById(R.id.tv_loginButton);

        et_username = findViewById(R.id.et_username);
        email = findViewById(R.id.et_email);
        pass1 = findViewById(R.id.et_password);
        pass2 = findViewById(R.id.et_confirmPassword);
        btn_Register = findViewById(R.id.btn_register);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        tv_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminRegister.this, AdminLoginActivity.class));
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputCheckFun(email, pass1, pass2);
            }
        });
    }

    private void InputCheckFun(EditText email, EditText pass, EditText pass2) {
        String s_email, s_pass, s_pass2;

        s_email = email.getText().toString();
        s_pass = pass.getText().toString();
        s_pass2 = pass2.getText().toString();

        if (s_email.isEmpty() || TextUtils.isEmpty(s_pass) || TextUtils.isEmpty(s_pass2)) {
            Toast.makeText(this, "Something is wrong", Toast.LENGTH_SHORT).show();
        } else {
            addUserToFirestore(s_email, s_pass);
        }
    }

    private void addUserToFirestore(String name, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        User user = new User(name, email);

        DocumentReference userRef = db.collection("Admins").document("admin123");
        userRef.set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminRegister.this, "Check Your Email id", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AdminLoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AdminRegister.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private static class User {
        private String username;
        private String email;

        public User(String name, String email) {
            this.username = name;
            this.email = email;
        }
    }
}
