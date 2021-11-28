package com.codewithdevesh.letsmeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private TextView gotoSignup;
    private FloatingActionButton login;
    private TextInputLayout email,password;
    private FirebaseAuth auth;
    private SpinKitView pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        pb = findViewById(R.id.pb_login);
        gotoSignup = findViewById(R.id.goto_signup);
        login = findViewById(R.id.login_bttn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        gotoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                String lEmail = Objects.requireNonNull(email.getEditText()).getText().toString();
                String lPassword = Objects.requireNonNull(password.getEditText()).getText().toString();
                auth.signInWithEmailAndPassword(lEmail,lPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            pb.setVisibility(View.GONE);
                            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                            finish();
                            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}