package com.realtime.chatapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    EditText password, email;
    Button login;
    TextView register;
    DatabaseReference ref;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        password=(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        login=(Button)findViewById(R.id.loginButton);
        register=(TextView) findViewById(R.id.register);

        auth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_email=email.getText().toString();
                String txt_pass=password.getText().toString();
                if(TextUtils.isEmpty(txt_email)|| TextUtils.isEmpty(txt_pass))
                    Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                else{
                    auth.signInWithEmailAndPassword(txt_email, txt_pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent intent=new Intent(Login.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }
}
