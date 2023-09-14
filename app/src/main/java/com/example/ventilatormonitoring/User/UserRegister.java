package com.example.ventilatormonitoring.User;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ventilatormonitoring.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class UserRegister extends AppCompatActivity {

    public static final String USERS = "User";
    private static final String USER_NAME_KEY = "user_name"; // SharedPreferences key for user's name

    EditText email, pass1, pass2, et_username, et_phone;
    Button btn_Register;
    TextView tv_loginBtn;

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    String userId = "";
    String username = "";

    TextView log_in;

    Pattern pat = Pattern.compile(emailRegex);

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        log_in = (TextView) findViewById(R.id.tv_loginButton);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent streamIntent = new Intent(UserRegister.this, UserLoginActivity.class);
                startActivity(streamIntent);
            }
        });

        et_username = findViewById(R.id.us_username);
        email = findViewById(R.id.et_email);
        pass1 = findViewById(R.id.et_password);
        pass2 = findViewById(R.id.et_confirmPassword);
        btn_Register = findViewById(R.id.btn_register);
        tv_loginBtn = findViewById(R.id.tv_loginButton);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();



        tv_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegister.this, UserLoginActivity.class));
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputCheckFun(email, pass1, pass2);
            }
        });
    }

    private void InputCheckFun(EditText email, EditText pass, EditText pass2 ) {
        String s_email, s_pass, s_pass2;

        s_email = email.getText().toString();
        s_pass = pass.getText().toString();
        s_pass2 = pass2.getText().toString();

        if (s_email.isEmpty() || TextUtils.isEmpty(s_pass) || TextUtils.isEmpty(s_pass2)) {
            Toast.makeText(this, "Something is wrong", Toast.LENGTH_SHORT).show();
        } else {
            CreatingUser(s_email, s_pass);
        }
    }

    private void CreatingUser(String Email, String Password) {
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    saveUserNameToSharedPreferences(et_username.getText().toString());

                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UserRegister.this, "Check Your Email id", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(UserRegister.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(UserRegister.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void saveUserNameToSharedPreferences(String userName) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME_KEY, userName);
        editor.apply();

    }
}
