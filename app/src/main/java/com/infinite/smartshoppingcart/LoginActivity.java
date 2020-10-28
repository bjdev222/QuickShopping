package com.infinite.smartshoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {

    EditText loginemail,loginpwd;
    Button btnlogin;
    TextView tvreg;

    private FirebaseAuth mAuth;
    SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginemail=findViewById(R.id.emaillogin);
        loginpwd=findViewById(R.id.pwdlogin);

        btnlogin=findViewById(R.id.btnlogin);
        tvreg=findViewById(R.id.tvregi);

        mAuth = FirebaseAuth.getInstance();


        sp = getSharedPreferences("login",MODE_PRIVATE);

        if(sp.getBoolean("logged",false)){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);

            startActivity(intent);
        }


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            login();
            sp.edit().putBoolean("logged",true).apply();
            }
        });



        tvreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,SignUp.class);
                startActivity(i);
            }
        });

    }

    private void login() {

        final String email=loginemail.getText().toString();
        String password=loginpwd.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);

                            startActivity(intent);



                        } else {


                            loginemail.setText(" ");
                            loginpwd.setText(" ");
                            Toast.makeText(LoginActivity.this, "error while Registered", Toast.LENGTH_SHORT).show();


                        }

                    }
                });


    }


}