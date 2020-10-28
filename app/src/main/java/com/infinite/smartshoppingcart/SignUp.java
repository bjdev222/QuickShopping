package com.infinite.smartshoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText email, password;
    Button rgstrbtn;
    private FirebaseAuth mAuth;
    DatabaseReference reff2;
    TextView ihave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        rgstrbtn=findViewById(R.id.sregisterbtn);
        email=findViewById(R.id.semail);
        password=findViewById(R.id.spassword);

        ihave=findViewById(R.id.ihave);

        ihave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUp.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        rgstrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });


    }


    private void register() {


        final String semail = email.getText().toString();
        String spwd = password.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(semail, spwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            email.setText(" ");
                            password.setText(" ");

                            reff2=  FirebaseDatabase.getInstance().getReference();
                            reff2.child(mAuth.getCurrentUser().getUid());


                            Toast.makeText(SignUp.this, "SuccessFully Registered", Toast.LENGTH_SHORT).show();
                            mAuth = FirebaseAuth.getInstance();

                            Intent intent=new Intent(SignUp.this,MainActivity.class);
                            startActivity(intent);


                        } else {
                            email.setText(" ");
                            password.setText(" ");
                            Toast.makeText(SignUp.this, "error while Registered", Toast.LENGTH_SHORT).show();


                        }


                    }
                });
    }
}