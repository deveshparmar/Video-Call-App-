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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    private TextView gotoLogin;
    private TextInputLayout name,email,password;
    private FloatingActionButton signup;
    private FirebaseAuth auth;
    private SpinKitView pb;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db= FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        pb = findViewById(R.id.pb_signup);
        gotoLogin = findViewById(R.id.goto_login);
        signup = findViewById(R.id.signup_bttn);
        name = findViewById(R.id.name_signup);
        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.password_signup);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,SignupActivity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                String sEmail = Objects.requireNonNull(email.getEditText()).getText().toString();
                String sName = Objects.requireNonNull(name.getEditText()).getText().toString();
                String sPassword = Objects.requireNonNull(password.getEditText()).getText().toString();
                User user = new User();
                user.setEmail(sEmail);
                user.setPassword(sPassword);
                user.setName(sName);

                auth.createUserWithEmailAndPassword(sEmail,sPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                                db.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        pb.setVisibility(View.GONE);
                                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                                        finish();
                                        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                                    }
                                });
                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}