package com.realtime.chatapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import static com.google.firebase.auth.FirebaseUser.*;

public class Register extends AppCompatActivity {

    EditText username, password, email;
    Button register;
    TextView login;
    DatabaseReference reff;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toast.makeText(Register.this, "Firebase Connection Success", Toast.LENGTH_LONG).show();

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.registerButton);
        login = (TextView) findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_user = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_pass = password.getText().toString();

                if (TextUtils.isEmpty(txt_user) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass)) {
                    Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else
                    register(txt_user, txt_email, txt_pass);
            }
        });
    }

    private void register(final String username, String email, String password) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                        FirebaseUser firebaseUser = auth.getCurrentUser();

                        assert firebaseUser != null;
                        String userid = firebaseUser.getUid();

                        reff = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", userid);
                        hashMap.put("username", username);
                        hashMap.put("imageURL", "default");

                        reff.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });}
                    }
                });
    }
}

